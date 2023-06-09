package com.example.walletsmart.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Account")
class AccountModel {
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo
    var name: String = ""
}