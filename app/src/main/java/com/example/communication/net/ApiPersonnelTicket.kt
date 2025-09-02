package com.example.communication.net

import com.example.communication.PersonnelTicket
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

@Serializable
data class ApiPersonnelTicket(
    val name: String? = null,
    val gender: String? = null,
    val age: Int = 0,
    val department: String? = null,
    val job: String? = null,
    val rank: String? = null,

    val hurtPlace: String? = null,
    val hurtTime: String? = null,
    val arriveTime: String? = null,
    val emergencyTreatment: Boolean = false,
    val radioactive: Boolean = false,
    val isolation: Boolean = false,
    val poison: Boolean = false,
    val type: Boolean = false,
    val aid: String? = null,

    val injuredArea: List<String>? = null,
    val injuredType: List<String>? = null,
    val injuredCondition: List<String>? = null,
    val woundType: List<String>? = null,
    val injuredSeverity: String? = null,

    val toxoid: Int = 0,
    val tetanus: Int = 0,
    val injectedDrugs: Map<String, String>? = null,
    val transfusion: Map<String, Int>? = null,
    val injection: Map<String, Int>? = null,
    val painKillers: JsonObject? = null,
    val oxygenInhalation: Boolean = false,
    val antiShockPants: Boolean = false,
    val shockOther: String? = null,
    val emergencySurgery: List<String>? = null,
    val evacuateTime: String? = null,
    val evacuateDestination: String? = null,
    val evacuateVehicle: String? = null,
    val restingPosition: String? = null,
    val fillDepartment: String? = null,

    val surgeon: String? = null
)

// 映射
fun PersonnelTicket.toApi(): ApiPersonnelTicket {
    val transfusionMap = this.transfusion?.let { (bloodType, dosage) ->
        if (bloodType.isNullOrBlank()) null else mapOf(bloodType to dosage)
    }
    val injectionMap = this.injection?.let { (drugName, dosage) ->
        if (drugName.isNullOrBlank()) null else mapOf(drugName to dosage)
    }
    val painKillersObj = this.painKillers?.let { (name, dosage, time) ->
        buildJsonObject {
            put("name", name)
            put("dosage", dosage)
            put("time", time)
        }
    }

    return ApiPersonnelTicket(
        name = this.personnel.name,
        gender = this.personnel.gender,
        age = this.personnel.age,
        department = this.personnel.department,
        job = this.personnel.job,
        rank = this.personnel.rank,

        hurtPlace = this.hurtPlace,
        hurtTime = this.hurtTime,
        arriveTime = this.arriveTime,
        emergencyTreatment = this.emergencyTreatment,
        radioactive = this.radioactive,
        isolation = this.isolation,
        poison = this.poison,
        type = this.type,
        aid = this.aid,

        injuredArea = this.injuredArea?.toList(),
        injuredType = this.injuredType?.toList(),
        injuredCondition = this.injuredCondition?.toList(),
        woundType = this.woundType?.toList(),
        injuredSeverity = this.injuredSeverity,

        toxoid = this.toxoid,
        tetanus = this.tetanus,
        injectedDrugs = this.injectedDrugs,
        transfusion = transfusionMap,
        injection = injectionMap,
        painKillers = painKillersObj,
        oxygenInhalation = this.oxygenInhalation,
        antiShockPants = this.antiShockPants,
        shockOther = this.shockOther,
        emergencySurgery = this.emergencySurgery?.toList(),
        evacuateTime = this.evacuateTime,
        evacuateDestination = this.evacuateDestination,
        evacuateVehicle = this.evacuateVehicle,
        restingPosition = this.restingPosition,
        fillDepartment = this.fillDepartment,

        surgeon = this.surgeon
    )
}