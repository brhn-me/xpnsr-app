package com.brhn.xpnsr.data.repository

import androidx.lifecycle.LiveData
import com.brhn.xpnsr.data.dao.TransactionDao
import com.brhn.xpnsr.models.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {
    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    suspend fun update(transaction: Transaction) {
        transactionDao.update(transaction)
    }

    fun getTransactionById(id: Long): LiveData<Transaction> {
        return transactionDao.getTransactionById(id)
    }

    suspend fun getTransactionByIdSuspend(id: Long): Transaction? {
        return transactionDao.getTransactionByIdSuspend(id)
    }

    suspend fun delete(transactionId: Long) {
        return transactionDao.deleteById(transactionId)
    }
}