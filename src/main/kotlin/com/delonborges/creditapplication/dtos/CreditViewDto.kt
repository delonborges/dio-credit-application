package com.delonborges.creditapplication.dtos

import com.delonborges.creditapplication.entities.Credit
import com.delonborges.creditapplication.enums.Status
import java.math.BigDecimal
import java.util.*

data class CreditViewDto(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallments: Int,
    val status: Status,
    val email: String?,
    val income: BigDecimal?,
) {
    constructor(credit: Credit): this(
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallments = credit.numberOfInstallments,
        status = credit.status,
        email = credit.customer?.email,
        income = credit.customer?.income
    )
}
