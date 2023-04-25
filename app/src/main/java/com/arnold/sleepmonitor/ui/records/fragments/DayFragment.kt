package com.arnold.sleepmonitor.ui.records.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arnold.sleepmonitor.databinding.FragmentDayBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter

class DayFragment : Fragment() {
    private var _binding: FragmentDayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setDayLineChart()
        setDayPieChart()

        return root
    }

    private fun setDayLineChart(line: LineChart = binding.dayLineChart) {
        val xValueList = ArrayList<String>()
        val yValueList = ArrayList<Float>()
        val yValueLabel = ArrayList<String>()

        xValueList.add("22:00")
        xValueList.add("23:00")
        xValueList.add("00:00")
        xValueList.add("01:00")
        xValueList.add("02:00")
        xValueList.add("03:00")
        xValueList.add("04:00")
        xValueList.add("05:00")
        xValueList.add("06:00")
        xValueList.add("07:00")
        xValueList.add("08:00")

        yValueList.add(2f)
        yValueList.add(1f)
        yValueList.add(0f)
        yValueList.add(0f)
        yValueList.add(1f)
        yValueList.add(1f)
        yValueList.add(0f)
        yValueList.add(0f)
        yValueList.add(1f)
        yValueList.add(2f)

        yValueLabel.add("Deep")
        yValueLabel.add("Light")
        yValueLabel.add("Awake")

        val lineChartEntry = ArrayList<Entry>()
        for ((i, value) in yValueList.withIndex()) {
            lineChartEntry.add(Entry(i.toFloat(), value))
        }

        val lineDataSet = LineDataSet(lineChartEntry, "Sleep")
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.lineWidth = 3f
        lineDataSet.color = resources.getColor(android.R.color.holo_blue_light)

        val lineData = LineData(lineDataSet)
        line.data = lineData

        line.description.isEnabled = false
        line.setDrawGridBackground(false)
        line.setDrawBorders(false)
        line.setTouchEnabled(false)

        line.xAxis.apply {
            setDrawGridLines(false)
            setDrawAxisLine(false)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return xValueList[value.toInt()]
                }
            }
        }

        line.axisLeft.apply {
            setDrawGridLines(false)
            setDrawAxisLine(false)
            setLabelCount(3, true)
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return yValueLabel[value.toInt()]
                }
            }
        }

        line.axisRight.apply {
            setDrawGridLines(false)
            setDrawAxisLine(false)
            setDrawLabels(false)
        }

        line.invalidate()
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
        pie.setTouchEnabled(false)

        pie.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}