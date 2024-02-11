@file:Suppress("UNCHECKED_CAST")

package com.malomnogo.animequote.core

import androidx.lifecycle.ViewModel
import com.malomnogo.animequote.presentation.MainViewModel
import com.malomnogo.animequote.presentation.RunAsync
import com.malomnogo.animequote.presentation.UiObservable

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(clazz: Class<out T>): T

    class Factory(private val makeViewModel: ProvideViewModel) : ProvideViewModel {

        private val viewModelMap = mutableMapOf<Class<out ViewModel>, ViewModel>()

        override fun <T : ViewModel> viewModel(clazz: Class<out T>): T =
            if (viewModelMap.containsKey(clazz)) {
                viewModelMap[clazz]
            } else {
                val viewModel = makeViewModel.viewModel(clazz)
                viewModelMap[clazz] = viewModel
                viewModel
            } as T
    }

    class MakeViewModel(private val provideInstance: ProvideInstance) : ProvideViewModel {

        override fun <T : ViewModel> viewModel(clazz: Class<out T>): T =
            if (clazz == MainViewModel::class.java) {
                MainViewModel(
                    observable = UiObservable.Base(),
                    repository = provideInstance.provideRepository(),
                    runAsync = RunAsync.Base(),
                ) as T
            } else {
                throw IllegalStateException()
            }
    }
}