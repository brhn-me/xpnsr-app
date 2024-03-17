package com.brhn.xpnsr.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brhn.xpnsr.data.repository.TransactionRepository
import com.brhn.xpnsr.models.Transaction
import kotlinx.coroutines.launch

class TransactionVM(private val repository: TransactionRepository) : ViewModel() {
    private val _transaction = MutableLiveData<Transaction?>()
    val transaction: LiveData<Transaction?> = _transaction

    fun loadTransaction(transactionId: Long?) {
        if (transactionId == null) {
            _transaction.value = null
            return
        }

        viewModelScope.launch {
            _transaction.value = repository.getTransactionByIdSuspend(transactionId)
        }
    }

    fun saveTransaction(transaction: Transaction) = viewModelScope.launch {
        if (transaction.id == 0L) {
            repository.insert(transaction)
        } else {
            repository.update(transaction)
        }
    }

    fun deleteTransaction(transactionId: Long) = viewModelScope.launch {
        repository.delete(transactionId)
    }
}