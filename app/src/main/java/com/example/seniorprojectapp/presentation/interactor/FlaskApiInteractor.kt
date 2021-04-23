package com.example.seniorprojectapp.presentation.interactor

import com.example.seniorprojectapp.data.contract.FetchAPIInterface
import com.example.seniorprojectapp.data.model.DataError
import com.example.seniorprojectapp.data.model.DataResponse
import com.example.seniorprojectapp.data.model.DataResponseFailure
import com.example.seniorprojectapp.data.model.DataResponseSuccess
import com.example.seniorprojectapp.presentation.contract.FlaskApiInteractorInterface
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class FlaskApiInteractor(private val fetchAPIInterface: FetchAPIInterface):FlaskApiInteractorInterface {
    override fun getPMData(): Observable<DataResponse>? {
        return fetchAPIInterface.getDataFromApi().observeOn(Schedulers.io())
                .map {
                    when(it){
                        is DataResponseSuccess -> {
                            DataResponseSuccess(it.data)
                        }
                        is DataResponseFailure -> {
                            when(it.error){
                                DataError.HTTP_EXCEPTION -> DataResponseFailure(DataError.HTTP_EXCEPTION,it.e)
                                DataError.EMPTY_NULL -> DataResponseFailure(DataError.EMPTY_NULL)
                                else -> DataResponseFailure(DataError.UNKNOWN,it.e)
                            }
                        }

                    }
                }
    }
}