package com.malomnogo.animequote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.malomnogo.animequote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var viewModel: MainViewModel
    private lateinit var uiCallBack: UiCallBack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = viewModel(MainViewModel::class.java)

        uiCallBack = object : UiCallBack {
            override fun update(uiState: QuoteUiState) {
                uiState.show(binding)
            }
        }

        binding.nextButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(uiCallBack)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()

    }

    override fun <T : ViewModel> viewModel(clasz: Class<out T>): T =
        (application as ProvideViewModel).viewModel(clasz)
}

interface UiCallBack {

    fun update(loadUiState: QuoteUiState)

    object Empty : UiCallBack {

        override fun update(uiState: QuoteUiState) = Unit
    }
}