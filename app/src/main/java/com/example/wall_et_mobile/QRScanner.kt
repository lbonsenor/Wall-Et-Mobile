package com.example.wall_et_mobile

import android.content.Context
import android.util.Log
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow

class QRScanner(
    appContext: Context
) {
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE
        )
        .build()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options)
    val barCodeResults = MutableStateFlow<String?>(null)

    fun startScan(){
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                barCodeResults.value = barcode.rawValue
                Log.d("QR", barcode.rawValue.toString())
            }
            .addOnFailureListener { e -> }
    }

//    suspend fun startScan() {
//        try {
//            val result = scanner.startScan().await()
//            barCodeResults.value = result.rawValue
//        } catch (e: Exception) {
//
//
//        }
//    }



}