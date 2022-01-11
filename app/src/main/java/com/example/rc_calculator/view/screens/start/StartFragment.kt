package com.example.rc_calculator.view.screens.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rc_calculator.R
import com.example.rc_calculator.databinding.FragmentStartBinding

class StartFragment : Fragment() {



    lateinit var binding : FragmentStartBinding
//    private val viewModel: StartViewModel by lazy {
//        ViewModelProvider(this).get(StartViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root

    }



}