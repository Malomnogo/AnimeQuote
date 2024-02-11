package com.malomnogo.animequote.data.cloud

import retrofit2.Call
import retrofit2.http.GET

interface QuoteService {

    @GET("api/random")
    fun load(): Call<QuoteResponse>
}


