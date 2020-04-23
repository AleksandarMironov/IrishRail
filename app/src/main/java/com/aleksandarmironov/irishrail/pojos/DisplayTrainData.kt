package com.aleksandarmironov.irishrail.pojos

data class DisplayTrainData(
    val trainCode: String,
    val trainDirection: String? = "",
    val trainDepartTime: String? = "",
    val userArrivalDestination: String? = "",
    val trainDate: String? = "",
    var trainArrivalTime: String? = ""
)