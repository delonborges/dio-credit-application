package com.delonborges.creditapplication.controllers

import com.delonborges.creditapplication.dtos.CustomerDto
import com.delonborges.creditapplication.dtos.CustomerUpdateDto
import com.delonborges.creditapplication.dtos.CustomerViewDto
import com.delonborges.creditapplication.entities.Customer
import com.delonborges.creditapplication.services.impl.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
class CustomerController(
    private val customerService: CustomerService
) {

    @PostMapping
    fun saveCustomer(@RequestBody @Valid customerDto: CustomerDto): ResponseEntity<CustomerViewDto> {
        val customer = this.customerService.save(customerDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerViewDto(customer))
    }

    @GetMapping("/{id}")
    fun findCustomerById(@PathVariable id: Long): ResponseEntity<CustomerViewDto> {
        val customer: Customer = this.customerService.findById(id)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerViewDto(customer))
    }

    @DeleteMapping("/{id}")
    fun deleteCustomerById(@PathVariable id: Long): ResponseEntity<Void> {
        this.customerService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping
    fun updateCustomerById(
        @RequestParam(value = "id") id: Long,
        @RequestBody @Valid customerUpdateDto: CustomerUpdateDto
    ): ResponseEntity<CustomerViewDto> {
        val customer = this.customerService.findById(id)
        val customerToUpdate = customerUpdateDto.toEntity(customer)
        val customerUpdated = this.customerService.save(customerToUpdate)
        return ResponseEntity.status(HttpStatus.OK).body(
            CustomerViewDto(
                customerUpdated
            )
        )
    }
}
