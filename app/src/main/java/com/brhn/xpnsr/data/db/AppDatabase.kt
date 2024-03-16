package com.brhn.xpnsr.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brhn.xpnsr.data.converters.TransactionTypeConverter
import com.brhn.xpnsr.data.dao.TransactionDao
import com.brhn.xpnsr.data.getSampleTransactions
import com.brhn.xpnsr.models.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Transaction::class], version = 1, exportSchema = false)
@TypeConverters(TransactionTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }

        fun prepopulateIfEmpty(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                val database = getDatabase(context)
                val transactionDao = database.transactionDao()

                if (transactionDao.countTransactions() == 0) { // Assuming this method exists
                    transactionDao.insertAll(getSampleTransactions())
                }
            }
        }
    }


}



