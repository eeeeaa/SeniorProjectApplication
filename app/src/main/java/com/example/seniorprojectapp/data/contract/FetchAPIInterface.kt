package com.example.seniorprojectapp.data.contract

import com.example.seniorprojectapp.data.model.DataArrayResponse
import com.example.seniorprojectapp.data.model.DataResponse
import com.example.seniorprojectapp.data.model.PMArrayData
import io.reactivex.Observable

interface FetchAPIInterface {
    fun getDataFromApi():Observable<DataResponse>
    fun getArrayDataFromApi():Observable<DataArrayResponse>
}