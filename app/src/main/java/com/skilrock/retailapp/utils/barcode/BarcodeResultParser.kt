package com.skilrock.retailapp.utils.barcode

import com.skilrock.retailapp.utils.barcode.gs1.Gs1Parser


class BarcodeResultParser {

    fun parseBarcode(barcode: String, format: BarcodeFormat): Barcode? = when(format){
        BarcodeFormat.GS1_128 -> Gs1Parser().parseBarcode(barcode)
    }

    fun parseBarcode(barcodes: List<String>, format: BarcodeFormat): Barcode? = when(format){
        BarcodeFormat.GS1_128 -> barcodes.map { Gs1Parser().parseBarcode(it)}.reduce{ acc, elem -> acc + elem }
    }

}