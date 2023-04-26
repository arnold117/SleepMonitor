package com.arnold.sleepmonitor.ui.records.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arnold.sleepmonitor.FileHandler
import com.arnold.sleepmonitor.data_structure.NightData
import com.arnold.sleepmonitor.data_structure.SingleUnitData
import com.arnold.sleepmonitor.databinding.FragmentDayBinding
import com.arnold.sleepmonitor.process.Calculator
import com.arnold.sleepmonitor.process.Converter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class DayFragment : Fragment() {
    private var _binding: FragmentDayBinding? = null
    private val binding get() = _binding!!
    private val handler = FileHandler()

    private val data = getData()
    private val nightData = getNightData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setDataText()
        setDayLineChart()
        setDayPieChart()
        setBodyBarChart()
        setEnvLineChart()

        return root
    }

    private fun getData() : List<SingleUnitData> {
        val converter = Converter()
        return converter.dataFrame2SingleUnit(
            handler.readDataFrame("test", "singleUnit2")
        )
    }

    private fun getNightData() : NightData {
        val converter = Converter()
        return converter.singleUnit2Night(data)
    }

    private fun setDataText(bind: FragmentDayBinding = binding) {
        val time = nightData.duration
        val hour = time / 60
        val minute = time % 60

        bind.apply {
            dayDuration.text = "$hour:$minute"
            dayStartEndTime.text =
                "${nightData.startTime.split("T")[1]} - ${nightData.endTime.split("T")[1]}"
            dayRating.text = nightData.sleepScore.toString()
            dayRespQuality.text = nightData.respirationQualityScore.toString()
            dayMeanLux.text = String.format("%.2f", nightData.meanLux)
            dayMeanVol.text = String.format("%.2f", nightData.meanVolume)
            dayEnvScore.text = nightData.environmentScore.toString()
        }
    }

    private fun setDayLineChart(line: LineChart = binding.dayLineChart) {
        val xValueList = ArrayList<String>()
        val yValueList = ArrayList<Float>()
        val yValueLabel = ArrayList<String>()

        data.map { it ->
            val time = it.time.split("T")[1].split(":")
            val hm = "${time[0]}:${time[1]}"
            xValueList.add(hm)
            yValueList.add(it.status.toFloat()) }

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

        yValueList.add(nightData.deepSleepRatio.toFloat())
        yValueList.add(nightData.lightSleepRatio.toFloat())

        if (nightData.awakeCount > 1) {
            xValueList.add("Awake")
            val awake = nightData.awakeCount.toFloat() / data.size.toFloat()
            yValueList.add(awake)
        }

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
        pieData.setValueFormatter(PercentFormatter(pie))
        pie.data = pieData

        pie.description.isEnabled = false
        pie.legend.isEnabled = false
        pie.setUsePercentValues(true)

        pie.invalidate()
    }

    private fun setBodyBarChart(bar: BarChart = binding.dayBarChart) {
        val timeList = ArrayList<String>()
        val moveList = ArrayList<Float>()
        val snoreList = ArrayList<Float>()

        data.map { it ->
            val time = it.time.split("T")[1].split(":")
            val hm = "${time[0]}:${time[1]}"
            timeList.add(hm)
            moveList.add(it.movesCount.toFloat())
            snoreList.add(it.snoreCount.toFloat())
        }

        val moveEntry = ArrayList<BarEntry>()
        for ((i, value) in moveList.withIndex()) {
            moveEntry.add(BarEntry(i.toFloat(), value))
        }

        val snoreEntry = ArrayList<BarEntry>()
        for ((i, value) in snoreList.withIndex()) {
            snoreEntry.add(BarEntry(i.toFloat(), value))
        }

        val moveDataSet = BarDataSet(moveEntry, "Body Movement")
        moveDataSet.color = resources.getColor(android.R.color.holo_blue_light)
        moveDataSet.valueTextSize = 16f

        val snoreDataSet = BarDataSet(snoreEntry, "Snore")
        snoreDataSet.color = resources.getColor(android.R.color.holo_green_light)
        snoreDataSet.valueTextSize = 16f

        val barData = BarData(moveDataSet, snoreDataSet)
        barData.barWidth = 0.5f
        bar.data = barData

        bar.description.isEnabled = false
        bar.setDrawGridBackground(false)
        bar.setDrawBorders(false)
        bar.setTouchEnabled(false)

        bar.xAxis.apply {
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return timeList[value.toInt()]
                }
            }
        }

        bar.axisLeft.apply {
            setDrawGridLines(false)
        }

        bar.axisRight.apply {
            setDrawGridLines(false)
            setDrawLabels(false)
        }

        bar.invalidate()
    }

    private fun setEnvLineChart(envLine : LineChart = binding.envLineChart) {
        val timeList = ArrayList<String>()
        val illuminationList = ArrayList<Float>()
        val voiceVolumeList = ArrayList<Float>()

        data.map { it ->
            val time = it.time.split("T")[1].split(":")
            val hm = "${time[0]}:${time[1]}"
            timeList.add(hm)
            illuminationList.add(it.meanLux.toFloat())
            voiceVolumeList.add(it.meanEnvironmentVolume.toFloat())
        }

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