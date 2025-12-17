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
fun MemoEditScreen(navController: NavController, memoId: Long) {
    val existingMemo = MemoRepository.getMemo(memoId)

    var title by remember { mutableStateOf(existingMemo?.title ?: "") }
    var content by remember { mutableStateOf(existingMemo?.content ?: "") }

    Column(modifier = Modifier.padding(16.dp)) {
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
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (existingMemo == null) {
                MemoRepository.addMemo(Memo(title = title, content = content))
            } else {
                MemoRepository.updateMemo(
                    existingMemo.copy(title = title, content = content)
                )
            }
            navController.popBackStack()
        }) {
            Text("저장")
        }
    }
}
