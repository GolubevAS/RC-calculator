package com.example.rc_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rc_calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var viewModel: ViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)

        viewModel.apply {
            this!!.getInvalidMessage().observe(
                this@MainActivity,
                Observer { shouldShow ->
                    if (shouldShow != null && shouldShow) {
                        APP.showErrorMessage()
                    }
                })
        }

        val binding : ActivityMainBinding? = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.let{
            it.viewModel = viewModel
            it.setLifecycleOwner(this)
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }
}