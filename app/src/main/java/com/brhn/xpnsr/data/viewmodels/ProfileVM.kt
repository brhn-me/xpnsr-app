package com.brhn.xpnsr.data.viewmodels

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileVM(application: Application) : AndroidViewModel(application) {
    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri

    fun updateProfileImageUri(uri: Uri) = viewModelScope.launch {
        _profileImageUri.value = uri
    }
}