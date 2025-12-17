package com.example.mobileapp_jetpack.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object MemoRepository {

    private val _memos = MutableStateFlow<List<Memo>>(emptyList())
    val memos: StateFlow<List<Memo>> = _memos

    fun addMemo(memo: Memo) {
        _memos.value = _memos.value + memo
    }

    fun updateMemo(memo: Memo) {
        _memos.value = _memos.value.map {
            if (it.id == memo.id) memo else it
        }
    }

    fun deleteMemo(id: Long) {
        _memos.value = _memos.value.filterNot { it.id == id }
    }

    fun getMemo(id: Long): Memo? {
        return _memos.value.find { it.id == id }
    }
}
