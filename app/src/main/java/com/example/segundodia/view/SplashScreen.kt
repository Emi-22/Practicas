package com.example.segundodia.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.segundodia.R
import kotlinx.coroutines.delay
import android.graphics.BitmapFactory

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(1000)
        navController.navigate("Conferences") {
            popUpTo("Splash") { inclusive = true }
        }
    }
    val context = LocalContext.current
    val logo: ImageBitmap = remember {
        BitmapFactory.decodeStream(
            context.resources.openRawResource(R.raw.nfl_logo)
        ).asImageBitmap()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            bitmap = logo,
            contentDescription = "NFL Logo"
        )
    }
}
