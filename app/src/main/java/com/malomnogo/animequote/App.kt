package com.malomnogo.animequote

import android.app.Application
import androidx.lifecycle.ViewModel

class App : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        val makeViewModel = ProvideViewModel.Base(this)
        factory = ProvideViewModel.Factory(makeViewModel)
    }

    override fun <T : ViewModel> viewModel(clazz: Class<out T>): T {
        return factory.viewModel(clazz)
    }
}