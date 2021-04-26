package com.example.seniorprojectapp.presentation.contract

import com.example.seniorprojectapp.data.model.DataArrayResponse
import com.example.seniorprojectapp.data.model.DataResponse
import io.reactivex.Observable

interface FlaskApiInteractorInterface {
    fun getPMData(): Observable<DataResponse>?
    fun getPMArrayData():Observable<DataArrayResponse>?
}