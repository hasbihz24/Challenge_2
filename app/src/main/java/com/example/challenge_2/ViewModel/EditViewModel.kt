package com.example.challenge_2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditViewModel: ViewModel() {
    var _edCounter : MutableLiveData<Int> = MutableLiveData()
    val edcounter: LiveData<Int> get() = _edCounter
    var _priceCounter : MutableLiveData<Int> = MutableLiveData()
    val pricecounter: LiveData<Int> get() = _priceCounter
//    fun getLiveData(): LiveData<Int>{
//        return _edCounter
//    }

    fun IncrementCount(harga: Int){
        _edCounter.value = _edCounter.value?.plus(1)
        _priceCounter.value = _priceCounter.value?.plus(harga)
    }

    fun DecrementCount(harga: Int){
        _edCounter.value?.let {
            if(it > 0){
                _priceCounter.value = _priceCounter.value?.minus(harga)
                _edCounter.value = _edCounter.value?.minus(1)
            }
        }
    }
}