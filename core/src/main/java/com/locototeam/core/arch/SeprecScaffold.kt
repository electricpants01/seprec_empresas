package com.locototeam.core.arch

import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewWithFragmentComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.getValue

interface SeprecScaffold<ViewState, ViewEvent, SideEffect> {
    val viewState: StateFlow<ViewState>
    val sideEffects: Flow<SideEffect>
    fun handleEvent(event: ViewEvent)
    fun updateViewState(block: (ViewState) -> ViewState)
    fun CoroutineScope.emitSideEffect(sideEffect: SideEffect)
}

class SeprecScaffoldDelegate<ViewState, ViewEvent, SideEffect>(
    initialState: ViewState,
) : SeprecScaffold<ViewState, ViewEvent, SideEffect> {
    private val _viewState = MutableStateFlow(initialState)

    override val viewState: StateFlow<ViewState>
        get() = _viewState.asStateFlow()

    private val _sideEffects by lazy { Channel<SideEffect>(Channel.BUFFERED) }

    override val sideEffects: Flow<SideEffect> by lazy { _sideEffects.receiveAsFlow() }

    override fun handleEvent(event: ViewEvent) {}

    override fun updateViewState(block: (ViewState) -> ViewState) {
        _viewState.update(block)
    }

    override fun CoroutineScope.emitSideEffect(sideEffect: SideEffect) {
        this.launch { _sideEffects.send(sideEffect) }
    }
}

fun <ViewState, ViewEvent, SideEffect> seprecScaffold(
    initialState: ViewState,
): SeprecScaffoldDelegate<ViewState, ViewEvent, SideEffect> = SeprecScaffoldDelegate(initialState)