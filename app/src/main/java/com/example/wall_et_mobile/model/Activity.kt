package com.example.wall_et_mobile.model

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import java.sql.Timestamp
import java.time.LocalDate
import java.time.ZoneId

data class Activity(
    val amount: CurrencyAmount,
    val transactionTime: Timestamp,
    val name: String,
    val transactionType: TransactionType,
    val paymentType: PaymentType = PaymentType.AVAILABLE,
) {
    fun toLocalDate() : LocalDate{
        return transactionTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }

    companion object {
        val Test = Activity(
            name = "Farmacity",
            transactionType = TransactionType.ONLINE_PAYMENT,
            amount = CurrencyAmount(100.0, Currency.getInstance("ARS")),
            transactionTime = Timestamp(System.currentTimeMillis())
        )
    }
}
