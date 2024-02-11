package com.malomnogo.animequote

import java.net.UnknownHostException

interface Repository {

    suspend fun saveJoke(text: String)
    suspend fun loadData(): LoadResult

    class Base(
        private val cacheDataSource: CacheDataSource.Save,
        private val service: QuoteService
    ) : Repository {

        override suspend fun loadData(): LoadResult = try {
            val response = service.load().execute()
            val body = response.body()!!
            body.result()
        } catch (t: Exception) {
            if (t is UnknownHostException)
                LoadResult.Error("No internet connection")
            else
                LoadResult.Error("Service unavailable")
        }

        override suspend fun saveJoke(text: String) {
            cacheDataSource.saveQuote(value = text)
        }
    }

    abstract class AbstractFake(private val cacheDataSource: CacheDataSource.Mutable) :
        Repository {

        override suspend fun saveJoke(text: String) {
            cacheDataSource.saveQuote(text)
        }
    }

    class FakeSuccessAfterError(cacheDataSource: CacheDataSource.Mutable) :
        AbstractFake(cacheDataSource) {

        private var isLoaded = false

        override suspend fun loadData(): LoadResult {
            val result = if (isLoaded) LoadResult.Success(quote = "It's a great funny joke")
            else LoadResult.Error(message = "No internet connection")
            isLoaded = true
            return result
        }
    }

}