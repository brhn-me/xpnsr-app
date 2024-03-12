package com.brhn.xpnsr.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.brhn.xpnsr.models.Transaction

@Dao
interface TransactionDao {
    @Insert
    fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun countTransactions(): Int
}