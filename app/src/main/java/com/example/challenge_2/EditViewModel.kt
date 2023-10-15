package com.example.challenge_2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditViewModel: ViewModel() {
    var _edCounter : MutableLiveData<Int> = MutableLiveData()
    val edcounter: LiveData<Int> get() = _edCounter
//    fun getLiveData(): LiveData<Int>{
//        return _edCounter
//    }

    fun IncrementCount(){
        _edCounter.value = _edCounter.value?.plus(1)
    }

    fun DecrementCount(){
        _edCounter.value?.let {
            if(it > 0){
                _edCounter.value = _edCounter.value?.minus(1)
            }
        }
    }
}