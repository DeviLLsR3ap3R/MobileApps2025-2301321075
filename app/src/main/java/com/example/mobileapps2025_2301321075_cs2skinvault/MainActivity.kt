package com.example.mobileapps2025_2301321075_cs2skinvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapps2025_2301321075_cs2skinvault.data.db.AppDatabase
import com.example.mobileapps2025_2301321075_cs2skinvault.data.repository.SkinRepository
import com.example.mobileapps2025_2301321075_cs2skinvault.databinding.ActivityMainBinding
import com.example.mobileapps2025_2301321075_cs2skinvault.ui.adapter.SkinAdapter
import com.example.mobileapps2025_2301321075_cs2skinvault.ui.theme.MobileApps20252301321075CS2SkinVaultTheme
import com.example.mobileapps2025_2301321075_cs2skinvault.ui.viewmodel.SkinViewModel
import com.example.mobileapps2025_2301321075_cs2skinvault.ui.viewmodel.SkinViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var rvSkins: RecyclerView? = null
    private var addSkinButton: FloatingActionButton? = null
    private var adapter: SkinAdapter? = null
    private lateinit var repository: SkinRepository
    private lateinit var viewModel: SkinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        rvSkins = findViewById(R.id.rvSkins)
        addSkinButton = findViewById(R.id.btnAddSkin)

        val dao = AppDatabase.getDatabase(this).skinDao()
        repository = SkinRepository(dao)

        viewModel = viewModels<SkinViewModel> {
            SkinViewModelFactory(repository)
        }.value

        adapter = SkinAdapter()
        rvSkins?.layoutManager = LinearLayoutManager(this)
        rvSkins?.adapter = adapter
        rvSkins?.setHasFixedSize(true)

        viewModel.allSkins.observe(this) {
            skins -> adapter?.submitList(skins)
        }

        addSkinButton?.setOnClickListener {

        }

        adapter?.lambdaOnClick = {

        }
    }
}