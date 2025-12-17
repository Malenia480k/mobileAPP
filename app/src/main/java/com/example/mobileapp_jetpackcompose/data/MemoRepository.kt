package com.example.mobileapp_jetpack.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit

object MemoRepository {

    private val _memos = MutableStateFlow<List<Memo>>(emptyList())
    val memos: StateFlow<List<Memo>> = _memos

    /** 메모 추가 또는 수정 */
    fun addOrUpdateMemo(memo: Memo) {
        _memos.value = _memos.value
            .filterNot { it.id == memo.id } + memo
    }

    /** 메모를 휴지통으로 이동 */
    fun moveToTrash(id: Long) {
        _memos.value = _memos.value.map {
            if (it.id == id) {
                it.copy(
                    isTrashed = true,
                    trashedAt = System.currentTimeMillis()
                )
            } else it
        }
    }

    /** 휴지통에서 메모 복구 */
    fun restoreMemo(id: Long) {
        _memos.value = _memos.value.map {
            if (it.id == id) {
                it.copy(
                    isTrashed = false,
                    trashedAt = null
                )
            } else it
        }
    }

    /** 7일 지난 휴지통 메모 완전 삭제 */
    fun deleteExpiredTrashedMemos() {
        val now = System.currentTimeMillis()
        _memos.value = _memos.value.filterNot { memo ->
            memo.isTrashed &&
                    memo.trashedAt != null &&
                    now - memo.trashedAt >= TimeUnit.DAYS.toMillis(7)
        }
    }

    /** 휴지통 메모만 가져오기 */
    fun getTrashedMemos(): List<Memo> {
        deleteExpiredTrashedMemos()
        return _memos.value.filter { it.isTrashed }
    }

    /** 일반 메모만 가져오기 */
    fun getActiveMemos(): List<Memo> {
        deleteExpiredTrashedMemos()
        return _memos.value.filter { !it.isTrashed }
    }

    /** ID로 메모 찾기 */
    fun getMemoById(id: Long): Memo? {
        return _memos.value.find { it.id == id }
    }
}

