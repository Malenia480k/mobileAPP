package com.example.mobileapp_jetpack.data

data class Memo(
    val id: Long,
    val title: String,
    val content: String,
    val timestamp: Long,
    val isTrashed: Boolean = false,
    val trashedAt: Long? = null
)
