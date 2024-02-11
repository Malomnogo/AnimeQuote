package com.malomnogo.animequote.presentation

interface UiObservable : UpdateUi, UpdateObserver {

    class Base : UiObservable {

        private var cache: QuoteUiState = QuoteUiState.Empty
        private var observer: UpdateUi = UpdateUi.Empty

        override fun update(uiState: QuoteUiState) {
            cache = uiState
            observer.update(cache)
        }

        override fun updateObserver(observer: UpdateUi) {
            this.observer = observer
            observer.update(cache)
        }
    }
}

interface UpdateObserver {
    fun updateObserver(observer: UpdateUi)
}
