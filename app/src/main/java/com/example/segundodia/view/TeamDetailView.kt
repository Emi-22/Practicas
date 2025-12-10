package com.example.segundodia.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.segundodia.components.MainIconButton
import com.example.segundodia.components.TitleBar
import com.example.segundodia.data.NFLRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailView(navController: NavController, teamId: Int){
    val team = NFLRepository.getTeamById(teamId)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { TitleBar(team?.name ?: "Equipo") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = team?.primaryColor ?: Color.DarkGray
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val context = LocalContext.current
            val rawId = team?.let { t -> NFLRepository.getTeamLogoRawResId(context, t) } ?: 0
            val logo = remember(team?.id, rawId) {
                if (rawId != 0) BitmapFactory.decodeStream(
                    context.resources.openRawResource(rawId)
                )?.asImageBitmap() else null
            }
            if (logo != null) {
                Image(bitmap = logo, contentDescription = team?.name)
            }
            Text(text = team?.name ?: "", fontSize = 28.sp)
            Text(text = team?.description ?: "", fontSize = 18.sp)
        }
    }
}


