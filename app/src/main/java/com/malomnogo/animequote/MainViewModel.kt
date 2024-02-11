package com.malomnogo.animequote

import androidx.lifecycle.ViewModel

class MainViewModel(
    private val repository: ScreenRepository.Read,
    private val navigation: NavigationObservable,
) : ViewModel() {

    fun init(isFirstTimeOpen: Boolean) {
        navigation.navigate(
            if (isFirstTimeOpen) LoadScreen
            else QuoteScreen
        )
    }

    fun startGettingUpdates(observer: Navigation) {
        navigation.updateNavigateObserver(observer)
    }

    fun stopGettingUpdates() {
        navigation.updateNavigateObserver(navigation)
    }
}