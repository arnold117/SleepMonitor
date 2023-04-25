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
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

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
        setEnvLineChart()

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
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return xValueList[value.toInt()]
                }
            }
        }

        line.axisLeft.apply {
            setDrawGridLines(false)
            setLabelCount(3, true)
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return yValueLabel[value.toInt()]
                }
            }
        }

        line.axisRight.apply {
            setDrawGridLines(false)
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

    private fun setEnvLineChart(envLine : LineChart = binding.envLineChart) {
        val timeList = ArrayList<String>()
        val illuminationList = ArrayList<Float>()
        val voiceVolumeList = ArrayList<Float>()

        timeList.add("22:00")
        timeList.add("23:00")
        timeList.add("00:00")
        timeList.add("01:00")
        timeList.add("02:00")
        timeList.add("03:00")
        timeList.add("04:00")
        timeList.add("05:00")
        timeList.add("06:00")
        timeList.add("07:00")
        timeList.add("08:00")

        illuminationList.add(0.5f)
        illuminationList.add(0.3f)
        illuminationList.add(0.2f)
        illuminationList.add(0.1f)
        illuminationList.add(0.2f)
        illuminationList.add(0.1f)
        illuminationList.add(0.2f)
        illuminationList.add(0.1f)
        illuminationList.add(0.2f)
        illuminationList.add(0.2f)
        illuminationList.add(0.3f)

        voiceVolumeList.add(5f)
        voiceVolumeList.add(3f)
        voiceVolumeList.add(2f)
        voiceVolumeList.add(1f)
        voiceVolumeList.add(2f)
        voiceVolumeList.add(3f)
        voiceVolumeList.add(4f)
        voiceVolumeList.add(5f)
        voiceVolumeList.add(6f)
        voiceVolumeList.add(7f)
        voiceVolumeList.add(8f)

        val illuminationLineChartEntry = ArrayList<Entry>()
        for ((i, value) in illuminationList.withIndex()) {
            illuminationLineChartEntry.add(Entry(i.toFloat(), value))
        }

        val voiceVolumeLineChartEntry = ArrayList<Entry>()
        for ((i, value) in voiceVolumeList.withIndex()) {
            voiceVolumeLineChartEntry.add(Entry(i.toFloat(), value))
        }

        val illuminationLineDataSet = LineDataSet(illuminationLineChartEntry, "Illumination")
        illuminationLineDataSet.setDrawCircles(false)
        illuminationLineDataSet.setDrawValues(false)
        illuminationLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        illuminationLineDataSet.lineWidth = 3f
        illuminationLineDataSet.color = resources.getColor(android.R.color.holo_blue_light)
        illuminationLineDataSet.axisDependency = YAxis.AxisDependency.LEFT

        val voiceVolumeLineDataSet = LineDataSet(voiceVolumeLineChartEntry, "Voice Volume")
        voiceVolumeLineDataSet.setDrawCircles(false)
        voiceVolumeLineDataSet.setDrawValues(false)
        voiceVolumeLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        voiceVolumeLineDataSet.lineWidth = 3f
        voiceVolumeLineDataSet.color = resources.getColor(android.R.color.holo_green_light)
        voiceVolumeLineDataSet.axisDependency = YAxis.AxisDependency.RIGHT

        val lineData = LineData(illuminationLineDataSet, voiceVolumeLineDataSet)
        envLine.data = lineData

        envLine.description.isEnabled = false
        envLine.setDrawGridBackground(false)
        envLine.setDrawBorders(false)
        envLine.setTouchEnabled(false)

        envLine.xAxis.apply {
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return timeList[value.toInt()]
                }
            }
        }

        envLine.axisLeft.apply {
            setDrawGridLines(false)
            textColor = resources.getColor(android.R.color.holo_blue_light)
        }

        envLine.axisRight.apply {
            setDrawGridLines(false)
            textColor = resources.getColor(android.R.color.holo_green_light)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}