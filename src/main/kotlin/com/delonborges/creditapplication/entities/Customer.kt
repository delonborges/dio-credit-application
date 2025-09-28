package com.delonborges.creditapplication.entities

data class Customer(
    var firstName: String = "",
    var lastName: String = "",
    val document: String,
    var email: String = "",
    var password: String = "",
    var address: Address = Address(),
    var credits: List<Credit> = mutableListOf(),
    val id: Long? = null
)
