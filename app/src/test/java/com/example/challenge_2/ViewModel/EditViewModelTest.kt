package com.example.challenge_2.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.challenge_2.ViewModel.EditViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EditViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var edCounterObserver: Observer<Int>

    @Mock
    private lateinit var priceCounterObserver: Observer<Int>

    private lateinit var editViewModel: EditViewModel

    @Before
    fun setup() {
        editViewModel = EditViewModel()
        editViewModel.edcounter.observeForever(edCounterObserver)
        editViewModel.pricecounter.observeForever(priceCounterObserver)
    }

    @Test
    fun testIncrementCount() {

        editViewModel._edCounter.value = 0
        editViewModel._priceCounter.value = 10
        val harga = 10
        editViewModel.IncrementCount(harga)

        // Then
        verify(edCounterObserver).onChanged(1)
        verify(priceCounterObserver).onChanged(20)
    }

    @Test
    fun testDecrementCount() {
        editViewModel._edCounter.value = 2
        editViewModel._priceCounter.value = 10
        val harga = 10
        editViewModel.DecrementCount(harga)
        verify(edCounterObserver).onChanged(1)
        verify(priceCounterObserver).onChanged(0)
    }
}