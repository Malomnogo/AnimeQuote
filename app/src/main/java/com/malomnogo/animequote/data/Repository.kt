package com.malomnogo.animequote.data

import com.malomnogo.animequote.data.cloud.QuoteService
import java.net.UnknownHostException

interface Repository {

    suspend fun loadData(): LoadResult

    class Base(private val service: QuoteService) : Repository {

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
    }

    class FakeErrorThenTwiceSuccessThenError : Repository {

        private var position = 0

        override suspend fun loadData(): LoadResult {
            val result =
                if (position in 1..2) LoadResult.Success(quote = "Fake quote text $position")
                else LoadResult.Error(message = "No internet connection")
            position++
            return result
        }
    }
}