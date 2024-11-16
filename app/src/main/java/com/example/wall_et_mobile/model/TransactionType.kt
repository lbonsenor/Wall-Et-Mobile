package com.example.wall_et_mobile.model

import com.example.wall_et_mobile.R

enum class TransactionType(val stringInt: Int, val iconInt: Int) {
    ONLINE_PAYMENT(R.string.online_payment, R.drawable.qrcode_scan),
    TRANSFER_RECEIVED(R.string.transfer_received, R.drawable.payments),
    TRANSFER_SENT(R.string.transfer_sent, R.drawable.payments),
    LOCAL_STORE(R.string.offline_payment, R.drawable.local_mall),
}