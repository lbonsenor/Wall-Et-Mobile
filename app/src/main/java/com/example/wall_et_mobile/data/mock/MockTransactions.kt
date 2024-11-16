import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import com.example.wall_et_mobile.model.Activity
import com.example.wall_et_mobile.model.TransactionType
import java.sql.Timestamp

object MockTransactions {
    val sampleTransactions = listOf(
        Activity(
            name = "Farmacity",
            transactionType = TransactionType.ONLINE_PAYMENT,
            amount = CurrencyAmount(100.0, Currency.getInstance("ARS")),
            transactionTime = Timestamp(System.currentTimeMillis())
        ),
        Activity(
            name = "Juan",
            transactionType = TransactionType.TRANSFER_RECEIVED,
            amount = CurrencyAmount(500.0, Currency.getInstance("ARS")),
            transactionTime = Timestamp(System.currentTimeMillis() - 3600000)
        ),
        Activity(
            name = "McDonald's",
            transactionType = TransactionType.LOCAL_STORE,
            amount = CurrencyAmount(250.0, Currency.getInstance("ARS")),
            transactionTime = Timestamp(System.currentTimeMillis() - 7200000)
        ),
        Activity(
            name = "Noah Cefalta",
            transactionType = TransactionType.TRANSFER_SENT,
            amount = CurrencyAmount(1000.0, Currency.getInstance("ARS")),
            transactionTime = Timestamp(System.currentTimeMillis() - 86400000)
        )
    )
}