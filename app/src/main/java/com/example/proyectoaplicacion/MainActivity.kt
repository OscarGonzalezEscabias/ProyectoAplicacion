package com.example.proyectoaplicacion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyectoaplicacion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bindingMain: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        setSupportActionBar(bindingMain.appBarMain.toolbar)

        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", null)

        bindingMain.navigationView.getHeaderView(0).findViewById<TextView>(R.id.usernameTextView).text = userName

        val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.recyclerViewFragment, R.id.settingsFragment, R.id.aboutFragment),
            bindingMain.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        bindingMain.navigationView.setupWithNavController(navController)

        bindingMain.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.recyclerViewFragment -> {
                    navController.navigate(R.id.recyclerViewFragment)
                    true
                }
                R.id.settingsFragment -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                R.id.aboutFragment -> {
                    navController.navigate(R.id.aboutFragment)
                    true
                }
                R.id.logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }
    }

    private fun logout() {
        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove("acceso")
        editor.apply()

        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
