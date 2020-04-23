package com.aleksandarmironov.irishrail.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aleksandarmironov.irishrail.networking.IrishRailApi
import com.aleksandarmironov.irishrail.pojos.DisplayTrainData
import com.aleksandarmironov.irishrail.pojos.StationData
import com.aleksandarmironov.irishrail.pojos.StationScheduleItem
import com.aleksandarmironov.irishrail.pojos.TrainData
import com.aleksandarmironov.irishrail.utils.notifyObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivityViewModel : ViewModel() {

    var firstStation = MutableLiveData<String>()
    var destinationsStation = MutableLiveData<String>()
    var firstStationError = MutableLiveData<Int>()
    var brayStationError = MutableLiveData<Int>()
    var internetConnectionError = MutableLiveData<Int>()
    var brayStationTrainList = MutableLiveData<ArrayList<DisplayTrainData>>()
    var firstStationTrainList = MutableLiveData<ArrayList<DisplayTrainData>>()

    var isDirectionNormal = true
    private var viewModelJob = Job()

    //retrofit uses different thread for web requests, so Dispatchers.Main is convenient for updating UI
    private val myCoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        brayStationTrainList.value = arrayListOf()
        firstStationTrainList.value = arrayListOf()
        setStationNames()
        loadData()
    }

    fun rotateDirection() {
        isDirectionNormal = !isDirectionNormal
        setStationNames()
        loadData()
    }

    fun loadData() {
        hideNoTrainWarnings()
        clearListData()
        loadInitialStationData()
        loadBrayStationData()
    }

    fun internetConnectionStatus(isAvailable: Boolean){
        internetConnectionError.postValue(if(isAvailable) View.GONE else View.VISIBLE)
    }

    private fun hideNoTrainWarnings(){
        firstStationError.value = View.GONE
        brayStationError.value = View.GONE
    }

    private fun setStationNames() {
        if (isDirectionNormal) {
            firstStation.value = SHANKILL_STATION
            destinationsStation.value = ARKLOW_STATION
        } else {
            firstStation.value = ARKLOW_STATION
            destinationsStation.value = SHANKILL_STATION
        }
    }

    private fun clearListData() {
        firstStationTrainList.value = arrayListOf()
        brayStationTrainList.value = arrayListOf()
    }

    private fun loadInitialStationData() {
        firstStation.value?.let {
            myCoroutineScope.launch {
                val firstStationDeferred = IrishRailApi.retrofitService.getStationData(it)
                var firstStationData: StationData? = null
                try {
                    firstStationData = firstStationDeferred.await()
                } catch (throwable: Throwable) {
                    handleErr(throwable)
                }
                firstStationData?.let {
                    for (scheduledItem in it.stationData) {
                        if ((isTrainFromShankillToBray(scheduledItem) || isTrainFromArklowToBray(scheduledItem)) &&
                            scheduledItem.trainCode != null
                        ) {
                            firstStationTrainList.value?.add(
                                DisplayTrainData(
                                    scheduledItem.trainCode ?: "",
                                    scheduledItem.destination,
                                    scheduledItem.expDepart,
                                    CHANGE_STATION,
                                    scheduledItem.trainDate
                                )
                            )
                        }
                    }
                }
                firstStationTrainList.value?.let {
                    if (it.isEmpty()){
                        firstStationError.value = View.VISIBLE
                    } else {
                        it.forEach { loadTrainSchedule(it) }
                    }
                }
            }
        }
    }

    private fun isTrainFromShankillToBray(stationScheduleItem: StationScheduleItem) =
        isDirectionNormal &&
                DIRECTION_SOUTHBOUND == stationScheduleItem.direction &&
                (CHANGE_STATION == stationScheduleItem.destination || DESTINATION_GREYSTONES == stationScheduleItem.destination)

    private fun isTrainFromArklowToBray(stationScheduleItem: StationScheduleItem) =
        !isDirectionNormal &&
                DIRECTION_NORTHBOUND == stationScheduleItem.direction &&
                ROSSLARE_EUROPORT_STATION == stationScheduleItem.origin &&
                DUBLIN_CONNOLLY_STATION == stationScheduleItem.destination

    private fun isTrainFromBrayToArklow(stationScheduleItem: StationScheduleItem) =
        isDirectionNormal &&
                DIRECTION_SOUTHBOUND == stationScheduleItem.direction &&
                ROSSLARE_EUROPORT_STATION == stationScheduleItem.destination &&
                DUBLIN_CONNOLLY_STATION == stationScheduleItem.origin &&
                stationScheduleItem.trainCode != null

    private fun isTrainFromBrayToShankill(stationScheduleItem: StationScheduleItem) =
        !isDirectionNormal &&
                DIRECTION_NORTHBOUND == stationScheduleItem.direction &&
                stationScheduleItem.trainCode != null


    private fun loadBrayStationData() {
        myCoroutineScope.launch {
            val brayStationDeferred = IrishRailApi.retrofitService.getStationData(CHANGE_STATION)
            var brayStationData: StationData? = null
            try {
                brayStationData = brayStationDeferred.await()
            } catch (throwable: Throwable) {
                handleErr(throwable)
            }

            brayStationData?.let {
                for (scheduledItem in it.stationData) {
                    if (isTrainFromBrayToArklow(scheduledItem)) {
                        brayStationTrainList.value?.add(
                            DisplayTrainData(
                                scheduledItem.trainCode ?: "",
                                scheduledItem.destination,
                                scheduledItem.expDepart,
                                ARKLOW_STATION,
                                scheduledItem.trainDate
                            )
                        )
                    } else if (isTrainFromBrayToShankill(scheduledItem)) {
                        brayStationTrainList.value?.add(
                            DisplayTrainData(
                                scheduledItem.trainCode ?: "",
                                scheduledItem.destination,
                                scheduledItem.expDepart,
                                SHANKILL_STATION,
                                scheduledItem.trainDate
                            )
                        )
                    }
                }
            }
            brayStationTrainList.value?.let {
                if (it.isEmpty()){
                    brayStationError.value = View.VISIBLE
                } else {
                    it.forEach { loadTrainSchedule(it) }
                }
            }
        }
    }

    private fun loadTrainSchedule(displayTrainData: DisplayTrainData) {
        myCoroutineScope.launch {
            val trainScheduleDeferred = IrishRailApi.retrofitService.getTrainData(
                displayTrainData.trainCode.trim(),
                displayTrainData.trainDate ?: getCurrentDate()
            )
            var trainData: TrainData? = null
            try {
                trainData = trainScheduleDeferred.await()
            } catch (throwable: Throwable) {
                handleErr(throwable)
            }

            trainData?.let {
                displayTrainData.trainArrivalTime =
                    it.trainSchedule.find { it.locationFullName == displayTrainData.userArrivalDestination }?.expectedArrival
                        ?: ""
            }

            firstStationTrainList.notifyObserver()
            brayStationTrainList.notifyObserver()
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ROOT)
        return dateFormat.format(Calendar.getInstance().time)
    }

    private fun handleErr(throwable: Throwable) {
        Log.e(TAG, throwable.message?: "")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object {
        const val SHANKILL_STATION = "Shankill"
        const val CHANGE_STATION = "Bray"
        const val ARKLOW_STATION = "Arklow"
        const val DIRECTION_SOUTHBOUND = "Southbound"
        const val DIRECTION_NORTHBOUND = "Northbound"
        const val DESTINATION_GREYSTONES = "Greystones"
        const val ROSSLARE_EUROPORT_STATION = "Rosslare Europort"
        const val DUBLIN_CONNOLLY_STATION = "Dublin Connolly"
        private const val TAG = "Irish Rail"
    }
}