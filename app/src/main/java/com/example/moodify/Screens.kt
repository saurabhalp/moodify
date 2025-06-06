package com.example.moodify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

// 📁 MoodTuneApp.kt
// Main Navigation Setup

@Composable
fun MoodTuneApp() {
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

// 📄 SplashScreen.kt
@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(true) {
        delay(2000)
        navController.navigate("home")
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("MoodTune", fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }
}

// 📄 CameraScreen.kt
@Composable
fun CameraScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Scan your Mood", fontSize = 24.sp)
        Spacer(Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text("[Camera Preview Here]", color = Color.White)
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            // TODO: Implement camera capture and ML model integration
            val dummyMood = "Happy"
            navController.navigate("result/$dummyMood")
        }) {
            Text("Scan My Mood")
        }
    }
}

// 📄 ResultScreen.kt
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

// 📄 MusicScreen.kt
@Composable
fun MusicScreen(navController: NavController, mood: String) {
    val musicList = mapOf(
        "Happy" to listOf("Happy Song 1", "Dance Vibes"),
        "Sad" to listOf("Lo-fi Chill", "Soft Piano"),
        "Angry" to listOf("Rock Beats", "Workout Pump")
    )[mood] ?: listOf("Generic Tune 1", "Generic Tune 2")

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Suggested Music for $mood", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(10.dp))
        musicList.forEach { song ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(R.drawable.ic_launcher_foreground),null)
                    Spacer(Modifier.width(10.dp))
                    Text(song)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        OutlinedButton(onClick = { navController.popBackStack("home", inclusive = false) }) {
            Text("Back to Camera")
        }
    }
}
