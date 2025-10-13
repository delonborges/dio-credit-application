package com.delonborges.creditapplication.controllers

import com.delonborges.creditapplication.dtos.CreditDto
import com.delonborges.creditapplication.dtos.CreditViewDto
import com.delonborges.creditapplication.dtos.CreditViewListDto
import com.delonborges.creditapplication.entities.Credit
import com.delonborges.creditapplication.services.impl.CreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditController(
    private val creditService: CreditService
) {

    @PostMapping
    fun saveCredit(@RequestBody creditDto: CreditDto): ResponseEntity<CreditViewDto> {
        val credit = this.creditService.save(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CreditViewDto(credit))
    }

    @GetMapping()
    fun findAllCreditsByCustomerId(@RequestParam(value = "customerId") customerId: Long): ResponseEntity<List<CreditViewListDto>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(this.creditService.findAllByCustomer(customerId).stream().map { credit: Credit -> CreditViewListDto(credit) }.collect(Collectors.toList()))
    }

    @GetMapping("/{creditCode}")
    fun findCreditByCreditCode(
        @RequestParam(value = "customerId") customerId: Long,
        @PathVariable creditCode: UUID
    ): ResponseEntity<CreditViewDto> {
        val credit = this.creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(
            CreditViewDto(
                credit
            )
        )
    }
}
