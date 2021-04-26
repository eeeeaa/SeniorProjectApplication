package com.example.seniorprojectapp.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.ekn.gruzer.gaugelibrary.Range
import com.example.seniorprojectapp.R
import com.example.seniorprojectapp.data.model.*
import com.example.seniorprojectapp.presentation.factory.MainViewModelFactory
import com.example.seniorprojectapp.presentation.model.ArrayDataModel
import com.example.seniorprojectapp.presentation.viewModel.MainViewModel
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {
    private lateinit var model: MainViewModel
    val TAG = "Main activity: "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
        initialization()
        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        refreshLayout.setOnRefreshListener {
            initialization()
            refreshLayout.isRefreshing = false
        }
    }
    private fun toast(msg: String){
        val inflater = layoutInflater
        val layout: View =
            inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))

        val text: TextView = layout.findViewById(R.id.custom_toast_text)
        text.text = msg

        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.BOTTOM, 0, 40)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
    private fun initialization(){
        pmDataloadingStatus(true)
        observeData()
        observeArrayData()
    }
    private fun pmDataloadingStatus(isLoad: Boolean){
        val pm_load = findViewById<ProgressBar>(R.id.pm_loading_bar)
        val fire_load = findViewById<ProgressBar>(R.id.fire_loading_bar)
        val temp_load = findViewById<ProgressBar>(R.id.temp_loading_bar)
        val humid_load = findViewById<ProgressBar>(R.id.humid_loading_bar)
        val traffic_load = findViewById<ProgressBar>(R.id.traffic_loading_bar)
        val wind_load = findViewById<ProgressBar>(R.id.wind_loading_bar)
        val bar_list = arrayListOf<ProgressBar>(
            pm_load,
            fire_load,
            temp_load,
            humid_load,
            traffic_load,
            wind_load
        )
        for (item in bar_list){
            if (isLoad) {
                item.visibility = View.VISIBLE
            }else{
                item.visibility = View.GONE
            }
        }
        gaugeLoadingStatus(isLoad)
    }
    private fun gaugeLoadingStatus(isLoad: Boolean){
        val gauge_list = arrayListOf<Int>(
            R.id.fire_gauge,
            R.id.temp_gauge,
            R.id.humid_gauge,
            R.id.traffic_gauge,
            R.id.wind_gauge
        )
        for (item in gauge_list){
            if (isLoad){
                gaugeSetVisible(false, item)
            }else{
                gaugeSetVisible(true, item)
            }
        }
    }

    private fun observeData(){
        val observer = Observer<DataResponse>{
            if(it != null){
                when(it){
                    is DataResponseSuccess -> {
                        Log.d(TAG, "success!")
                        pmDataloadingStatus(false)
                        setData(it.data)
                    }
                    is DataResponseFailure -> {
                        Log.d(TAG, "error :(")
                        pmDataloadingStatus(false)
                        when (it.error) {
                            DataError.HTTP_EXCEPTION -> {
                                toast("HTTP exception: " + it.e?.message)
                            }
                            DataError.EMPTY_NULL -> {
                                toast("empty or null data!")
                            }
                            DataError.UNKNOWN -> {
                                toast("something went wrong!")
                            }
                        }
                    }
                }
            }
        }
        model.currentData.observe(this, observer)
    }
    private fun observeArrayData(){
        val observer = Observer<DataArrayResponse> {
            if(it != null){
                when(it){
                    is DataArrayResponseSuccess -> {
                        Log.d(TAG, "array success!")
                        setArrayData(it.data)
                    }
                    is DataArrayResponseFailure -> {
                        Log.d(TAG, "array failure!")
                        when (it.error) {
                            DataArrayError.HTTP_EXCEPTION -> {
                                toast("HTTP exception: " + it.e?.message)
                            }
                            DataArrayError.EMPTY_NULL -> {
                                toast("empty or null data!")
                            }
                            DataArrayError.UNKNOWN -> {
                                toast("something went wrong!")
                            }
                        }
                    }
                }
            }
        }
        model.currentArrayData.observe(this, observer)
    }
    private fun setArrayData(data: List<PMArrayData>){
        val arrayChart = findViewById<AnyChartView>(R.id.array_chart)
        arrayChart.setProgressBar(findViewById(R.id.array_loading_bar))
        val arrayData = arrayListOf<ArrayDataModel>()
        for (item in data){
            arrayData.add(ArrayDataModel(item.pm25, item.time))
        }
        val cartesian = AnyChart.line()
        cartesian.animation(true)
        cartesian.padding(10.0, 20.0, 5.0, 20.0)
        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.title("Trend of PM2.5 concentration in Bangkok")

        cartesian.yAxis(0).title("PM2.5 concentration (μg/m^3)")
        //cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)
        val dataSeries = arrayListOf<DataEntry>()
        for (item in arrayData){
            dataSeries.add(ValueDataEntry(item.datetime, item.pm25))
        }
        val series1 = cartesian.line(dataSeries)
        series1.name("PM2.5")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)
        arrayChart.setChart(cartesian)
    }
    private fun setData(data: PMData){
        val currentPMData = findViewById<TextView>(R.id.current_pm_data)
        currentPMData.text = data.pm_current.toString()

        circularGauge(data.fire, R.id.fire_gauge)
        circularGauge(data.temp, R.id.temp_gauge)
        circularGauge(data.humidity, R.id.humid_gauge)
        circularGauge(data.traffic, R.id.traffic_gauge)
        circularGauge(data.windspeed, R.id.wind_gauge)
    }

    private fun circularGauge(data: Float?, view_id: Int){
        val gauge = findViewById<ArcGauge>(view_id)
        val range = Range()
        range.color = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        gauge.addRange(range)
        gauge.minValue = 0.0
        gauge.maxValue = 100.0
        gauge.valueColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        if (data != null) {
            var round_data = round(data, 2)
            gauge.value = round_data.toDouble()
        }
    }
    private fun gaugeSetVisible(status: Boolean, gauge_id: Int){
        val gauge = findViewById<ArcGauge>(gauge_id)
        if(status) {
            gauge.visibility = View.VISIBLE
        }else{
            gauge.visibility = View.GONE
        }
    }
    private fun round(d: Float, decimalPlace: Int): BigDecimal {
        var bd = BigDecimal(java.lang.Float.toString(d))
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
        return bd
    }
}