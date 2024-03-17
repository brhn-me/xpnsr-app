package com.brhn.xpnsr.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brhn.xpnsr.data.repository.TransactionRepository

class ViewModelFactory(private val repository: TransactionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}