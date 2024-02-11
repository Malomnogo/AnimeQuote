package com.malomnogo.animequote.data

import com.malomnogo.animequote.data.cache.CacheDataSource
import com.malomnogo.animequote.data.cloud.QuoteService
import java.net.UnknownHostException

interface Repository {

    suspend fun saveQuote(text: String)
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

        override suspend fun saveQuote(text: String) {
            cacheDataSource.saveQuote(value = text)
        }
    }

    class FakeErrorThenTwiceSuccessThenError(
        private val cacheDataSource: CacheDataSource.Mutable
    ) : Repository {

        private var position = 0

        override suspend fun saveQuote(text: String) {
            cacheDataSource.saveQuote(text)
        }

        override suspend fun loadData(): LoadResult {
            val result =
                if (position in 1..2) LoadResult.Success(quote = "Fake quote text $position")
                else LoadResult.Error(message = "No internet connection")
            position++
            return result
        }
    }

}