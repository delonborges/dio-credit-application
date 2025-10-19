package com.delonborges.creditapplication.builders

import com.delonborges.creditapplication.entities.Credit
import com.delonborges.creditapplication.entities.Customer
import com.delonborges.creditapplication.enums.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

class CreditBuilder {

    fun buildCredit(
        id: Long = 1L,
        creditCode: UUID = UUID.randomUUID(),
        creditValue: BigDecimal = BigDecimal.valueOf(1000.0),
        dayFirstInstallment: LocalDate = LocalDate.now(),
        numberOfInstallments: Int = 3,
        status: Status = Status.IN_PROGRESS,
        customer: Customer = CustomerBuilder().buildCustomer(),
    ) = Credit(
        id = id,
        creditCode = creditCode,
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        status = status,
        customer = customer,
    )
}
