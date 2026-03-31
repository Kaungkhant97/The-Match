package com.thematch.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.thematch.app.navigation.NavGraph
import com.thematch.app.ui.theme.TheMatchTheme

@Composable
fun App() {
    TheMatchTheme {
        val navController = rememberNavController()
        NavGraph(navController = navController)
    }
}
