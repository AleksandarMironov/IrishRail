package com.aleksandarmironov.irishrail.pojos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(strict = false, name = "objStationData")
data class StationScheduleItem(
    @field:Element(name = "Servertime", required = false)
    @param:Element(name = "Servertime", required = false)
    var serverTime: String? = "",

    @field:Element(name = "Traincode", required = false)
    @param:Element(name = "Traincode", required = false)
    var trainCode: String? = "",

    @field:Element(name = "Stationfullname", required = false)
    @param:Element(name = "Stationfullname", required = false)
    var stationFullName: String? = "",

    @field:Element(name = "Stationcode", required = false)
    @param:Element(name = "Stationcode", required = false)
    var stationCode: String? = "",

    @field:Element(name = "Querytime", required = false)
    @param:Element(name = "Querytime", required = false)
    var queryTime: String? = "",

    @field:Element(name = "Traindate", required = false)
    @param:Element(name = "Traindate", required = false)
    var trainDate: String? = "",

    @field:Element(name = "Origin", required = false)
    @param:Element(name = "Origin", required = false)
    var origin: String? = "",

    @field:Element(name = "Destination", required = false)
    @param:Element(name = "Destination", required = false)
    var destination: String? = "",

    @field:Element(name = "Origintime", required = false)
    @param:Element(name = "Origintime", required = false)
    var originTime: String? = "",

    @field:Element(name = "Destinationtime", required = false)
    @param:Element(name = "Destinationtime", required = false)
    var destinationTime: String? = "",

    @field:Element(name = "Status", required = false)
    @param:Element(name = "Status", required = false)
    var status: String? = "",

    @field:Element(name = "Lastlocation", required = false)
    @param:Element(name = "Lastlocation", required = false)
    var lastLocation: String? = "",

    @field:Element(name = "Duein", required = false)
    @param:Element(name = "Duein", required = false)
    var dueIn: Int? = 0,

    @field:Element(name = "Late", required = false)
    @param:Element(name = "Late", required = false)
    var late: Int? = 0,

    @field:Element(name = "Exparrival", required = false)
    @param:Element(name = "Exparrival", required = false)
    var expArrival: String? = "",

    @field:Element(name = "Expdepart", required = false)
    @param:Element(name = "Expdepart", required = false)
    var expDepart: String? = "",

    @field:Element(name = "Scharrival", required = false)
    @param:Element(name = "Scharrival", required = false)
    var schArrival: String? = "",

    @field:Element(name = "Schdepart", required = false)
    @param:Element(name = "Schdepart", required = false)
    var schDepart: String? = "",

    @field:Element(name = "Direction", required = false)
    @param:Element(name = "Direction", required = false)
    var direction: String? = "",

    @field:Element(name = "Traintype", required = false)
    @param:Element(name = "Traintype", required = false)
    var trainType: String? = "",

    @field:Element(name = "Locationtype", required = false)
    @param:Element(name = "Locationtype", required = false)
    var locationType: String? = ""
)