package com.delonborges.creditapplication.services

import com.delonborges.creditapplication.builders.CustomerBuilder
import com.delonborges.creditapplication.entities.Customer
import com.delonborges.creditapplication.exceptions.BusinessException
import com.delonborges.creditapplication.repositories.CustomerRepository
import com.delonborges.creditapplication.services.impl.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    lateinit var customerRepository: CustomerRepository

    @InjectMockKs
    lateinit var customerService: CustomerService

    @Test
    fun `should create customer successfully`() {
        val customer: Customer = CustomerBuilder().buildCustomer()
        every { customerRepository.save(any()) } returns customer

        val customerSaved = customerService.save(customer)

        assertThat(customerSaved).isNotNull
        assertThat(customerSaved).isSameAs(customer)
        verify(exactly = 1) { customerRepository.save(customer) }
    }

    @Test
    fun `should find customer by Id`() {
        val id: Long = Random().nextLong()
        val customer: Customer = CustomerBuilder().buildCustomer(id = id)
        every { customerRepository.findById(id) } returns Optional.of(customer)

        val customerFound = customerService.findById(id)

        assertThat(customerFound).isNotNull
        assertThat(customerFound).isSameAs(customer)
        assertThat(customerFound).isExactlyInstanceOf(Customer::class.java)
        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun `should throw exception when trying to find a nonexistent customer`() {
        val id: Long = Random().nextLong()
        every { customerRepository.findById(id) } returns Optional.empty()

        assertThatExceptionOfType(BusinessException::class.java).isThrownBy { customerService.findById(id) }.withMessage("Id $id not found")
        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun `should delete customer by Id`() {
        val id: Long = Random().nextLong()
        val customer: Customer = CustomerBuilder().buildCustomer(id = id)
        every { customerRepository.findById(id) } returns Optional.of(customer)
        every { customerRepository.delete(customer) } just runs

        customerService.delete(id)

        verify(exactly = 1) { customerRepository.findById(id) }
        verify(exactly = 1) { customerRepository.delete(customer) }
    }
}
