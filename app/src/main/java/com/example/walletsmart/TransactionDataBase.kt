package com.example.walletsmart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.walletsmart.model.TransactionModel

@Database(entities = [TransactionModel::class], version = 1)
abstract class TransactionDataBase : RoomDatabase() {
    abstract fun transactionDAO(): TransactionDAO

    companion object {
        private lateinit var INSTANCE: TransactionDataBase

        fun getDataBase(context: Context): TransactionDataBase {
            if (!::INSTANCE.isInitialized) {
                synchronized(TransactionDataBase::class) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context,
                            TransactionDataBase::class.java,"wallet_smart_db")
                            .addMigrations()
                            .allowMainThreadQueries()
                            .build()

                }
            }
            return INSTANCE
        }

    }
}