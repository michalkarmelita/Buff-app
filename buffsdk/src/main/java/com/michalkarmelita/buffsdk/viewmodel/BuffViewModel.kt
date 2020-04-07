package com.michalkarmelita.buffsdk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michalkarmelita.buffsdk.repository.BuffRepository
import com.michalkarmelita.buffsdk.repository.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


internal class BuffViewModel(private val repository: BuffRepository) : ViewModel() {

    private val buffState: LiveData<BuffState>
        get() = _buffState
    private val _buffState = MutableLiveData<BuffState>()

    init {
        fetchBuffs()
    }

    fun getBuffs(): LiveData<BuffState> = buffState

    fun onAnswerSelected() {
        viewModelScope.launch {
            delay(SELECT_ANSWER_DELAY)
            _buffState.postValue(BuffDismissed)
        }
    }

    fun onClosePressed() {
        _buffState.postValue(BuffDismissed)
    }

    fun onTimeElapsed() {
        _buffState.postValue(BuffDismissed)
    }

    private fun fetchBuffs() {
        viewModelScope.launch(Dispatchers.IO) {
            var item = 1
            while (isActive) {
                val data = repository.getBuffs(calculateBuffId(item))

                when (data) {
                    is DataState.Success -> _buffState.postValue(
                        BuffUiTransformer.convertToBuffUiState(
                            data.data
                        )
                    )
                    //TODO handle all different errors
                    else -> _buffState.postValue(BuffLoadingError)
                }
                item++
                delay(BUFF_REQUEST_PERIOD)
            }
        }
    }

    private fun calculateBuffId(item: Int) = item % 5 + 1

    companion object {
        private val SELECT_ANSWER_DELAY = TimeUnit.SECONDS.toMillis(2L)
        private val BUFF_REQUEST_PERIOD = TimeUnit.SECONDS.toMillis(30L)
    }
}