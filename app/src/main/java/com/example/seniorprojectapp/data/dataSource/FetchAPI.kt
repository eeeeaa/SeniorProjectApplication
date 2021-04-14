package com.example.seniorprojectapp.data.dataSource

import com.example.seniorprojectapp.data.contract.FetchAPIInterface
import com.example.seniorprojectapp.data.model.PMData

class FetchAPI:FetchAPIInterface {
    final var API_URL = ""
    //fetch data from the flask api
    override fun getDataFromApi(): List<PMData> {
        TODO("Not yet implemented")
        return emptyList()
    }
}