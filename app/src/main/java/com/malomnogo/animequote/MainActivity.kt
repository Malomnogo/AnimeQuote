package com.malomnogo.animequote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.malomnogo.animequote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var viewModel: MainViewModel
    private lateinit var uiCallBack: UpdateUi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = viewModel(MainViewModel::class.java)

        uiCallBack = object : UpdateUi {
            override fun update(uiState: QuoteUiState) {
                uiState.update(binding)
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

    override fun <T : ViewModel> viewModel(clazz: Class<out T>): T =
        (application as ProvideViewModel).viewModel(clazz)
}

interface UpdateUi {

    fun update(uiState: QuoteUiState)

    object Empty : UpdateUi {

        override fun update(uiState: QuoteUiState) = Unit
    }
}