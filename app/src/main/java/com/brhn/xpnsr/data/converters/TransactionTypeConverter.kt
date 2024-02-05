package com.brhn.xpnsr.data.converters

import androidx.room.TypeConverter
import com.brhn.xpnsr.models.TransactionType

class TransactionTypeConverter {

    @TypeConverter
    fun fromTransactionType(value: TransactionType): String {
        return value.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}