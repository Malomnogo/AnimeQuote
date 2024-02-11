package com.malomnogo.animequote.core

import android.content.Context
import com.malomnogo.animequote.data.Repository
import com.malomnogo.animequote.data.cache.CacheDataSource
import com.malomnogo.animequote.data.cloud.QuoteService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ProvideInstance {

    fun provideRepository(): Repository

    class Base(private val context: Context) : ProvideInstance {

        override fun provideRepository(): Repository {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            val client: OkHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .addInterceptor(logging)
                    .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://animechan.xyz/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return Repository.Base(
                CacheDataSource.Base(context),
                retrofit.create(QuoteService::class.java)
            )
        }
    }

    class Fake : ProvideInstance {

        override fun provideRepository() = Repository.FakeErrorThenTwiceSuccessThenError()

    }
}