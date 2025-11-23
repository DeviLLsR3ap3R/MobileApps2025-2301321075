package com.example.mobileapps2025_2301321075_cs2skinvault.data.repository

import androidx.lifecycle.LiveData
import com.example.mobileapps2025_2301321075_cs2skinvault.data.db.SkinDao
import com.example.mobileapps2025_2301321075_cs2skinvault.data.models.Skin

class SkinRepository(private val skinDao: SkinDao) {
    val allSkins: LiveData<List<Skin>> = skinDao.getAllSkins()
    suspend fun getSkinById(id: Int): Skin? {
        return skinDao.getSkinById(id)
    }

    suspend fun insertSkin(skin: Skin) {
        skinDao.insertSkin(skin)
    }

    suspend fun updateSkin(skin: Skin) {
        skinDao.updateSkin(skin)
    }

    suspend fun deleteSkin(skin: Skin) {
        skinDao.deleteSkin(skin)
    }
}