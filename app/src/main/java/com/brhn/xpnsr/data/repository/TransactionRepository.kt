package com.brhn.xpnsr.data.repository

import androidx.lifecycle.LiveData
import com.brhn.xpnsr.data.dao.TransactionDao
import com.brhn.xpnsr.models.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {
    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }
}