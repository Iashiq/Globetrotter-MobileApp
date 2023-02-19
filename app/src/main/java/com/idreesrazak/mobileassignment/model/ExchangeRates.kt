package com.idreesrazak.mobileassignment.model

data class ExchangeRates(
    val amount: Int,
    val base: String?,
    val date: String?,
    val rates: Rates?
)