package com.delonborges.creditapplication.repositories

import com.delonborges.creditapplication.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, Long>
