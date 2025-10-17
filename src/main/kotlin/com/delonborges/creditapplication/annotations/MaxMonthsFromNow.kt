package com.delonborges.creditapplication.annotations

import com.delonborges.creditapplication.validators.MaxMonthsFromNowValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [MaxMonthsFromNowValidator::class])
annotation class MaxMonthsFromNow(
    val months: Int,
    val message: String = "The date must be at most {months} months from now",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
