package com.example.rc_calculator.view.screens.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import com.example.rc_calculator.APP
import com.example.rc_calculator.MainActivity
import com.example.rc_calculator.R
import com.example.rc_calculator.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    val PREFERENCES_NAME = "main"
    val THEME_NAME = "theme"
    val AppThemeLightCodeStyle = 0
    val AppThemeDarkCodeStyle = 1
    val AppThemeDefault = 2

    lateinit var binding : FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init () {

        loadAppTheme()?.let { context?.setTheme(it) }

        binding.radioButton1.setOnClickListener{
            saveAppTheme(AppThemeDefault)
            activity?.recreate()
        }

        binding.radioButton2.setOnClickListener{
            saveAppTheme(AppThemeDarkCodeStyle)
            activity?.recreate()
        }

        binding.radioButton3.setOnClickListener{
            saveAppTheme(AppThemeLightCodeStyle)
            activity?.recreate()
        }

        binding.buttonThemesOk.setOnClickListener{
            APP.navController.navigate(R.id.action_settingFragment_to_startFragment)
        }


    }



    private fun codeStyleToStyleID(codeStyle: Int): Int {
        return when (codeStyle) {
            AppThemeLightCodeStyle -> R.style.AppThemeLight
            AppThemeDarkCodeStyle -> R.style.AppThemeDark
            AppThemeDefault -> R.style.MyCoolStyle
            else -> R.style.MyCoolStyle
        }

    }

    private fun loadAppTheme(): Int? {
        val codeTheme = context?.getSharedPreferences(PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE)
            ?.getInt(THEME_NAME, AppThemeDefault)
        return codeTheme?.let {
            codeStyleToStyleID(it)
        }
    }

    private fun saveAppTheme(code: Int) {
        context?.getSharedPreferences(PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE)
            ?.edit()
            ?.putInt(THEME_NAME, code)
            ?.apply()
    }



}