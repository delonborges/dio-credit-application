package com.delonborges.creditapplication.builders

import com.delonborges.creditapplication.dtos.CustomerDto
import java.math.BigDecimal

class CustomerDtoBuilder {

    fun buildCustomer(
        firstName: String = "Delon",
        lastName: String = "Barbosa Borges",
        document: String = "71711175617",
        income: BigDecimal = BigDecimal.valueOf(10000.00),
        email: String = "a@b.com",
        password: String = "123456",
        street: String = "Rua das Tabajaras",
        zipCode: String = "00000-000"
    ) = CustomerDto(
        firstName = firstName,
        lastName = lastName,
        document = document,
        income = income,
        email = email,
        password = password,
        zipCode = zipCode,
        street = street
    )
}
