package com.malomnogo.animequote.presentation

import com.malomnogo.animequote.data.Repository
import com.malomnogo.animequote.core.BaseViewModel

class MainViewModel(
    private val observable: UiObservable,
    private val repository: Repository,
    runAsync: RunAsync
) : BaseViewModel(runAsync) {

    fun load() {
        observable.update(QuoteUiState.Progress)
        runAsync({
            repository.loadData()
        }) { loadResult ->
            loadResult.handle(observable)
        }
    }

    fun startGettingUpdates(uiCallBack: UpdateUi) {
        observable.updateObserver(uiCallBack)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty)
    }

    fun init(isFirstOpen: Boolean) {
        if (isFirstOpen) observable.update(QuoteUiState.Initial)
    }
}