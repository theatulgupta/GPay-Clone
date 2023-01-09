package com.fyndings.gpayclone

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fyndings.gpayclone.databinding.FragmentLoginBinding
import com.fyndings.gpayclone.databinding.FragmentOTPBinding

class OTPFragment : Fragment() {
    private var _binding: FragmentOTPBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOTPBinding.inflate(inflater, container, false);
        binding.txtVerify.setOnClickListener {
            findNavController().navigate(R.id.action_OTPFragment_to_dashboardFragment)
        }
        return binding.root
    }

}