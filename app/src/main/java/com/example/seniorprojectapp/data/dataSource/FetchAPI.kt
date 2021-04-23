package com.example.seniorprojectapp.data.dataSource

import android.util.Log
import com.example.seniorprojectapp.data.contract.FetchAPIInterface
import com.example.seniorprojectapp.data.model.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class FetchAPI(private val flaskApiService: FlaskApiService):FetchAPIInterface {
    //fetch data from the flask api
    override fun getDataFromApi(): Observable<DataResponse> {
        return flaskApiService.getData()
            .observeOn(Schedulers.io())
            .map<DataResponse> { data ->
                Log.d("Fetch API success",data.toString())
                if(data != null){
                    DataResponseSuccess(data)
                }else{
                    DataResponseFailure(DataError.EMPTY_NULL)
                }
            }.onErrorReturn {
                    throwable ->
                Log.d("Fetch API fail",throwable.message)
                when(throwable){
                    is HttpException -> DataResponseFailure(DataError.HTTP_EXCEPTION,throwable)
                    else -> DataResponseFailure(DataError.UNKNOWN,throwable)
                }
            }
    }
}