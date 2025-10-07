package com.delonborges.creditapplication.dtos

import com.delonborges.creditapplication.entities.Credit
import java.math.BigDecimal
import java.util.*

data class CreditViewListDto(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallments: Int
) {
    constructor(credit: Credit): this(
        creditCode = credit.creditCode, creditValue = credit.creditValue, numberOfInstallments = credit.numberOfInstallments
    )
}
