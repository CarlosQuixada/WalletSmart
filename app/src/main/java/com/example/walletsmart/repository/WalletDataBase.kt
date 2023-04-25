package com.example.walletsmart.repository

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.walletsmart.model.AccountModel
import com.example.walletsmart.model.TransactionModel

//@Database(entities = [TransactionModel::class, AccountModel::class], version = 1)

@Database(
    version = 3,
    entities = [TransactionModel::class, AccountModel::class]
)
abstract class WalletDataBase : RoomDatabase() {
    abstract fun transactionDAO(): TransactionDAO
    abstract fun accountDAO(): AccountDAO

    companion object {
        private lateinit var INSTANCE: WalletDataBase

        fun getDataBase(context: Context): WalletDataBase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(WalletDataBase::class) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context,
                            WalletDataBase::class.java, "wallet_smart_db"
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()

                }
            }
            return INSTANCE
        }

    }
}