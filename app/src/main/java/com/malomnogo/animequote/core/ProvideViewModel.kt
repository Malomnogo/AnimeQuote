@file:Suppress("UNCHECKED_CAST")

package com.malomnogo.animequote.core

import android.content.Context
import androidx.lifecycle.ViewModel
import com.malomnogo.animequote.data.Repository
import com.malomnogo.animequote.data.cache.CacheDataSource
import com.malomnogo.animequote.presentation.RunAsync
import com.malomnogo.animequote.presentation.UiObservable
import com.malomnogo.animequote.presentation.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(clazz: Class<out T>): T

    class Factory(private val makeViewModel: ProvideViewModel) : ProvideViewModel {
        private val map = HashMap<Class<out ViewModel>, ViewModel>()
        override fun <T : ViewModel> viewModel(clazz: Class<out T>): T =
            if (map.containsKey(clazz)) {
                map[clazz]
            } else {
                val viewModel = makeViewModel.viewModel(clazz)
                map[clazz] = viewModel
                viewModel
            } as T
    }

    class Base(context: Context) : ProvideViewModel {

        private val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        private val client: OkHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .addInterceptor(logging)
                .build()
        private val retrofit: Retrofit =
            Retrofit.Builder()
                .baseUrl("https://animechan.xyz/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        private val observable = UiObservable.Base()
        private val cacheDataSource = CacheDataSource.Base(context)

        private val repository =
            Repository.FakeErrorThenTwiceSuccessThenError(cacheDataSource)
//            Repository.Base(cacheDataSource, retrofit.create(QuoteService::class.java))

        private val runAsync = RunAsync.Base()

        override fun <T : ViewModel> viewModel(clazz: Class<out T>): T {
            return when (clazz) {
                MainViewModel::class.java -> MainViewModel(observable, repository, runAsync)
                else -> throw IllegalStateException("No such viewModel with class:$clazz")
            } as T
        }
    }
}