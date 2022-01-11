package com.example.rc_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.rc_calculator.databinding.ActivityMainBinding
import com.example.rc_calculator.view.screens.start.StartViewModel



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // крепим Биндинг
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // крепим константу
        APP = this

        // крепим Навигатор
        navController = Navigation.findNavController(this, R.id.nav_fragment)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.butSettings) {
            APP.navController.navigate(R.id.action_startFragment_to_settingFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showErrorMessage() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }

}
