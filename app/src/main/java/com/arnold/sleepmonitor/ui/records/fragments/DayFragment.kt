package com.arnold.sleepmonitor.ui.records.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arnold.sleepmonitor.databinding.FragmentDayBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class DayFragment : Fragment() {
    private var _binding: FragmentDayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dayBar = binding.dayBarChart
        val dayPie = binding.dayPieChart

        setDayPieChart()

        return root
    }

    private fun setDayPieChart(pie: PieChart = binding.dayPieChart) {
        val xValueList = ArrayList<String>()
        val yValueList = ArrayList<Float>()

        xValueList.add("Deep")
        xValueList.add("Light")
        xValueList.add("Awake")

        yValueList.add(0.5f)
        yValueList.add(0.3f)
        yValueList.add(0.2f)

        val pieChartEntry = ArrayList<PieEntry>()
        for ((i, value) in yValueList.withIndex()) {
            pieChartEntry.add(PieEntry(value, xValueList[i]))
        }

        val pieDataSet = PieDataSet(pieChartEntry, "Sleep")
        pieDataSet.setColors(
            resources.getColor(android.R.color.holo_blue_light),
            resources.getColor(android.R.color.holo_green_light),
            resources.getColor(android.R.color.holo_red_light)
        )
        pieDataSet.valueTextSize = 16f
        pieDataSet.sliceSpace = 3f

        val pieData = PieData(pieDataSet)
        pie.data = pieData

        pie.description.isEnabled = false
        pie.legend.isEnabled = false

        pie.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}