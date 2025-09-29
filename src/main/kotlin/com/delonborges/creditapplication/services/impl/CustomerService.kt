package com.delonborges.creditapplication.services.impl

import com.delonborges.creditapplication.entities.Customer
import com.delonborges.creditapplication.repositories.CustomerRepository
import com.delonborges.creditapplication.services.iface.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
): ICustomerService {
    override fun save(customer: Customer): Customer {
        return customerRepository.save(customer)
    }

    override fun findById(id: Long): Customer {
        return customerRepository.findById(id).orElseThrow { RuntimeException("Id $id not found") }
    }

    override fun delete(id: Long) {
        customerRepository.deleteById(id)
    }
}
