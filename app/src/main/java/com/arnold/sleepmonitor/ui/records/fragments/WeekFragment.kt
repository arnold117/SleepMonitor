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

        bind.apply {
            weekDuration.text = weekData.aveDuration.toString()
            startEndDate.text =
                "${data.first().startTime.split("T")[0]} ~ ${data.last().endTime.split("T")[0]}"
            meanRating.text = weekData.aveSleepScore.toString()
            meanRespQuality.text = weekData.aveRespirationQualityScore.toString()
            meanLux.text = String.format("%.2f", weekData.aveLux)
            meanVol.text = String.format("%.2f", weekData.aveVolume)
            meanEnvScore.text = weekData.aveEnvironmentScore.toString()
        }
    }

    private fun setDurationLineChart(bind: FragmentWeekBinding = binding) {

    }

    private fun setPieChart(bind: FragmentWeekBinding = binding) {

    }

    private fun setEnvLineChart(bind: FragmentWeekBinding = binding) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}