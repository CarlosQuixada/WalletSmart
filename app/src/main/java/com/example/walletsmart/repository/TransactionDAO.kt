package com.example.walletsmart.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.walletsmart.model.TransactionModel

@Dao
interface TransactionDAO {
    @Insert
    fun insert(transaction: TransactionModel): Long

    @Update
    fun update(transaction: TransactionModel): Int

    @Delete
    fun delete(transaction: TransactionModel)


    @Query("SELECT * FROM `Transaction` WHERE id = :id")
    fun get(id: Int): TransactionModel

    @Query("SELECT * FROM `Transaction`")
    fun getAll(): List<TransactionModel>

    @Query("SELECT * FROM `Transaction` WHERE type = 1")
    fun getRevenue(): List<TransactionModel>

    @Query("SELECT * FROM `Transaction` WHERE type = 0")
    fun getExpenses(): List<TransactionModel>
}