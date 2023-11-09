package com.example.challenge_2.repos

import com.example.challenge_2.api.APIService

class MainRepository(private val apiService: APIService) {
    suspend fun getMenu() = apiService.getMenu2()
}