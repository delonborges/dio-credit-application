package com.delonborges.creditapplication.repositories

import com.delonborges.creditapplication.entities.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CreditRepository: JpaRepository<Credit, Long> {
    @Query(value = "SELECT * FROM credit WHERE customer_id = ?1", nativeQuery = true)
    fun findAllByCustomerId(customerId: Long): List<Credit>
    fun findByCreditCode(creditCode: UUID): Credit?
}
