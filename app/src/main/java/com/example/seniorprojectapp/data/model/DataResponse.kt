package com.example.seniorprojectapp.data.model

sealed class DataResponse
data class DataResponseSuccess(val data:PMData):DataResponse()
data class DataResponseFailure(val error: DataError, val e:Throwable?=null): DataResponse()
enum class DataError {
    HTTP_EXCEPTION,
    EMPTY_NULL,
    UNKNOWN
}
