package com.brhn.xpnsr.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.brhn.xpnsr.data.db.AppDatabase
import com.brhn.xpnsr.data.repository.TransactionRepository
import com.brhn.xpnsr.models.Transaction
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository
    val allTransactions: LiveData<List<Transaction>>

    init {
        val transactionsDao = AppDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(transactionsDao)
        allTransactions = repository.allTransactions
    }

    fun insert(transaction: Transaction) = viewModelScope.launch {
        repository.insert(transaction)
    }
}