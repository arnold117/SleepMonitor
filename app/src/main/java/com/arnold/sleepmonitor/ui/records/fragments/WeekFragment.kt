package com.arnold.sleepmonitor.ui.records.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arnold.sleepmonitor.FileHandler
import com.arnold.sleepmonitor.data_structure.NightData
import com.arnold.sleepmonitor.data_structure.WeekData
import com.arnold.sleepmonitor.databinding.FragmentWeekBinding
import com.arnold.sleepmonitor.process.Converter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import org.apache.poi.sl.usermodel.Line

class WeekFragment : Fragment() {
    private var _binding: FragmentWeekBinding? = null
    private val binding get() = _binding!!
    private val fileHandler = FileHandler()

    private val data = getData()
    private val weekData = getWeekData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setDataText()
        setDurationLineChart()
        setPieChart()
        setEnvLineChart()

        return root
    }

    private fun getData() : List<NightData> {
        val converter = Converter()
        return converter.dataFrame2Night(
            fileHandler.readDataFrame("test", "nights")
        )
    }

    private fun getWeekData() : WeekData {
        val converter = Converter()
        return converter.night2Week(data)
    }

    private fun setDataText(bind: FragmentWeekBinding = binding) {
        val hour = weekData.aveDuration / 60
        val min = weekData.aveDuration % 60

        bind.apply {
            weekDuration.text = "${hour}h${min}m"
            startEndDate.text =
                "${data.first().startTime.split("T")[0]} ~ ${data.last().endTime.split("T")[0]}"
            meanRating.text = weekData.aveSleepScore.toString()
            meanRespQuality.text = weekData.aveRespirationQualityScore.toString()
            meanLux.text = String.format("%.2f", weekData.aveLux)
            meanVol.text = String.format("%.2f", weekData.aveVolume)
            meanEnvScore.text = weekData.aveEnvironmentScore.toString()
        }
    }

    private fun setDurationLineChart(line: LineChart = binding.durationLineChart) {
        val timeList = ArrayList<String>()
        val durationList = ArrayList<Int>()

        data.map { it ->
            val date = it.startTime.split("T")[0].split("-")
            timeList.add("${date[1]}/${date[2]}")
            durationList.add(it.duration)
        }

        val lineEntry = ArrayList<Entry>()
        for (i in 0 until timeList.size) {
            lineEntry.add(Entry(i.toFloat(), durationList[i].toFloat()))
        }

        val lineDataSet = LineDataSet(lineEntry, "Duration")
        lineDataSet.color = resources.getColor(android.R.color.holo_blue_light)
        lineDataSet.setDrawCircles(false)
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.lineWidth = 3f

        val lineData = LineData(lineDataSet)
        line.data = lineData

        line.description.isEnabled = false
        line.setDrawGridBackground(false)
        line.setDrawBorders(false)
        line.setTouchEnabled(false)

        line.xAxis.apply {
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = object :ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return timeList[value.toInt()]
                }
            }
        }

        line.axisLeft.apply {
            setDrawGridLines(false)
        }

        line.axisRight.apply {
            setDrawGridLines(false)
            setDrawLabels(false)
        }

        line.invalidate()
    }

    private fun setPieChart(pie: PieChart = binding.averageRatioPieChart) {
        val labelList = ArrayList<String>()
        val ratioList = ArrayList<Float>()

        labelList.add("Deep")
        labelList.add("Light")

        ratioList.add(weekData.aveDeepRatio.toFloat())
        ratioList.add(weekData.aveLightRatio.toFloat())

        if (weekData.aveAwakeCount > 1) {
            labelList.add("Awake")
            ratioList.add(weekData.aveAwakeCount.toFloat() / data.size.toFloat())
        }

        val pieEntry = ArrayList<PieEntry>()
        for ((i, value) in ratioList.withIndex()) {
            pieEntry.add(PieEntry(value, labelList[i]))
        }

        val pieDataSet = PieDataSet(pieEntry, "Ratio")
        pieDataSet.colors = listOf(
            resources.getColor(android.R.color.holo_blue_light),
            resources.getColor(android.R.color.holo_green_light),
            resources.getColor(android.R.color.holo_red_light)
        )
        pieDataSet.valueTextSize = 16f
        pieDataSet.sliceSpace = 3f

        val pieData = PieData(pieDataSet)
        pieData.setValueFormatter(PercentFormatter(pie))
        pie.data = pieData

        pie.description.isEnabled = false
        pie.legend.isEnabled = false
        pie.setUsePercentValues(true)

        pie.invalidate()
    }

    private fun setEnvLineChart(envLine : LineChart = binding.envLineChart) {
        val timeList = ArrayList<String>()
        val luxList = ArrayList<Float>()
        val volList = ArrayList<Float>()

        data.map { it ->
            val date = it.startTime.split("T")[0].split("-")
            timeList.add("${date[1]}/${date[2]}")
            luxList.add(it.meanLux.toFloat())
            volList.add(it.meanVolume.toFloat())
        }

        val luxEntry = ArrayList<Entry>()
        for((i, value) in luxList.withIndex()) {
            luxEntry.add(Entry(i.toFloat(), value))
        }

        val volEntry = ArrayList<Entry>()
        for((i, value) in volList.withIndex()) {
            volEntry.add(Entry(i.toFloat(), value))
        }

        val luxDataSet = LineDataSet(luxEntry, "Lux")
        luxDataSet.color = resources.getColor(android.R.color.holo_blue_light)
        luxDataSet.setDrawCircles(false)
        luxDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        luxDataSet.lineWidth = 3f
        luxDataSet.setDrawValues(false)
        luxDataSet.axisDependency = YAxis.AxisDependency.LEFT

        val volDataSet = LineDataSet(volEntry, "Volume")
        volDataSet.color = resources.getColor(android.R.color.holo_green_light)
        volDataSet.setDrawCircles(false)
        volDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        volDataSet.lineWidth = 3f
        volDataSet.setDrawValues(false)
        volDataSet.axisDependency = YAxis.AxisDependency.RIGHT

        val lineData = LineData(luxDataSet, volDataSet)
        envLine.data = lineData

        envLine.description.isEnabled = false
        envLine.setDrawGridBackground(false)
        envLine.setDrawBorders(false)
        envLine.setTouchEnabled(false)

        envLine.xAxis.apply {
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = object :ValueFormatter() {
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

        envLine.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}