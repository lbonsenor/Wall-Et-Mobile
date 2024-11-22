
import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import com.example.wall_et_mobile.data.model.PaymentType
import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.TransactionType
import java.sql.Date

object MockTransactions {
    val sampleTransactions = listOf(
        Transaction(
            name = "Farmacity",
            transactionType = TransactionType.ONLINE_PAYMENT,
            amount = CurrencyAmount(100.0, Currency.getInstance("ARS")),
            transactionId = 0,
            balanceBefore = 0f,
            balanceAfter = 0f,
            pending = false,
            paymentType = PaymentType.BALANCE,
            card = null,
            linkUuid = null,
            createdAt = Date(System.currentTimeMillis()),
            updatedAt = Date(System.currentTimeMillis())
        ),
        Transaction(
            name = "Juan",
            transactionType = TransactionType.TRANSFER_RECEIVED,
            amount = CurrencyAmount(500.0, Currency.getInstance("ARS")),
            createdAt = Date(System.currentTimeMillis() - 3600000),
            updatedAt = Date(System.currentTimeMillis() - 3600000),
            transactionId = 1,
            balanceBefore = 0f,
            balanceAfter = 0f,
            pending = false,
            paymentType = PaymentType.BALANCE,
            card = null,
            linkUuid = null
        ),
        Transaction(
            name = "McDonald's",
            transactionType = TransactionType.LOCAL_STORE,
            amount = CurrencyAmount(250.0, Currency.getInstance("ARS")),
            createdAt = Date(System.currentTimeMillis() - 7200000),
            updatedAt = Date(System.currentTimeMillis() - 7200000),
            transactionId = 1,
            balanceBefore = 0f,
            balanceAfter = 0f,
            pending = false,
            paymentType = PaymentType.BALANCE,
            card = null,
            linkUuid = null
        ),
        Transaction(
            name = "Noah Cefalta",
            transactionType = TransactionType.TRANSFER_SENT,
            amount = CurrencyAmount(1000.0, Currency.getInstance("ARS")),
            createdAt = Date(System.currentTimeMillis() - 8640000),
            updatedAt = Date(System.currentTimeMillis() - 8640000),
            transactionId = 1,
            balanceBefore = 0f,
            balanceAfter = 0f,
            pending = false,
            paymentType = PaymentType.BALANCE,
            card = null,
            linkUuid = null
        )
    )
}