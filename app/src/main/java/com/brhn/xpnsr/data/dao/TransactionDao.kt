package com.brhn.xpnsr.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.brhn.xpnsr.models.Transaction

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT EXISTS(SELECT 1 FROM transactions LIMIT 1)")
    suspend fun isDatabaseEmpty(): Boolean
}