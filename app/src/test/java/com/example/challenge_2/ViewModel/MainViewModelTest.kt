package com.example.challenge_2.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.challenge_2.ViewModel.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var mainViewModel: MainViewModel
    @Mock
    private lateinit var observer: Observer<Int>

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
        mainViewModel.counter.observeForever(observer)
    }

    @Test
    fun testIncrementCount() {
        mainViewModel.IncrementCount()
        Mockito.verify(observer).onChanged(1)
    }

    @Test
    fun testDecrementCount() {
        mainViewModel.DecrementCount()
        Mockito.verify(observer, Mockito.never()).onChanged(-1)
    }

}