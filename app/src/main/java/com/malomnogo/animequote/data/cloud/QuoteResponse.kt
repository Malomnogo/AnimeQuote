package com.malomnogo.animequote.data.cloud

import com.google.gson.annotations.SerializedName
import com.malomnogo.animequote.data.LoadResult

data class QuoteResponse(
    @SerializedName("quote")
    private val quote: String,
    @SerializedName("anime")
    private val anime: String,
    @SerializedName("character")
    private val character: String
) {
    fun result(): LoadResult = LoadResult.Success(quote = quote)
}