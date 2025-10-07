package com.delonborges.creditapplication.dtos

import com.delonborges.creditapplication.entities.Address
import com.delonborges.creditapplication.entities.Customer
import java.math.BigDecimal

data class CustomerDto(
    val firstName: String,
    val lastName: String,
    val document: String,
    val income: BigDecimal,
    val email: String,
    val password: String,
    val zipCode: String,
    val street: String
) {
    fun toEntity() = Customer(
        firstName = this.firstName,
        lastName = this.lastName,
        document = this.document,
        income = this.income,
        email = this.email,
        password = this.password,
        address = Address(
            zipCode = this.zipCode, street = this.street
        ),
    )
}
