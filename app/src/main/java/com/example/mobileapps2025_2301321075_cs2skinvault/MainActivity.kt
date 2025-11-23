package com.example.mobileapps2025_2301321075_cs2skinvault

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapps2025_2301321075_cs2skinvault.data.db.AppDatabase
import com.example.mobileapps2025_2301321075_cs2skinvault.data.repository.SkinRepository
import com.example.mobileapps2025_2301321075_cs2skinvault.ui.adapter.SkinAdapter
import com.example.mobileapps2025_2301321075_cs2skinvault.ui.viewmodel.SkinViewModel
import com.example.mobileapps2025_2301321075_cs2skinvault.ui.viewmodel.SkinViewModelFactory

class MainActivity : AppCompatActivity() {
    private var rvSkins: RecyclerView? = null
    private var addSkinButton: Button? = null
    private var adapter: SkinAdapter? = null
    private lateinit var repository: SkinRepository
    private lateinit var viewModel: SkinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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
            val intent = Intent(this, AddSkinActivity::class.java)
            startActivity(intent)
        }

        adapter?.lambdaOnClick = { skin ->
            skin?.let {
                val i = Intent(this, UpdateDeleteSkinActivity::class.java)
                i.putExtra("skin_id", it.id)
                startActivity(i)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_theme -> {
                toggleTheme()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleTheme() {
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

}