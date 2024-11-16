package com.example.wall_et_mobile.model

import com.example.wall_et_mobile.R

enum class PaymentType(val stringInt: Int) {
    CREDIT_CARD(R.string.credit),
    DEBIT_CARD(R.string.debit),
    AVAILABLE(R.string.available_balance)
}
