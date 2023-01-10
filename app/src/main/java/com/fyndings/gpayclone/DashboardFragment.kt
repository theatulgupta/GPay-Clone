package com.fyndings.gpayclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.fyndings.gpayclone.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private lateinit var peopleList: ArrayList<GridItem>
    private lateinit var businessList: ArrayList<GridItem>

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().window.statusBarColor = this.resources.getColor(R.color.theme_blue)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false);

//        People RecyclerView
        binding.peopleRecyclerView.setHasFixedSize(true)
        binding.peopleRecyclerView.layoutManager = GridLayoutManager(this.activity, 4)

//        Businesses RecyclerView
        binding.businessesRecyclerView.setHasFixedSize(true)
        binding.businessesRecyclerView.layoutManager = GridLayoutManager(this.activity, 4)

        peopleList = ArrayList()
        addPeoples()

        businessList = ArrayList()
        addBusinesses()
//
        binding.peopleRecyclerView.adapter = GirdItemAdapter(peopleList)
        binding.businessesRecyclerView.adapter = GirdItemAdapter(businessList)

        return binding.root
    }

    private fun addPeoples() {
        peopleList.add(GridItem(R.color.theme_blue, "Blue Color"))
        peopleList.add(GridItem(R.color.theme_green, "Green Color"))
        peopleList.add(GridItem(R.color.theme_red, "Red Color"))
        peopleList.add(GridItem(R.color.theme_gray, "Grey Color"))
        peopleList.add(GridItem(R.color.theme_gray, "Grey Color"))
        peopleList.add(GridItem(R.color.theme_red, "Red Color"))
        peopleList.add(GridItem(R.color.theme_green, "Green Color"))
        peopleList.add(GridItem(R.color.theme_blue, "Blue Color"))
    }

    private fun addBusinesses() {
        businessList.add(GridItem(R.color.theme_red, "Red Color"))
        businessList.add(GridItem(R.color.theme_gray, "Grey Color"))
        businessList.add(GridItem(R.color.theme_green, "Green Color"))
        businessList.add(GridItem(R.color.theme_blue, "Blue Color"))
    }

}