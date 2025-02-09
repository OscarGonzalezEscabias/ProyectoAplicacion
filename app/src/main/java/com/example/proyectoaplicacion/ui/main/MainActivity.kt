package com.example.proyectoaplicacion.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoaplicacion.R
import com.example.proyectoaplicacion.databinding.ActivityMainBinding
import com.example.proyectoaplicacion.ui.about.AboutFragment
import com.example.proyectoaplicacion.ui.login.LoginActivity
import com.example.proyectoaplicacion.ui.recyclerview.RecyclerViewFragment
import com.example.proyectoaplicacion.ui.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        if (!viewModel.isUserLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.recyclerViewFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, RecyclerViewFragment())
                        .commit()
                    true
                }
                R.id.settingsFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, SettingsFragment())
                        .commit()
                    true
                }
                R.id.aboutFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment,  AboutFragment())
                        .commit()
                    true
                }
                R.id.logout -> {
                    viewModel.logout()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}