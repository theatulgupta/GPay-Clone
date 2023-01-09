package com.fyndings.gpayclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return binding.root
    }

}