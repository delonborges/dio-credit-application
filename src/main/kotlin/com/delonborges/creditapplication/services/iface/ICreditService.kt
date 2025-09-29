package com.delonborges.creditapplication.services.iface

import com.delonborges.creditapplication.entities.Credit
import java.util.*

interface ICreditService {
    fun save(credit: Credit): Credit
    fun findAllByCustomer(customerId: Long): List<Credit>
    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit
}
