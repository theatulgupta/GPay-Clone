package com.fyndings.gpayclone

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fyndings.gpayclone.databinding.FragmentDashboardBinding
import com.fyndings.gpayclone.databinding.FragmentLoginBinding

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().window.statusBarColor = this.resources.getColor(R.color.theme_blue)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.root
    }


}