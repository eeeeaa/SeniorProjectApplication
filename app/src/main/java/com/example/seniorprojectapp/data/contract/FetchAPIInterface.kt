package com.example.seniorprojectapp.data.contract

import com.example.seniorprojectapp.data.model.PMData

interface FetchAPIInterface {
    fun getDataFromApi():List<PMData>
}