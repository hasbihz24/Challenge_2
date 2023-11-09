package com.example.challenge_2.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge_2.api.APIService
import com.example.challenge_2.repos.MainRepository

class ApiViewModelFactory(private val apiService: APIService): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ApiViewModel::class.java)){
            return ApiViewModel(MainRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }
}