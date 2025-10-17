package com.delonborges.creditapplication.exceptions

data class BusinessException(override val message: String?): RuntimeException(message)
