package com.example.mobileapp_jetpack.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobileapp_jetpack.data.Memo
import com.example.mobileapp_jetpack.data.MemoRepository

@Composable
fun MemoEditScreen(
    navController: NavController,
    memoId: Long
) {
    val existingMemo = remember {
        MemoRepository.getMemoById(memoId)
    }

    var title by remember { mutableStateOf(existingMemo?.title ?: "") }
    var content by remember { mutableStateOf(existingMemo?.content ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("제목") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("내용") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val memo = Memo(
                    id = existingMemo?.id ?: System.currentTimeMillis(),
                    title = title,
                    content = content,
                    timestamp = existingMemo?.timestamp ?: System.currentTimeMillis(),
                    isTrashed = false,
                    trashedAt = null
                )

                MemoRepository.addOrUpdateMemo(memo)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("저장")
        }
    }
}
