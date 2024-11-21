package com.example.wall_et_mobile.data.model

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import java.sql.Timestamp
import java.time.LocalDate
import java.time.ZoneId

data class Transaction(
    val transactionId: Int = 0,
    val amount: CurrencyAmount,
    val transactionTime: Timestamp,
    val transactionType: TransactionType,
    val name: String,
) {
    fun toLocalDate() : LocalDate{
        return transactionTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }

    companion object {
        val Test = Transaction(
            name = "Farmacity",
            transactionType = TransactionType.ONLINE_PAYMENT,
            amount = CurrencyAmount(100.0, Currency.getInstance("ARS")),
            transactionTime = Timestamp(System.currentTimeMillis())
        )
    }
}
