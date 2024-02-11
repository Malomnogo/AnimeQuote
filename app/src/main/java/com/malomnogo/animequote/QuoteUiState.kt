package com.malomnogo.animequote

import android.view.View
import androidx.core.content.ContextCompat
import com.malomnogo.animequote.databinding.ActivityMainBinding

interface QuoteUiState {

    fun update(binding: ActivityMainBinding)

    abstract class Abstract(
        private val message: String,
        private val buttonText: Int = R.string.new_quote,
        private val progressVisibility: Int = View.GONE,
        private val textColor: Int = R.color.black,
    ) : QuoteUiState {

        override fun update(binding: ActivityMainBinding) = with(binding) {
            mainTextView.text = message
            progressBar.visibility = progressVisibility
            mainTextView.setTextColor(ContextCompat.getColor(mainTextView.context, textColor))
            nextButton.setText(buttonText)
        }
    }

    data class Error(private val message: String) : Abstract(
        message = message,
        textColor = R.color.red,
        buttonText = R.string.retry
    )

    object Progress : Abstract(message = "", progressVisibility = View.VISIBLE)

    data class Success(private val quote: String) : Abstract(quote)

    object Initial : Abstract(message = "", buttonText = R.string.new_quote)

    object Empty : QuoteUiState {
        override fun update(binding: ActivityMainBinding) = Unit
    }
}