package com.example.moodify.ui.theme.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodify.R

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
                    Icon(painter = painterResource(R.drawable.moodify),null)
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
