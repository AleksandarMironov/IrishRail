package com.aleksandarmironov.irishrail.networking

import com.aleksandarmironov.irishrail.pojos.StationData
import com.aleksandarmironov.irishrail.pojos.TrainData
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IrishRailApiService {

    @GET("realtime.asmx/getStationDataByNameXML")
    fun getStationData(
        @Query("StationDesc") stationDesc: String,
        @Query("NumMins") numMins: Int = 90
    ): Deferred<StationData?>

    @GET("realtime.asmx/getTrainMovementsXML")
    fun getTrainData(
        @Query("TrainId") trainId: String,
        @Query("TrainDate") trainDate: String
    ): Deferred<TrainData?>
}