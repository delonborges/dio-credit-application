package com.delonborges.creditapplication.services.iface

import com.delonborges.creditapplication.entities.Customer

interface ICustomerService {
    fun save(customer: Customer): Customer
    fun findById(id: Long): Customer
    fun delete(id: Long)
}
