package com.example.seniorprojectapp.data.model

sealed class DataArrayResponse
data class DataArrayResponseSuccess(val data:List<PMArrayData>):DataArrayResponse()
data class DataArrayResponseFailure(val error: DataArrayError, val e:Throwable?=null): DataArrayResponse()
enum class DataArrayError {
    HTTP_EXCEPTION,
    EMPTY_NULL,
    UNKNOWN
}
