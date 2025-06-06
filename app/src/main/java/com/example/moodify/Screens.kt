package com.example.moodify
import CameraScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moodify.ui.theme.screens.MusicScreen
import com.example.moodify.ui.theme.screens.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun MoodTuneApp(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { CameraScreen(navController) }
        composable("result/{mood}") { backStackEntry ->
            val mood = backStackEntry.arguments?.getString("mood") ?: "Unknown"
            ResultScreen(navController, mood)
        }
        composable("music/{mood}") { backStackEntry ->
            val mood = backStackEntry.arguments?.getString("mood") ?: "Unknown"
            MusicScreen(navController, mood)
        }
    }
}

@Composable
fun ResultScreen(navController: NavController, mood: String) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("You look $mood!", fontSize = 28.sp)
        Spacer(Modifier.height(20.dp))
        Button(onClick = { navController.navigate("music/$mood") }) {
            Text("Play Music")
        }
        Spacer(Modifier.height(10.dp))
        OutlinedButton(onClick = { navController.popBackStack("home", inclusive = false) }) {
            Text("Scan Again")
        }
    }
}
