package com.malomnogo.animequote

import com.google.gson.annotations.SerializedName

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