package com.malomnogo.animequote.core

import android.app.Application
import androidx.lifecycle.ViewModel

abstract class App : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel

    override fun onCreate() {
        super.onCreate()
        factory = ProvideViewModel.Factory(ProvideViewModel.MakeViewModel(provideInstance()))
    }

    override fun <T : ViewModel> viewModel(clazz: Class<out T>) = factory.viewModel(clazz)

    abstract fun provideInstance(): ProvideInstance

}

class ReleaseApp : App() {
    override fun provideInstance() = ProvideInstance.Base()
}

class MockApp : App() {
    override fun provideInstance() = ProvideInstance.Fake()
}