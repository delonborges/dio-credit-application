package com.delonborges.creditapplication.dtos

import com.delonborges.creditapplication.entities.Customer
import java.math.BigDecimal

data class CustomerViewDto(
    val firstName: String,
    val lastName: String,
    val document: String,
    val income: BigDecimal,
    val email: String,
    val zipCode: String,
    val street: String,
    val id: Long?,
) {
    constructor(customer: Customer): this(
        firstName = customer.firstName,
        lastName = customer.lastName,
        document = customer.document,
        income = customer.income,
        email = customer.email,
        zipCode = customer.address.zipCode,
        street = customer.address.street,
        id = customer.id,
    )
}
