package com.example.seniorprojectapp.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.seniorprojectapp.R
import com.example.seniorprojectapp.data.model.*
import com.example.seniorprojectapp.presentation.factory.MainViewModelFactory
import com.example.seniorprojectapp.presentation.viewModel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var model: MainViewModel
    final val TAG = "Main activity: "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
        initialization()

    }
    private fun toast(msg:String){
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
        observeData()
    }

    fun observeData(){
        val observer = Observer<DataResponse>{
            if(it != null){
                when(it){
                    is DataResponseSuccess -> {
                        Log.d(TAG, "success!")
                        setData(it.data)
                    }
                    is DataResponseFailure -> {
                        Log.d(TAG, "error :(")
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
    fun setData(data: PMData){
        val current_pm_data = findViewById<TextView>(R.id.current_pm_data)
        val fire_data = findViewById<TextView>(R.id.fire_data)
        val temp_data = findViewById<TextView>(R.id.temp_data)
        val humid_data = findViewById<TextView>(R.id.humid_data)
        val traffic_data = findViewById<TextView>(R.id.traffic_data)
        val wind_data = findViewById<TextView>(R.id.wind_data)

        current_pm_data.text = data.pm_current.toString()
        fire_data.text = data.fire.toString()
        temp_data.text = data.temp.toString()
        humid_data.text = data.humidity.toString()
        traffic_data.text = data.traffic.toString()
        wind_data.text = data.windspeed.toString()
    }
}