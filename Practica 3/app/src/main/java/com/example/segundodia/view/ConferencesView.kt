package com.example.segundodia.view

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.segundodia.data.Conference

@Composable
fun ConferencesView(navController: NavController){
    val context = LocalContext.current
    val afcLogo: ImageBitmap = remember {
        BitmapFactory.decodeStream(
            context.resources.openRawResource(
                com.example.segundodia.R.raw.american_conference_logo
            )
        ).asImageBitmap()
    }
    val nfcLogo: ImageBitmap = remember {
        BitmapFactory.decodeStream(
            context.resources.openRawResource(
                com.example.segundodia.R.raw.national_conference_logo
            )
        ).asImageBitmap()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ConferenceButton(
                title = "Conferencia Americana (AFC)",
                logo = afcLogo,
                color = Color(0xFFE31837)
            ) { navController.navigate("Teams/${Conference.AFC.name}") }

            ConferenceButton(
                title = "Conferencia Nacional (NFC)",
                logo = nfcLogo,
                color = Color(0xFF004C54)
            ) { navController.navigate("Teams/${Conference.NFC.name}") }
        }
    }
}

@Composable
private fun ConferenceButton(
    title: String,
    logo: ImageBitmap,
    color: Color,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(88.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(bitmap = logo, contentDescription = null, modifier = Modifier.size(40.dp))
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(12.dp))
            Text(text = title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}


