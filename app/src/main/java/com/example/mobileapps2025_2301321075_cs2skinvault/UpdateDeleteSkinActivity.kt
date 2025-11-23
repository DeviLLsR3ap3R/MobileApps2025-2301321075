package com.example.mobileapps2025_2301321075_cs2skinvault

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.mobileapps2025_2301321075_cs2skinvault.data.db.AppDatabase
import com.example.mobileapps2025_2301321075_cs2skinvault.data.models.Skin
import com.example.mobileapps2025_2301321075_cs2skinvault.data.repository.ImageRepository
import com.example.mobileapps2025_2301321075_cs2skinvault.data.repository.SkinRepository
import com.example.mobileapps2025_2301321075_cs2skinvault.ui.viewmodel.SkinViewModel
import com.example.mobileapps2025_2301321075_cs2skinvault.ui.viewmodel.SkinViewModelFactory

class UpdateDeleteSkinActivity : AppCompatActivity() {
    private var etSkinName: EditText? = null
    private var etWeapon: EditText? = null
    private var etRarity: EditText? = null
    private var etPrice: EditText? = null
    private var etImageUrl: EditText? = null
    private var ivPreview: ImageView? = null
    private var btnPickImage: Button? = null
    private var btnSaveChanges: Button? = null
    private var btnDeleteSkin: Button? = null
    private var pickedImagePath: String? = null
    private lateinit var imageRepo: ImageRepository

    private lateinit var repository: SkinRepository
    private lateinit var viewModel: SkinViewModel

    private var skinId: Int = -1
    private var currentSkin: Skin? = null

    private val pickImage = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pickedImagePath = imageRepo.saveFromUri(it)
            ivPreview?.load(pickedImagePath)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_update_delete_skin)

        etSkinName = findViewById(R.id.etSkinName)
        etWeapon = findViewById(R.id.etWeapon)
        etRarity = findViewById(R.id.etRarity)
        etPrice = findViewById(R.id.etPrice)
        etImageUrl = findViewById(R.id.etImageUrl)
        ivPreview = findViewById(R.id.ivPreview)
        btnPickImage = findViewById(R.id.btnPickImage)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)
        btnDeleteSkin = findViewById(R.id.btnDeleteSkin)

        imageRepo = ImageRepository(this)

        val dao = AppDatabase.getDatabase(this).skinDao()
        repository = SkinRepository(dao)

        viewModel = viewModels<SkinViewModel> {
            SkinViewModelFactory(repository)
        }.value

        skinId = intent.getIntExtra("skin_id", -1)
        if(skinId != -1) {
            viewModel.getSkinById(skinId) { skin ->
                skin?.let {
                    currentSkin = it
                    etSkinName?.setText(it.name)
                    etWeapon?.setText(it.weapon)
                    etRarity?.setText(it.rarity)
                    etPrice?.setText(it.price.toString())
                    etImageUrl?.setText(it.imageUrl)
                    ivPreview?.load(it.imagePath ?: it.imageUrl )
                }
            }
        } else {
            Toast.makeText(this, "Invalid skin ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnPickImage?.setOnClickListener {
            pickImage.launch(arrayOf("image/*"))
        }

        btnSaveChanges?.setOnClickListener {
            val name = etSkinName?.text.toString().trim()
            val weapon = etWeapon?.text.toString().trim()
            val rarity = etRarity?.text.toString().trim()
            val priceString = etPrice?.text.toString().trim()
            val imageUrl = etImageUrl?.text.toString().trim().ifBlank { null }

            if(name.isEmpty() || weapon.isEmpty() || rarity.isEmpty() || priceString.isEmpty()) {
                Toast.makeText(this, "Please fill all fields that are not marked as optional!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = priceString.toDoubleOrNull() ?: 0.0

            currentSkin?.let { skin ->
                val updatedSkin = skin.copy(
                    name = name,
                    weapon = weapon,
                    rarity = rarity,
                    price = price,
                    imageUrl = imageUrl,
                    imagePath = pickedImagePath ?: skin.imagePath
                )

                viewModel.updateSkin(updatedSkin)
                Toast.makeText(this, "Skin updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnDeleteSkin?.setOnClickListener {
            currentSkin?.let {
                viewModel.deleteSkin(it)
                imageRepo.deletePhoto(it.imagePath)
                Toast.makeText(this, "Skin deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}