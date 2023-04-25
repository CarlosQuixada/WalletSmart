package com.example.walletsmart.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.walletsmart.model.AccountModel

@Dao
interface AccountDAO {
    @Insert
    fun insert(account: AccountModel): Long

    @Update
    fun update(account: AccountModel): Int

    @Delete
    fun delete(account: AccountModel)

    @Query("SELECT * FROM `Account` WHERE id = :id")
    fun get(id: Int): AccountModel

    @Query("SELECT * FROM `Account`")
    fun getAll(): List<AccountModel>
}