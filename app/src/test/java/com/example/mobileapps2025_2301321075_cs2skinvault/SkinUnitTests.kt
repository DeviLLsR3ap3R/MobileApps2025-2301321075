package com.example.mobileapps2025_2301321075_cs2skinvault

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

data class Skin(
    val name: String,
    val weapon: String,
    val rarity: String,
    val price: Double,
    val imageUrl: String?,
    val imagePath: String?
)

class FakeSkinRepository {
    private val skins = mutableListOf<Skin>()

    fun addSkin(skin: Skin) {
        skins.add(skin)
    }

    fun removeSkin(skin: Skin) {
        skins.remove(skin)
    }

    fun getSkins(): List<Skin> = skins.toList()

    fun getSkinByName(name: String): Skin? = skins.find { it.name == name }
}

class FakeSkinViewModel(private val repo: FakeSkinRepository) {

    fun insertSkin(skin: Skin) = repo.addSkin(skin)

    fun updateSkin(oldSkin: Skin, newSkin: Skin) {
        val existing = repo.getSkinByName(oldSkin.name)
        if (existing != null) {
            repo.removeSkin(existing)
            repo.addSkin(newSkin)
        }
    }

    fun deleteSkin(skin: Skin) = repo.removeSkin(skin)

    fun getAllSkins(): List<Skin> = repo.getSkins()
}

class SkinUnitTests {
    private lateinit var repo: FakeSkinRepository
    private lateinit var viewModel: FakeSkinViewModel

    @Before
    fun setup() {
        repo = FakeSkinRepository()
        viewModel = FakeSkinViewModel(repo)
    }

    @Test
    fun addSkin_increasesCount() {
        val initial = repo.getSkins().size
        val skin = Skin("Redline", "AK-47", "Classified", 120.0, null, null)
        repo.addSkin(skin)
        assertEquals(initial + 1, repo.getSkins().size)
    }

    @Test
    fun removeSkin_decreasesCount() {
        val skin = Skin("Howl", "M4A4", "Covert", 5000.0, null, null)
        repo.addSkin(skin)
        val sizeBefore = repo.getSkins().size
        repo.removeSkin(skin)
        assertEquals(sizeBefore - 1, repo.getSkins().size)
    }

    @Test
    fun insertSkin() {
        val skin = Skin("Asiimov", "AK-47", "Covert", 120.0, null, null)
        viewModel.insertSkin(skin)
        val all = viewModel.getAllSkins()
        assertEquals(1, all.size)
        assertEquals("Asiimov", all[0].name)
    }

    @Test
    fun updateSkin() {
        val skin = Skin("Asiimov", "AK-47", "Covert", 120.0, null, null)
        viewModel.insertSkin(skin)
        val updated = skin.copy(name = "Jaguar")
        viewModel.updateSkin(skin, updated)
        val all = viewModel.getAllSkins()
        assertEquals(1, all.size)
        assertEquals("Jaguar", all[0].name)
    }

    @Test
    fun deleteSkin() {
        val skin = Skin("Hyper Beast", "AWP", "Covert", 120.0, null, null)
        viewModel.insertSkin(skin)
        viewModel.deleteSkin(skin)
        val all = viewModel.getAllSkins()
        assertTrue(all.isEmpty())
    }
}