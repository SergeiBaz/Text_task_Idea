package com.example.test_task.feature.home.until

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Закинул чисто для своего удобства
 * Должно быть в core каком нибудь, но его было лень создавать
 */

interface MviState
interface MviAction
interface MviEffect

abstract class MviViewModel<State : MviState, Action : MviAction, Effect : MviEffect>(initialState: State) :
    ViewModel() {

    abstract fun onAction(action: Action)

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    protected fun changeState(block: (State) -> State) {
        _uiState.update(block)
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

}