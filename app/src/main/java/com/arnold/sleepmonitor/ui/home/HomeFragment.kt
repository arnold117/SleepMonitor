package com.arnold.sleepmonitor.ui.home

import android.annotation.SuppressLint
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arnold.sleepmonitor.Cache
import com.arnold.sleepmonitor.FileHandler
import com.arnold.sleepmonitor.data_structure.NightData
import com.arnold.sleepmonitor.data_structure.SingleUnitData
import com.arnold.sleepmonitor.databinding.FragmentHomeBinding
import com.arnold.sleepmonitor.process.Converter
import com.arnold.sleepmonitor.recorder.*
import com.arnold.sleepmonitor.ui.home.activities.SleepActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val TAG = "HomeFragment"
    val handler = FileHandler()

    private val data = getData()
    private val nightData = getNightData()

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val overview = binding.homeCardSleepOverview
        val buttonRecording = binding.buttonRecording

        setDataText()
        setLineChart()

        overview.setOnClickListener {
            Toast.makeText(context, "Go to Records Page for more detail!", Toast.LENGTH_SHORT).show()
        }

        buttonRecording.setOnClickListener {
            Cache.isRecording = !Cache.isRecording
            // go to activity
            val intent = android.content.Intent(context, SleepActivity::class.java)
            startActivity(intent)
        }

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

    private fun setDataText(bind: FragmentHomeBinding = binding) {
        val time = nightData.duration
        val hour = time / 60
        val minute = time % 60

        bind.apply {
            homeDuration.text = "${hour}h ${minute}m"
            homeStartEndTime.text =
                "${nightData.startTime.split("T")[1]} - ${nightData.endTime.split("T")[1]}"
            homeAdvise.text = getAdvise()
        }
    }

    private fun getAdvise() : String {
        var advise = ""

        if (nightData.deepSleepRatio < Cache.stdDeepRatioLow) {
            advise += "You seems to have insufficient deep sleep, "
            advise += "how about do some sports today?"
        } else if (nightData.deepSleepRatio > Cache.stdDeepRatioHigh) {
            advise += "You seems very tired for you have excessive deep sleep, "
            advise += "why not try to have more fun today?"
        }

        if (nightData.meanLux > Cache.stdLuxHigh) {
            advise += "You seems to have excessive light exposure, "
            advise += "you should sleep in a darker environment."
        }

        if (nightData.meanVolume > Cache.stdVolHigh) {
            advise += "You seems to have excessive noise exposure, "
            advise += "you should sleep in a quieter environment."
        }

        if (nightData.duration < Cache.stdDurationLow) {
            advise += "You seems to have insufficient sleep, "
            advise += "how about drop some work today?"
        } else if (nightData.duration > Cache.stdDurationHigh) {
            advise += "You seems to have excessive sleep, "
            advise += "some sports may help you sleep better."
        }

        Log.i(TAG, "deep: ${nightData.deepSleepRatio}, advise: $advise")

        return advise
    }

    private fun setLineChart(line: LineChart = binding.homeLineChart) {
        val xValueList = ArrayList<String>()
        val yValueList = ArrayList<Float>()
        val yValueLabel = ArrayList<String>()

       data.map { it ->
           val time = it.time.split("T")[1].split(":")
           val hm = "${time[0]}:${time[1]}"
           xValueList.add(hm)
           yValueList.add(it.status.toFloat())
       }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}