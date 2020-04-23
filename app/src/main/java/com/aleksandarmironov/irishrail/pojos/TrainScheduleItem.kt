package com.aleksandarmironov.irishrail.pojos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(strict = false, name = "objTrainMovements")
data class TrainScheduleItem (
    @field:Element(name = "TrainCode", required = false)
    @param:Element(name = "TrainCode", required = false)
    var trainCode: String? = "",

    @field:Element(name = "TrainDate", required = false)
    @param:Element(name = "TrainDate", required = false)
    var trainDate: String? = "",

    @field:Element(name = "LocationCode", required = false)
    @param:Element(name = "LocationCode", required = false)
    var locationCode: String? = "",

    @field:Element(name = "LocationFullName", required = false)
    @param:Element(name = "LocationFullName", required = false)
    var locationFullName: String? = "",

    @field:Element(name = "LocationOrder", required = false)
    @param:Element(name = "LocationOrder", required = false)
    var locationOrder: String? = "",

    @field:Element(name = "LocationType", required = false)
    @param:Element(name = "LocationType", required = false)
    var locationType: String? = "",

    @field:Element(name = "TrainOrigin", required = false)
    @param:Element(name = "TrainOrigin", required = false)
    var trainOrigin: String? = "",

    @field:Element(name = "TrainDestination", required = false)
    @param:Element(name = "TrainDestination", required = false)
    var trainDestination: String? = "",

    @field:Element(name = "ScheduledArrival", required = false)
    @param:Element(name = "ScheduledArrival", required = false)
    var scheduledArrival: String? = "",

    @field:Element(name = "ScheduledDeparture", required = false)
    @param:Element(name = "ScheduledDeparture", required = false)
    var scheduledDeparture: String? = "",

    @field:Element(name = "ExpectedArrival", required = false)
    @param:Element(name = "ExpectedArrival", required = false)
    var expectedArrival: String? = "",

    @field:Element(name = "ExpectedDeparture", required = false)
    @param:Element(name = "ExpectedDeparture", required = false)
    var expectedDeparture: String? = "",

    @field:Element(name = "Arrival", required = false)
    @param:Element(name = "Arrival", required = false)
    var arrival: String? = "",

    @field:Element(name = "Departure", required = false)
    @param:Element(name = "Departure", required = false)
    var departure: String? = "",

    @field:Element(name = "AutoArrival", required = false)
    @param:Element(name = "AutoArrival", required = false)
    var autoArrival: String? = "",

    @field:Element(name = "AutoDepart", required = false)
    @param:Element(name = "AutoDepart", required = false)
    var autoDepart: String? = "",

    @field:Element(name = "StopType", required = false)
    @param:Element(name = "StopType", required = false)
    var stopType: String? = ""
)