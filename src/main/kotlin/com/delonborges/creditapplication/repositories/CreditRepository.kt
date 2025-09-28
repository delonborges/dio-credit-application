package com.delonborges.creditapplication.repositories

import com.delonborges.creditapplication.entities.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CreditRepository: JpaRepository<Credit, Long>
