package com.example.mobileapp_jetpack.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileapp_jetpack.data.MemoRepository
import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat
import java.util.*



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen() {
    val trashedMemos = MemoRepository.getTrashedMemos()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("휴지통") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(trashedMemos) { memo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = memo.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = SimpleDateFormat(
                                    "yyyy년 MM월 dd일",
                                    Locale.getDefault()
                                ).format(Date(memo.timestamp)),
                                style = MaterialTheme.typography.bodySmall
                            )

                            if (memo.trashedAt != null) {
                                Text(
                                    text = "삭제까지 ${remainingDays(memo.trashedAt)}일 남음",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                            TextButton(onClick = {
                                MemoRepository.restoreMemo(memo.id)
                            }) {
                                Text("복구")
                            }
                        }

                    }
                    }
                }
            }
        }


fun remainingDays(trashedAt: Long): Long {
    val passed = System.currentTimeMillis() - trashedAt
    val daysPassed = TimeUnit.MILLISECONDS.toDays(passed)
    return (7 - daysPassed).coerceAtLeast(0)
}
