package com.malomnogo.animequote.data

import com.malomnogo.animequote.presentation.QuoteUiState
import com.malomnogo.animequote.presentation.UiObservable

interface LoadResult {

    fun handle(observable: UiObservable)

    abstract class Base(private val updateState: QuoteUiState) : LoadResult {
        override fun handle(observable: UiObservable) {
            observable.update(updateState)
        }
    }

    data class Success(private val quote: String) : Base(QuoteUiState.Success(quote))

    data class Error(private val message: String) : Base(QuoteUiState.Error(message))
}