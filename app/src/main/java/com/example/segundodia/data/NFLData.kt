package com.example.segundodia.data

import androidx.compose.ui.graphics.Color

enum class Conference {
    AFC, NFC
}

data class Team(
    val id: Int,
    val name: String,
    val conference: Conference,
    val primaryColor: Color,
    val description: String
)

object NFLRepository {
    val teams: List<Team> = listOf(
        // AFC (solo equipos con logo disponible en res/raw)
        Team(
            id = 1,
            name = "Miami Dolphins",
            conference = Conference.AFC,
            primaryColor = Color(0xFF008E97),
            description = "AFC Este, ofensiva rápida y dinámica."
        ),
        Team(
            id = 2,
            name = "New England Patriots",
            conference = Conference.AFC,
            primaryColor = Color(0xFF002244),
            description = "AFC Este, dinastía histórica bajo Belichick y Brady."
        ),
        Team(
            id = 3,
            name = "Pittsburgh Steelers",
            conference = Conference.AFC,
            primaryColor = Color(0xFF101820),
            description = "AFC Norte, defensa física y seis campeonatos."
        ),
        Team(
            id = 4,
            name = "Cincinnati Bengals",
            conference = Conference.AFC,
            primaryColor = Color(0xFFFB4F14),
            description = "AFC Norte, ataque aéreo explosivo."
        ),

        // NFC (solo equipos con logo disponible en res/raw)
        Team(
            id = 5,
            name = "Dallas Cowboys",
            conference = Conference.NFC,
            primaryColor = Color(0xFF041E42),
            description = "NFC Este, franquicia histórica 'America's Team'."
        ),
        Team(
            id = 6,
            name = "Los Angeles Rams",
            conference = Conference.NFC,
            primaryColor = Color(0xFF003594),
            description = "NFC Oeste, campeones recientes del Super Bowl."
        ),
        Team(
            id = 7,
            name = "New Orleans Saints",
            conference = Conference.NFC,
            primaryColor = Color(0xFFD3BC8D),
            description = "NFC Sur, ofensiva creativa y estadio ruidoso."
        ),
        Team(
            id = 8,
            name = "Carolina Panthers",
            conference = Conference.NFC,
            primaryColor = Color(0xFF0085CA),
            description = "NFC Sur, identidad joven en reconstrucción."
        )
    )

    fun getTeamsByConference(conference: Conference): List<Team> =
        teams.filter { it.conference == conference }

    fun getTeamById(id: Int): Team? = teams.firstOrNull { it.id == id }

    // Obtiene el id de recurso en raw por nombre mapeado; devuelve 0 si no existe
    fun getTeamLogoRawResId(context: android.content.Context, team: Team): Int {
        val nameToRaw: Map<String, String> = mapOf(
            "Miami Dolphins" to "dolphins_logo",
            "New England Patriots" to "patriots_logo",
            "Pittsburgh Steelers" to "steelers_logo",
            "Cincinnati Bengals" to "bengals_logo",
            "Dallas Cowboys" to "cowboys_logo",
            "Los Angeles Rams" to "rams_logo",
            "New Orleans Saints" to "saints_logo",
            // Archivo provisto como 'panters_logo'
            "Carolina Panthers" to "panters_logo"
        )
        val rawName = nameToRaw[team.name] ?: return 0
        return context.resources.getIdentifier(rawName, "raw", context.packageName)
    }
}


