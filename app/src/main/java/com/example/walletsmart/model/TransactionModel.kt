package com.example.walletsmart.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Transaction")
class TransactionModel {
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo
    var description: String = ""

    @ColumnInfo
    var value: Float = Float.MIN_VALUE

    @ColumnInfo
    var date: String = ""

    @ColumnInfo
    var type: Boolean = true
}