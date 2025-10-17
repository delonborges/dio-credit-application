package com.delonborges.creditapplication.validators

import com.delonborges.creditapplication.annotations.MaxMonthsFromNow
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate

class MaxMonthsFromNowValidator: ConstraintValidator<MaxMonthsFromNow, LocalDate> {

    private var maxMonths: Int = 0

    override fun initialize(constraintAnnotation: MaxMonthsFromNow) {
        this.maxMonths = constraintAnnotation.months
    }

    override fun isValid(
        value: LocalDate?,
        context: ConstraintValidatorContext
    ): Boolean {
        return value == null || !value.isAfter(LocalDate.now().plusMonths(maxMonths.toLong()))
    }

}
