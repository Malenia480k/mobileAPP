package com.example.mobileapp_jetpack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp_jetpack.ui.MemoEditScreen
import com.example.mobileapp_jetpack.ui.MemoListScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable("list") {
            MemoListScreen(navController)
        }
        composable("edit/{memoId}") {
            val memoId = it.arguments?.getString("memoId")?.toLong() ?: -1
            MemoEditScreen(navController, memoId)
        }
    }
}
