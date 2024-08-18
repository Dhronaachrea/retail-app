package com.skilrock.retailapp.utils.barcode.gs1

import android.annotation.SuppressLint
import com.skilrock.retailapp.R
import com.skilrock.retailapp.utils.barcode.BarcodeLabel
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import java.text.ParseException

enum class Gs1ApplicationIdentifier(
    override val baseIdentifierCode: String,
    override val identifierDescriptionId: Int,
    override val identifierLength: Int,
    override val minDataLength: Int,
    override val maxDataLength: Int,
    override val dataFormatter: ((String, String) -> String)? = null
) : BarcodeLabel {
    GS1_NAME("", R.string.product_name, 0, 0, 0),
    GS1_RAW("", R.string.parsed_code, 0, 0, 0),
    GS1_MALFORMED("", R.string.malformed_barcode, 0, 0, 0);

    companion object {
        @SuppressLint("SimpleDateFormat")
        private fun dateFormatter(izCode: String, data: String): String = try {
            SimpleDateFormat("dd MMM yyyy").format(
                SimpleDateFormat("yyMMdd", Locale.ROOT).parse(
                    data
                )!!
            )
        } catch (ex: ParseException) {
            data
        }
        
        private fun decimalFormatter(izCode: String, data: String): String =
            BigDecimal(data).movePointLeft(
                izCode.substring(izCode.length - 1, izCode.length).toInt()
            ).toPlainString() 
        
        private fun intFormatter(izCode: String, data: String): String =
                BigDecimal(data).toPlainString()

        fun getGs1Code(barcode: String): Gs1ApplicationIdentifier? =
            values().firstOrNull { elem -> elem.baseIdentifierCode.isNotBlank() && barcode.startsWith(elem.baseIdentifierCode) }
    }
}