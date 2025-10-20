package com.delonborges.creditapplication.controllers

import com.delonborges.creditapplication.builders.CustomerDtoBuilder
import com.delonborges.creditapplication.repositories.CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerResourceTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL = "/api/customers"
    }

    @BeforeEach
    fun setup() {
        customerRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        customerRepository.deleteAll()
    }

    @Test
    fun `should create customer successfully`() {
        val customerDto = CustomerDtoBuilder().buildCustomer()
        val json = objectMapper.writeValueAsString(customerDto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").isNotEmpty)
            .andExpect(jsonPath("$.firstName").value(customerDto.firstName))
            .andExpect(jsonPath("$.lastName").value(customerDto.lastName))
            .andExpect(jsonPath("$.document").value(customerDto.document))
            .andExpect(jsonPath("$.email").value(customerDto.email))
            .andExpect(jsonPath("$.zipCode").value(customerDto.zipCode))
            .andExpect(jsonPath("$.street").value(customerDto.street))
    }

    @Test
    fun `should not save customer when document exists`() {
        customerRepository.save(CustomerDtoBuilder().buildCustomer().toEntity())
        val customerDto = CustomerDtoBuilder().buildCustomer()
        val json = objectMapper.writeValueAsString(customerDto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.title").value("Conflict"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(409))
            .andExpect(jsonPath("$.exception").value("class org.springframework.dao.DataIntegrityViolationException"))
            .andExpect(jsonPath("$.details").isNotEmpty)
    }

    @Test
    fun `should not save a customer with firstName empty`() {
        val customerDto = CustomerDtoBuilder().buildCustomer(firstName = "")
        val json = objectMapper.writeValueAsString(customerDto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.title").value("Bad Request"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.exception").value("class org.springframework.web.bind.MethodArgumentNotValidException"))
            .andExpect(jsonPath("$.details").isNotEmpty)
    }

    @Test
    fun `should find customer by id`() {
        val customer = customerRepository.save(CustomerDtoBuilder().buildCustomer().toEntity())

        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${customer.id}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.firstName").value(customer.firstName))
            .andExpect(jsonPath("$.lastName").value(customer.lastName))
            .andExpect(jsonPath("$.document").value(customer.document))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.zipCode").value(customer.address.zipCode))
            .andExpect(jsonPath("$.street").value(customer.address.street))
    }

    @Test
    fun `should not find customer by id`() {
        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${1}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.title").value("Unprocessable Entity"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(422))
            .andExpect(jsonPath("$.exception").value("class com.delonborges.creditapplication.exceptions.BusinessException"))
            .andExpect(jsonPath("$.details").isNotEmpty)
    }

    @Test
    fun `should delete customer by id`() {
        val customer = customerRepository.save(CustomerDtoBuilder().buildCustomer().toEntity())

        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/${customer.id}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)

        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${customer.id}").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.title").value("Unprocessable Entity"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(422))
            .andExpect(jsonPath("$.exception").value("class com.delonborges.creditapplication.exceptions.BusinessException"))
            .andExpect(jsonPath("$.details").isNotEmpty)
    }

    @Test
    fun `should not delete customer by id`() {
        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.title").value("Unprocessable Entity"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(422))
            .andExpect(jsonPath("$.exception").value("class com.delonborges.creditapplication.exceptions.BusinessException"))
            .andExpect(jsonPath("$.details").isNotEmpty)
    }

    @Test
    fun `should update customer by id`() {
        val customer = customerRepository.save(CustomerDtoBuilder().buildCustomer().toEntity())
        val customerUpdated = CustomerDtoBuilder().buildCustomer(firstName = "UpdatedName")
        val json = objectMapper.writeValueAsString(customerUpdated)

        mockMvc.perform(MockMvcRequestBuilders.patch("$URL?id=${customer.id}").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.firstName").value("UpdatedName"))
    }

    @Test
    fun `should not update customer by id`() {
        val customerUpdated = CustomerDtoBuilder().buildCustomer()
        val json = objectMapper.writeValueAsString(customerUpdated)

        mockMvc.perform(MockMvcRequestBuilders.patch("$URL?id=999").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.title").value("Unprocessable Entity"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(422))
            .andExpect(jsonPath("$.exception").value("class com.delonborges.creditapplication.exceptions.BusinessException"))
            .andExpect(jsonPath("$.details").isNotEmpty)
    }
}
