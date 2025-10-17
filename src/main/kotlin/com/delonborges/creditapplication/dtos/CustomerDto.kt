package com.delonborges.creditapplication.dtos

import com.delonborges.creditapplication.entities.Address
import com.delonborges.creditapplication.entities.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
    @field:NotEmpty(message = "Invalid input") val firstName: String,
    @field:NotEmpty(message = "Invalid input") val lastName: String,
    @field:NotEmpty(message = "Invalid input") @field:CPF(message = "Invalid document (CPF)") val document: String,
    @field:NotNull(message = "Invalid input") var income: BigDecimal,
    @field:NotEmpty(message = "Invalid input") @field:Email(message = "Invalid input") val email: String,
    @field:NotEmpty(message = "Invalid input") val password: String,
    @field:NotEmpty(message = "Invalid input") val zipCode: String,
    @field:NotEmpty(message = "Invalid input") val street: String
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
