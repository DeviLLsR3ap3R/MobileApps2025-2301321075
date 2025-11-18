package com.example.mobileapps2025_2301321075_cs2skinvault.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import com.example.mobileapps2025_2301321075_cs2skinvault.data.models.Skin
import com.example.mobileapps2025_2301321075_cs2skinvault.data.repository.SkinRepository
import kotlinx.coroutines.launch

class SkinViewModel(private val repository: SkinRepository) : ViewModel() {
    val allSkins: LiveData<List<Skin>> = repository.allSkins

    fun insertSkin(skin: Skin) = viewModelScope.launch {
        repository.insertSkin(skin)
    }

    fun updateSkin(skin: Skin) = viewModelScope.launch {
        repository.updateSkin(skin)
    }

    fun deleteSkin(skin: Skin) = viewModelScope.launch {
        repository.deleteSkin(skin)
    }
}

class SkinViewModelFactory(private val repository: SkinRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SkinViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SkinViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}