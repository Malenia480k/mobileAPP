package com.example.mobileapp_jetpack.data

data class Memo(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
