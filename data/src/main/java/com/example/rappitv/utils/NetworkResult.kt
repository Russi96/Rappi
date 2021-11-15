package com.example.rappitv.utils

/**
 * Created by danie on 01,septiembre,2021
 * https://kotlinlang.org/docs/sealed-classes.html#location-of-direct-subclasses
 */
open class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T): NetworkResult<T>(data)
    class Error<T>(message: String?, data: T? = null): NetworkResult<T>(data, message)
    class Loading<T>: NetworkResult<T>()

}