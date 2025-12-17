package com.example.mobileapp_jetpack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp_jetpack.ui.MemoEditScreen
import com.example.mobileapp_jetpack.ui.MemoListScreen
import com.example.mobileapp_jetpack.ui.TrashScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {

        // 메모 목록 화면
        composable("list") {
            MemoListScreen(navController)
        }

        // 메모 작성 / 수정 화면
        composable("edit/{memoId}") { backStackEntry ->
            val memoId = backStackEntry.arguments
                ?.getString("memoId")
                ?.toLongOrNull() ?: -1L

            MemoEditScreen(
                navController = navController,
                memoId = memoId
            )
        }

        // 휴지통 화면
        composable("trash") {
            TrashScreen()
        }
    }
}

