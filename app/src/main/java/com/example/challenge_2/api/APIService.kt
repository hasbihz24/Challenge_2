package com.example.challenge_2.api

import com.example.challenge_2.model.MenuResponse
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("listmenu")
    fun getMenu(): Call<MenuResponse>

    @GET("listmenu")
    suspend fun getMenu2(): MenuResponse
}