package com.example.wall_et_mobile.data

class DataSourceException(
    var code: Int,
    message: String,
) : Exception(message)