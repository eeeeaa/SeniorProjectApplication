package com.example.seniorprojectapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.seniorprojectapp.data.contract.FetchAPIInterface
import com.example.seniorprojectapp.data.dataSource.FetchAPI
import com.example.seniorprojectapp.data.model.*
import com.example.seniorprojectapp.presentation.contract.FlaskApiInteractorInterface
import com.example.seniorprojectapp.presentation.interactor.FlaskApiInteractor
import com.example.seniorprojectapp.presentation.provider.NetworkProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {
    var source = URLSource()
    var Url = source.LOCALHOST + source.DATA_URL
    var array_url = source.LOCALHOST + source.PM_ARRAY_URL
    var fetchAPIInterface:FetchAPIInterface = FetchAPI(NetworkProvider(Url).provideFlaskAPIClient())
    var flaskApiInteractor:FlaskApiInteractorInterface = FlaskApiInteractor(fetchAPIInterface)
    lateinit var currentData: LiveData<DataResponse>
    lateinit var currentArrayData:LiveData<DataArrayResponse>
    init {
        getPMData()
        getPMArrayData()
    }
    fun getPMData(){
        val publisher = flaskApiInteractor.getPMData()?.toFlowable(BackpressureStrategy.BUFFER)
        if (publisher != null) {
            currentData = LiveDataReactiveStreams.fromPublisher(publisher.subscribeOn(Schedulers.io()))
        }
    }
    fun getPMArrayData(){
        val publisher = flaskApiInteractor.getPMArrayData()?.toFlowable(BackpressureStrategy.BUFFER)
        if(publisher!= null){
            currentArrayData = LiveDataReactiveStreams.fromPublisher(publisher.subscribeOn(Schedulers.io()))
        }
    }
}