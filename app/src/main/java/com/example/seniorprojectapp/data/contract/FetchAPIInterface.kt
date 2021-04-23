package com.example.seniorprojectapp.data.contract

import com.example.seniorprojectapp.data.model.DataResponse
import io.reactivex.Observable

interface FetchAPIInterface {
    fun getDataFromApi():Observable<DataResponse>
}