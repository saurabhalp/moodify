package com.example.moodify.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import com.example.moodify.R
@Composable

fun SplashScreen(navController: NavController) {
    LaunchedEffect(true) {
        delay(2000)
        navController.navigate("home")
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(R.drawable.moodify),null)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Moodify", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
    }

}

@Preview
@Composable
fun splashPreview(){
    SplashScreen(navController = rememberNavController())
}