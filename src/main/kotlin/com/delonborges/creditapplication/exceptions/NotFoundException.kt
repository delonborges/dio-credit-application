package com.delonborges.creditapplication.exceptions

data class NotFoundException(override val message: String?): RuntimeException(message)
