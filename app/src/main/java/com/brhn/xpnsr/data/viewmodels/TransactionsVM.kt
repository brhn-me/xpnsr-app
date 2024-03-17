package com.brhn.xpnsr.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.brhn.xpnsr.data.db.AppDatabase
import com.brhn.xpnsr.data.repository.TransactionRepository
import com.brhn.xpnsr.models.Transaction

class TransactionsVM(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository
    val transactions: LiveData<List<Transaction>>


    init {
        val transactionsDao = AppDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(transactionsDao)
        transactions = repository.allTransactions
    }
}

