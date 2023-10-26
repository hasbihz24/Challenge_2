package com.example.challenge_2.model

data class OrderRequest(
    var username : String = "",
    var total : Int = 0,
    var orders : List<Order>
)
