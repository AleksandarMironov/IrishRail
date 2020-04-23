package com.aleksandarmironov.irishrail.pojos

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false, name = "ArrayOfObjStationData")
data class StationData (

    @field:ElementList(inline = true, required = false, name="objStationData" )
    @param:ElementList(inline = true, required = false, name="objStationData" )
    var stationData: List<StationScheduleItem> = listOf()
)