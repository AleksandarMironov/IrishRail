package com.aleksandarmironov.irishrail.pojos

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false, name = "ArrayOfObjTrainMovements")
data class TrainData (
    @field:ElementList(inline = true, required = false, name="objTrainMovements" )
    @param:ElementList(inline = true, required = false, name="objTrainMovements" )
    var trainSchedule: List<TrainScheduleItem> = listOf()
)