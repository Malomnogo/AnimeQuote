package com.malomnogo.animequote

import com.malomnogo.animequote.data.LoadResult
import com.malomnogo.animequote.data.Repository
import com.malomnogo.animequote.presentation.MainViewModel
import com.malomnogo.animequote.presentation.QuoteUiState
import com.malomnogo.animequote.presentation.RunAsync
import com.malomnogo.animequote.presentation.UiObservable
import com.malomnogo.animequote.presentation.UpdateUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: FakeRepository
    private lateinit var observable: FakeUiObservable
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setup() {
        repository = FakeRepository()
        observable = FakeUiObservable()
        runAsync = FakeRunAsync()
        viewModel = MainViewModel(
            repository = repository,
            observable = observable,
            runAsync = runAsync
        )
    }

    @Test
    fun testFirstOpen() {
        viewModel.init(isFirstOpen = true)
        assertEquals(listOf(QuoteUiState.Initial), observable.states)

        val uiCallback = object : UpdateUi {
            override fun update(uiState: QuoteUiState) = Unit
        }

        viewModel.startGettingUpdates(uiCallback)
        assertEquals(listOf(uiCallback), observable.observers)

        viewModel.stopGettingUpdates()
        assertEquals(listOf(uiCallback, UpdateUi.Empty), observable.observers)
    }

    @Test
    fun testNotFirstRun() {
        viewModel.init(isFirstOpen = false)
        assertEquals(emptyList<QuoteUiState>(), observable.states)
    }

    @Test
    fun testSuccess() {
        repository.result = LoadResult.Success(quote = "Fake quote text")
        viewModel.load()
        assertEquals(listOf(QuoteUiState.Progress), observable.states)

        runAsync.returnResult()
        assertEquals(
            listOf(
                QuoteUiState.Progress,
                QuoteUiState.Success(quote = "Fake quote text")
            ), observable.states
        )
    }

    @Test
    fun testError() {
        repository.result = LoadResult.Error(message = "No internet connection")
        viewModel.load()
        assertEquals(listOf(QuoteUiState.Progress), observable.states)

        runAsync.returnResult()
        assertEquals(
            listOf(
                QuoteUiState.Progress,
                QuoteUiState.Error(message = "No internet connection")
            ), observable.states
        )
    }

}

private class FakeRepository : Repository {

    lateinit var result: LoadResult
    private var cachedQuote = ""

    override suspend fun loadData() = result

    override suspend fun saveQuote(text: String) {
        cachedQuote = text
    }

}

class FakeUiObservable : UiObservable {

    val states = mutableListOf<QuoteUiState>()
    val observers = mutableListOf<UpdateUi>()

    override fun update(uiState: QuoteUiState) {
        states.add(uiState)
    }

    override fun updateObserver(observer: UpdateUi) {
        observers.add(observer)
        update(states.last())
    }
}

@Suppress("UNCHECKED_CAST")
class FakeRunAsync : RunAsync {

    private var cachedUiBlock: (Any) -> Unit = {}
    private var cachedResult: Any = ""

    fun returnResult() {
        cachedUiBlock.invoke(cachedResult)
    }

    override fun <T : Any> start(
        coroutineScope: CoroutineScope,
        background: suspend () -> T,
        uiBlock: (T) -> Unit,
    ) = runBlocking {
        val result = background.invoke()
        cachedResult = result
        cachedUiBlock = uiBlock as (Any) -> Unit
    }

}