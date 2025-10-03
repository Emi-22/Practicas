package com.example.segundodia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.segundodia.view.ConferencesView
import com.example.segundodia.view.HomeView
import com.example.segundodia.view.TeamDetailView
import com.example.segundodia.view.TeamsView
import com.example.segundodia.view.SplashScreen


@Composable
fun NavManager(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Splash"){
        composable("Splash"){ SplashScreen(navController) }
        composable("Home"){ HomeView(navController) }
        composable("Conferences"){ ConferencesView(navController) }
        composable(
            route = "Teams/{conference}",
            arguments = listOf(navArgument("conference"){ type = NavType.StringType })
        ){
            val conf = it.arguments?.getString("conference") ?: "AFC"
            TeamsView(navController, conf)
        }
        composable(
            route = "TeamDetail/{teamId}",
            arguments = listOf(navArgument("teamId"){ type = NavType.IntType })
        ){
            val teamId = it.arguments?.getInt("teamId") ?: 0
            TeamDetailView(navController, teamId)
        }
    }
}
