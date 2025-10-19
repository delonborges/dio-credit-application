package com.delonborges.creditapplication

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import kotlin.test.assertNotNull

@SpringBootTest
class CreditApplicationTests {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Test
    fun `context loads`() {
        assertNotNull(applicationContext)
    }
}
