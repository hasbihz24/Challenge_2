package com.example.challenge_2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val _counter: MutableLiveData<Int> = MutableLiveData(0)
    val counter: LiveData<Int> get() = _counter

    fun IncrementCount(){
        _counter.value = _counter.value?.plus(1)
    }

    fun DecrementCount(){
        _counter.value?.let {
            if(it > 0){
                _counter.value = _counter.value?.minus(1)
            }
        }
    }

}