package com.delonborges.creditapplication.builders

import com.delonborges.creditapplication.entities.Address
import com.delonborges.creditapplication.entities.Customer
import java.math.BigDecimal

class CustomerBuilder {

    fun buildCustomer(
        firstName: String = "Delon",
        lastName: String = "Barbosa Borges",
        document: String = "12345678900",
        income: BigDecimal = BigDecimal.valueOf(10000.00),
        email: String = "a@b.com",
        password: String = "123456",
        street: String = "Rua das Tabajaras",
        zipCode: String = "00000-000",
        id: Long? = 1L
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        document = document,
        income = income,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode, street = street
        ),
        id = id
    )
}
