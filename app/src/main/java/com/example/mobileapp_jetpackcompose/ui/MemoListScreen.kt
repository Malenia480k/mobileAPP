package com.example.mobileapp_jetpack.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobileapp_jetpack.data.MemoRepository
import java.text.SimpleDateFormat
import java.util.*




@Composable
fun MemoListScreen(navController: NavController) {
    val memos by MemoRepository.memos.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("edit/-1") }
            ) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).padding(16.dp)
        ) {
            items(memos) { memo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            navController.navigate("edit/${memo.id}")
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(memo.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            SimpleDateFormat(
                                "yyyy년 MM월 dd일",
                                Locale.getDefault()
                            ).format(Date(memo.timestamp)),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}
