package com.delonborges.creditapplication.dtos

import com.delonborges.creditapplication.annotations.MaxMonthsFromNow
import com.delonborges.creditapplication.entities.Credit
import com.delonborges.creditapplication.entities.Customer
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @field:NotNull(message = "Invalid input") var creditValue: BigDecimal,
    @field:Future @field:MaxMonthsFromNow(months = 3) val dayFirstInstallment: LocalDate,
    @field:Max(value = 48) val numberOfInstallments: Int,
    @field:NotNull(message = "Invalid input") var customerId: Long
) {
    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
