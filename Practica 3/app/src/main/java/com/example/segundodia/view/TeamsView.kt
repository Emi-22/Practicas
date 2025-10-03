package com.example.segundodia.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.segundodia.components.MainIconButton
import com.example.segundodia.components.TitleBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.segundodia.data.Conference
import com.example.segundodia.data.NFLRepository
import com.example.segundodia.data.Team

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsView(navController: NavController, conferenceName: String){
    val conference = Conference.valueOf(conferenceName)
    val teams: List<Team> = NFLRepository.getTeamsByConference(conference)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { TitleBar(if (conference == Conference.AFC) "AFC" else "NFC") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.DarkGray
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(teams) { team ->
                    TeamItem(team = team){ navController.navigate("TeamDetail/${team.id}") }
                }
            }
        }
    }
}

@Composable
private fun TeamItem(team: Team, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = team.primaryColor)
    ){
        val context = LocalContext.current
        val rawId = remember(team.id) { NFLRepository.getTeamLogoRawResId(context, team) }
        val logo: ImageBitmap? = remember(team.id, rawId) {
            if (rawId != 0) BitmapFactory.decodeStream(
                context.resources.openRawResource(rawId)
            )?.asImageBitmap() else null
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            if (logo != null) {
                Image(bitmap = logo, contentDescription = team.name)
            } else {
                Text(text = team.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


