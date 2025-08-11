package com.example.communication

import kotlinx.serialization.Serializable

@Serializable
data class Personnel(
    var name: String? = null,
    var gender: String? = null, // 布尔值意图不明确，直接字符串描述
    var age: Int = 0,
    var department: String? = null,
    var job: String? = null, //职位
    var rank: String? = null, //军衔
)

// 尽量避免用过于直白的名字
@Serializable
data class PersonnelTicket(
    var personnel: Personnel = Personnel(), //人员信息

    var hurtPlace: String? = null, //受伤地点
    var hurtTime: String? = null, //受伤时间
    var arriveTime: String? = null, //到达时间
    var emergencyTreatment: Boolean = false, //紧急救治
    var radioactive: Boolean = false, //放射性污染
    var isolation: Boolean = false, //隔离
    var poison: Boolean = false, //毒剂
    var type: Boolean = false, //是否为战伤
    var aid: String? = null, //救治措施
    var injuredArea: Array<String>? = null, //受伤部位
    var injuredType: Array<String>? = null, //受伤类型
    var injuredCondition: Array<String>? = null, //受伤情况
    var woundType: Array<String>? = null, //伤口类型
    var injuredSeverity: String? = null, //受伤严重程度

    var toxoid: Int = 0, //破伤风类毒素
    var tetanus: Int = 0, //破伤风类抗毒素
    var injectedDrugs: Map<String, String>? = null, // 注射药名及剂量
    var transfusion: Pair<String, Int>? = null, // 输血信息 (血型, 数量)
    var injection: Pair<String, Int>? = null, // 输液信息 (药名, 剂量)
    var painKillers: Triple<String, Int, String>? = null, //止痛药信息 (药名, 剂量, 时间)
    var oxygenInhalation: Boolean = false, //吸氧
    var antiShockPants: Boolean = false, //抗休克裤
    var shockOther: String? = null, //其他抗休克措施
    var emergencySurgery: Array<String>? = null, //紧急手术
    var evacuateTime: String? = null, //后送时间
    var evacuateDestination: String? = null, //后送目的地
    var evacuateVehicle: String? = null, //运输工具
    var restingPosition: String? = null, //体位
    var fillDepartment: String? = null, //填表部门
    var surgeon: String? = null, //军医
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonnelTicket

        if (emergencyTreatment != other.emergencyTreatment) return false
        if (radioactive != other.radioactive) return false
        if (isolation != other.isolation) return false
        if (poison != other.poison) return false
        if (type != other.type) return false
        if (toxoid != other.toxoid) return false
        if (tetanus != other.tetanus) return false
        if (oxygenInhalation != other.oxygenInhalation) return false
        if (antiShockPants != other.antiShockPants) return false
        if (personnel != other.personnel) return false
        if (hurtPlace != other.hurtPlace) return false
        if (hurtTime != other.hurtTime) return false
        if (arriveTime != other.arriveTime) return false
        if (aid != other.aid) return false
        if (!injuredArea.contentEquals(other.injuredArea)) return false
        if (!injuredType.contentEquals(other.injuredType)) return false
        if (!injuredCondition.contentEquals(other.injuredCondition)) return false
        if (!woundType.contentEquals(other.woundType)) return false
        if (injuredSeverity != other.injuredSeverity) return false
        if (injectedDrugs != other.injectedDrugs) return false
        if (transfusion != other.transfusion) return false
        if (injection != other.injection) return false
        if (painKillers != other.painKillers) return false
        if (shockOther != other.shockOther) return false
        if (!emergencySurgery.contentEquals(other.emergencySurgery)) return false
        if (evacuateTime != other.evacuateTime) return false
        if (evacuateDestination != other.evacuateDestination) return false
        if (evacuateVehicle != other.evacuateVehicle) return false
        if (restingPosition != other.restingPosition) return false
        if (fillDepartment != other.fillDepartment) return false
        if (surgeon != other.surgeon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = emergencyTreatment.hashCode()
        result = 31 * result + radioactive.hashCode()
        result = 31 * result + isolation.hashCode()
        result = 31 * result + poison.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + toxoid
        result = 31 * result + tetanus
        result = 31 * result + oxygenInhalation.hashCode()
        result = 31 * result + antiShockPants.hashCode()
        result = 31 * result + personnel.hashCode()
        result = 31 * result + (hurtPlace?.hashCode() ?: 0)
        result = 31 * result + (hurtTime?.hashCode() ?: 0)
        result = 31 * result + (arriveTime?.hashCode() ?: 0)
        result = 31 * result + (aid?.hashCode() ?: 0)
        result = 31 * result + (injuredArea?.contentHashCode() ?: 0)
        result = 31 * result + (injuredType?.contentHashCode() ?: 0)
        result = 31 * result + (injuredCondition?.contentHashCode() ?: 0)
        result = 31 * result + (woundType?.contentHashCode() ?: 0)
        result = 31 * result + (injuredSeverity?.hashCode() ?: 0)
        result = 31 * result + (injectedDrugs?.hashCode() ?: 0)
        result = 31 * result + (transfusion?.hashCode() ?: 0)
        result = 31 * result + (injection?.hashCode() ?: 0)
        result = 31 * result + (painKillers?.hashCode() ?: 0)
        result = 31 * result + (shockOther?.hashCode() ?: 0)
        result = 31 * result + (emergencySurgery?.contentHashCode() ?: 0)
        result = 31 * result + (evacuateTime?.hashCode() ?: 0)
        result = 31 * result + (evacuateDestination?.hashCode() ?: 0)
        result = 31 * result + (evacuateVehicle?.hashCode() ?: 0)
        result = 31 * result + (restingPosition?.hashCode() ?: 0)
        result = 31 * result + (fillDepartment?.hashCode() ?: 0)
        result = 31 * result + (surgeon?.hashCode() ?: 0)
        return result
    }
}


