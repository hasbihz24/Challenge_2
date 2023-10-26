package com.example.challenge_2.api

import com.example.challenge_2.model.MenuResponse
import com.example.challenge_2.model.Order
import com.example.challenge_2.model.OrderRequest
import com.example.challenge_2.model.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @GET("listmenu")
    fun getMenu(): Call<MenuResponse>

    @GET("listmenu")
    suspend fun getMenu2(): MenuResponse

    @POST("order-menu")
    fun postOrder(@Body order: OrderRequest): Call<OrderResponse>

}