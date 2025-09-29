package com.delonborges.creditapplication.services.impl

import com.delonborges.creditapplication.entities.Credit
import com.delonborges.creditapplication.repositories.CreditRepository
import com.delonborges.creditapplication.services.iface.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
): ICreditService {
    override fun save(credit: Credit): Credit {
        credit.apply { customer = customerService.findById(credit.customer?.id!!) }
        return creditRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> {
        return this.creditRepository.findAllByCustomerId(customerId)
    }

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit = this.creditRepository.findByCreditCode(creditCode) ?: throw RuntimeException("Credit code $creditCode not found")
        return if (credit.customer?.id == customerId) credit else throw RuntimeException("Customer not found")
    }
}
