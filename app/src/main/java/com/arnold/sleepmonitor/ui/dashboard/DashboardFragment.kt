package com.arnold.sleepmonitor.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arnold.sleepmonitor.R
import com.arnold.sleepmonitor.databinding.FragmentDashboardBinding
import com.arnold.sleepmonitor.ui.dashboard.activities.About
import com.arnold.sleepmonitor.ui.dashboard.activities.SensorTesting
import com.arnold.sleepmonitor.ui.dashboard.activities.StorageTesting

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView = binding.dashListView
        val dash = resources.getStringArray(R.array.dash)
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            dash)
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
//            val selectedItem = parent.getItemAtPosition(position) as String
//            Toast.makeText(requireContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()

            when (position) {
                1 -> {
                    val intent = android.content.Intent(requireContext(), SensorTesting::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = android.content.Intent(requireContext(), StorageTesting::class.java)
                    startActivity(intent)
                }
                3 -> {
                    val intent = android.content.Intent(requireContext(), About::class.java)
                    startActivity(intent)
                }
                else -> {
                    Toast.makeText(requireContext(), "Coming soon!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}