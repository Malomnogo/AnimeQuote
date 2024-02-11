package com.malomnogo.animequote

class MainViewModel(
    private val observable: UiObservable,
    private val repository: Repository,
    runAsync: RunAsync
) : BaseViewModel(runAsync) {

    fun load() {
        observable.updateUi(UiState.Progress)
        runAsync({
            repository.loadData()
        }) { loadResult ->
            loadResult.handle(observable)
        }
    }

    fun startGettingUpdates(uiCallBack: UiCallBack) {
        observable.updateObserver(uiCallBack)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UiCallBack.Empty)
    }

    fun init(isFirstOpen: Boolean) {
        if (isFirstOpen) observable.updateUi(UiState.Initial)
    }

}