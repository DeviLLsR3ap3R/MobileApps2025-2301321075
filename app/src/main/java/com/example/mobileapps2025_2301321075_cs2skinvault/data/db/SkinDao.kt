package com.example.mobileapps2025_2301321075_cs2skinvault.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mobileapps2025_2301321075_cs2skinvault.data.models.Skin

@Dao
interface SkinDao {
    @Insert
    fun insertSkin(skin: Skin)

    @Update
    fun updateSkin(skin: Skin)

    @Delete
    fun deleteSkin(skin: Skin)

    @Query("SELECT * FROM skins")
    fun getAllSkins(): List<Skin>
}