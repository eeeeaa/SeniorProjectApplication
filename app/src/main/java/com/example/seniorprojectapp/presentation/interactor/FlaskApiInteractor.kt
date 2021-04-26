package com.example.seniorprojectapp.presentation.interactor

import com.example.seniorprojectapp.data.contract.FetchAPIInterface
import com.example.seniorprojectapp.data.model.*
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

    override fun getPMArrayData(): Observable<DataArrayResponse>? {
        return fetchAPIInterface.getArrayDataFromApi().observeOn(Schedulers.io())
            .map {
                when(it){
                    is DataArrayResponseSuccess -> {
                        DataArrayResponseSuccess(it.data)
                    }
                    is DataArrayResponseFailure -> {
                        when(it.error){
                            DataArrayError.HTTP_EXCEPTION -> DataArrayResponseFailure(DataArrayError.HTTP_EXCEPTION,it.e)
                            DataArrayError.EMPTY_NULL -> DataArrayResponseFailure(DataArrayError.EMPTY_NULL)
                            else -> DataArrayResponseFailure(DataArrayError.UNKNOWN,it.e)
                        }
                    }
                }
            }
    }
}