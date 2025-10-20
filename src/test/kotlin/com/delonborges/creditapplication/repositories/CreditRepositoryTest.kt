package com.delonborges.creditapplication.repositories

import com.delonborges.creditapplication.builders.CreditBuilder
import com.delonborges.creditapplication.builders.CustomerBuilder
import com.delonborges.creditapplication.entities.Credit
import com.delonborges.creditapplication.entities.Customer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {

    @Autowired
    lateinit var creditRepository: CreditRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var firstCredit: Credit
    private lateinit var secondCredit: Credit

    @BeforeEach
    fun setup() {
        customer = testEntityManager.persist(CustomerBuilder().buildCustomer())
        firstCredit = testEntityManager.persist(CreditBuilder().buildCredit(customer = customer))
        secondCredit = testEntityManager.persist(CreditBuilder().buildCredit(customer = customer))
    }

    @Test
    fun `should find credit by credit code`() {
        val firstCreditCode = UUID.fromString("11111111-1111-1111-1111-111111111111")
        val secondCreditCode = UUID.fromString("22222222-2222-2222-2222-222222222222")
        firstCredit.creditCode = firstCreditCode
        secondCredit.creditCode = secondCreditCode

        val firstCredit: Credit = creditRepository.findByCreditCode(firstCreditCode)!!
        val secondCredit: Credit = creditRepository.findByCreditCode(secondCreditCode)!!

        assertThat(firstCredit).isNotNull
        assertThat(firstCredit).isSameAs(this.firstCredit)
        assertThat(secondCredit).isNotNull
        assertThat(secondCredit).isSameAs(this.secondCredit)
    }

    @Test
    fun `should find all credits by customer id`() {
        val customerId = 1L
        val credits = creditRepository.findAllByCustomerId(customerId)

        assertThat(credits).isNotEmpty
        assertThat(credits.size).isEqualTo(2)
        assertThat(credits).containsExactlyInAnyOrder(firstCredit, secondCredit)
    }
}
