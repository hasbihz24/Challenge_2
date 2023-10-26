package com.example.challenge_2.model

data class MenuResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)