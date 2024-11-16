package com.example.wall_et_mobile.model

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import java.sql.Timestamp

data class Activity(
    val amount: CurrencyAmount,
    val transactionTime: Timestamp,
    val name: String,
    val transactionType: TransactionType,
    val paymentType: PaymentType = PaymentType.AVAILABLE,
) {
    companion object {
        val Test = Activity(
            name = "Farmacity",
            transactionType = TransactionType.ONLINE_PAYMENT,
            amount = CurrencyAmount(100.0, Currency.getInstance("ARS")),
            transactionTime = Timestamp(System.currentTimeMillis())
        )
    }
}
