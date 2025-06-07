package com.example.moodify.ui.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class MoodImageAnalyzer(
    private val context: Context,
    private val onMoodDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val moods = listOf("Happy", "Sad", "Surprised", "Fearful", "Angry", "Disgusted", "Neutral")

    private val interpreter: Interpreter by lazy {
        try {
            val model = loadModelFile(context, "model.tflite")
            val interpreter = Interpreter(model)

            // Log input/output details
            val inputTensor = interpreter.getInputTensor(0)
            val outputTensor = interpreter.getOutputTensor(0)

            Log.d("MoodAnalyzer", "Input shape: ${inputTensor.shape().joinToString()} Type: ${inputTensor.dataType()}")
            Log.d("MoodAnalyzer", "Output shape: ${outputTensor.shape().joinToString()} Type: ${outputTensor.dataType()}")

            interpreter
        } catch (e: Exception) {
            Log.e("MoodAnalyzer", "Error initializing interpreter: ${e.message}")
            Toast.makeText(context, "Error initializing TFLite Model: ${e.message}", Toast.LENGTH_LONG).show()
            throw RuntimeException("Failed to initialize TFLite interpreter", e)
        }
    }

    override fun analyze(imageProxy: ImageProxy) {
        try {
            val bitmap = imageProxyToBitmap(imageProxy) ?: run {
                imageProxy.close()
                return
            }

            // Resize to 224x224 for model input
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val input = convertBitmapToInputArray(resizedBitmap)

            val output = Array(1) { FloatArray(7) }
            interpreter.run(input, output)

            val predictedIndex = output[0].indices.maxByOrNull { output[0][it] } ?: 0
            val mood = moods.getOrElse(predictedIndex) { "Unknown" }

            Log.d("MoodAnalyzer", "Predicted mood: $mood")

            onMoodDetected(mood)
        } catch (e: Exception) {
            Log.e("MoodAnalyzer", "Error analyzing image: ${e.message}")
        } finally {
            imageProxy.close()
        }
    }

    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            fileDescriptor.startOffset,
            fileDescriptor.declaredLength
        )
    }

    // Proper YUV_420_888 to RGB Bitmap conversion
    private fun imageProxyToBitmap(image: ImageProxy): Bitmap? {
        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, image.width, image.height), 100, out)
        val imageBytes = out.toByteArray()

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    // Convert Bitmap to input array [1,224,224,3] normalized float
    private fun convertBitmapToInputArray(bitmap: Bitmap): Array<Array<Array<FloatArray>>> {
        val width = 224
        val height = 224
        val input = Array(1) { Array(height) { Array(width) { FloatArray(3) } } }

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = bitmap.getPixel(x, y)
                val r = ((pixel shr 16) and 0xFF) / 255f
                val g = ((pixel shr 8) and 0xFF) / 255f
                val b = (pixel and 0xFF) / 255f
                input[0][y][x][0] = r
                input[0][y][x][1] = g
                input[0][y][x][2] = b
            }
        }
        return input
    }
}
