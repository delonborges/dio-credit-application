package com.delonborges.creditapplication.services

import com.delonborges.creditapplication.builders.CreditBuilder
import com.delonborges.creditapplication.builders.CustomerBuilder
import com.delonborges.creditapplication.entities.Credit
import com.delonborges.creditapplication.entities.Customer
import com.delonborges.creditapplication.exceptions.BusinessException
import com.delonborges.creditapplication.exceptions.NotFoundException
import com.delonborges.creditapplication.repositories.CreditRepository
import com.delonborges.creditapplication.services.impl.CreditService
import com.delonborges.creditapplication.services.impl.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.util.*
import kotlin.test.Test

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {

    @MockK
    lateinit var creditRepository: CreditRepository

    @MockK
    lateinit var customerService: CustomerService

    @InjectMockKs
    lateinit var creditService: CreditService

    @Test
    fun `should create credit successfully`() {
        //Given
        val credit: Credit = CreditBuilder().buildCredit()
        every { customerService.findById(any()) } returns credit.customer!!
        every { creditRepository.save(any()) } returns credit

        //When
        val creditSaved = creditService.save(credit)

        //Then
        assertThat(creditSaved).isNotNull
        assertThat(creditSaved).isSameAs(credit)
        verify(exactly = 1) { customerService.findById(credit.customer?.id!!) }
        verify(exactly = 1) { creditRepository.save(credit) }
    }

    @Test
    fun `should throw exception when customer is not found while saving credit`() {
        val credit: Credit = CreditBuilder().buildCredit()
        every { customerService.findById(any()) } throws BusinessException("Id ${credit.customer?.id} not found")

        assertThatExceptionOfType(BusinessException::class.java).isThrownBy { creditService.save(credit) }.withMessage("Id ${credit.customer?.id} not found")
        verify(exactly = 1) { customerService.findById(credit.customer?.id!!) }
        verify(exactly = 0) { creditRepository.save(any()) }
    }

    @Test
    fun `should find all credits by customer`() {
        val customer: Customer = CustomerBuilder().buildCustomer()
        val firstCredit: Credit = CreditBuilder().buildCredit(customer = customer)
        val secondCredit: Credit = CreditBuilder().buildCredit(customer = customer)
        every { creditRepository.findAllByCustomerId(customer.id!!) } returns listOf(firstCredit, secondCredit)

        val credits = creditService.findAllByCustomer(customer.id!!)

        assertThat(credits).isNotNull
        assertThat(credits).hasSize(2)
        assertThat(credits).containsExactly(firstCredit, secondCredit)
        verify(exactly = 1) { creditRepository.findAllByCustomerId(customer.id!!) }
    }

    @Test
    fun `should find credit by credit code`() {
        val credit: Credit = CreditBuilder().buildCredit()
        every { creditRepository.findByCreditCode(credit.creditCode) } returns credit

        val creditFound = creditService.findByCreditCode(credit.customer?.id!!, credit.creditCode)

        assertThat(creditFound).isNotNull
        assertThat(creditFound).isSameAs(credit)
        verify(exactly = 1) { creditRepository.findByCreditCode(credit.creditCode) }
    }

    @Test
    fun `should throw exception when trying to find a nonexistent credit`() {
        val creditCode: UUID = UUID.randomUUID()
        every { creditRepository.findByCreditCode(creditCode) } returns null

        assertThatExceptionOfType(NotFoundException::class.java).isThrownBy { creditService.findByCreditCode(1, creditCode) }
            .withMessage("Credit code $creditCode not found")
        verify(exactly = 1) { creditRepository.findByCreditCode(creditCode) }
    }

    @Test
    fun `should throw exception when customer id does not match credit owner`() {
        val credit: Credit = CreditBuilder().buildCredit()
        val differentCustomerId = 999L
        every { creditRepository.findByCreditCode(credit.creditCode) } returns credit

        assertThatExceptionOfType(NotFoundException::class.java).isThrownBy { creditService.findByCreditCode(differentCustomerId, credit.creditCode) }
            .withMessage("Customer not found")
        verify(exactly = 1) { creditRepository.findByCreditCode(credit.creditCode) }
    }

    @Test
    fun `should throw exception when credit customer is null`() {
        val credit: Credit = CreditBuilder().buildCredit()
        credit.customer = null
        val customerId = 1L
        every { creditRepository.findByCreditCode(credit.creditCode) } returns credit

        assertThatExceptionOfType(NotFoundException::class.java).isThrownBy { creditService.findByCreditCode(customerId, credit.creditCode) }
            .withMessage("Customer not found")
        verify(exactly = 1) { creditRepository.findByCreditCode(credit.creditCode) }
    }

    @Test
    fun `should throw exception when credit customer id is null`() {
        //Given
        val customer: Customer = CustomerBuilder().buildCustomer(id = null)
        val credit: Credit = CreditBuilder().buildCredit(customer = customer)
        val customerId = 1L
        every { creditRepository.findByCreditCode(credit.creditCode) } returns credit

        //When & Then
        assertThatExceptionOfType(NotFoundException::class.java)
            .isThrownBy { creditService.findByCreditCode(customerId, credit.creditCode) }
            .withMessage("Customer not found")
        verify(exactly = 1) { creditRepository.findByCreditCode(credit.creditCode) }
    }
}
