package com.example.segundodia.onBoardViews

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.segundodia.R
import com.example.segundodia.data.PageData
import com.example.segundodia.dataStore.StoreBoarding

@Composable
fun MainOnBoarding(navController: NavController, store: StoreBoarding){
    val items=ArrayList<PageData>()

    items.add(
        PageData(
            image= R.raw.car_wash,
            titulo="Primer Titulo",
            descripcion="Descripcion 1"
        )

    )

}