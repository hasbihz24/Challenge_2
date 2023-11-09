package com.example.challenge_2.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.challenge_2.model.Resource
import com.example.challenge_2.repos.MainRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher

class ApiViewModel(private val mainRepository: MainRepository): ViewModel() {
    fun getAllMenu() = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = mainRepository.getMenu()))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }

}