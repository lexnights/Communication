package com.example.communication

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Soldier(
    private var name: String? = null,
    private var sex: Boolean = false,
    private var age: Int = 0,
    private var department: String? = null,
    private var job: String? = null, //职位
    private var rank: String? = null, //军衔
    private var place: String? = null, //受伤地点
    private var hurtTime: String? = null, //受伤时间
    private var arriveTime: String? = null, //到达时间
    private var emergencyTreatment: Boolean = false, //紧急救治
    private var radioactive: Boolean = false, //放射性污染
    private var isolation: Boolean = false, //隔离
    private var poison: Boolean = false, //毒剂
    private var type: Boolean = false, //是否为战伤
    private var aid: String? = null, //救治措施
    private var injuredArea: Array<String>? = null, //受伤部位
    private var injuredType: Array<String>? = null, //受伤类型
    private var injuredCondition: Array<String>? = null, //受伤情况
    private var woundType: Array<String>? = null, //伤口类型
    private var injuredSeverity: String? = null, //受伤严重程度
    private var toxoid: Int = 0, //破伤风类毒素
    private var tetanus: Int = 0, //破伤风类抗毒素
    private var injectedDrugs: Map<String, Int>? = null, // 注射药名及剂量
    private var transfusion: Pair<String, Int>? = null, // 输血信息 (血型, 数量)
    private var injection: Pair<String, Int>? = null, // 输液信息 (药名, 剂量)
    private var painkillers: Triple<String, Int, String>? = null, //止痛药信息 (药名, 剂量, 时间)
    private var oxygenInhalation: Boolean = false, //吸氧
    private var antiShockPants: Boolean = false, //抗休克裤
    private var shockOther: String? = null, //其他抗休克措施
    private var emergencySurgery: Array<String>? = null, //紧急手术
    private var time: String? = null, //时间
    private var destination: String? = null, //目的地
    private var vehicle: String? = null, //运输工具
    private var position: String? = null, //体位
    private var fillDepartment: String? = null, //填表部门
    private var surgeon: String? = null, //军医
){
    fun Soldier.toJson(): String {
        return Json.encodeToString(this)
    }
    fun Soldier.getName(): String? {
        return name
    }
    fun Soldier.getSex(): Boolean {
        return sex
    }
    fun Soldier.getAge(): Int {
        return age
    }
    fun Soldier.getDepartment(): String? {
        return department
    }
    fun Soldier.getJob(): String? {
        return job
    }
    fun Soldier.getRank(): String? {
        return rank
    }
    fun Soldier.getPlace(): String? {
        return place
    }
    fun Soldier.getHurtTime(): String? {
        return hurtTime
    }
    fun Soldier.getArriveTime(): String? {
        return arriveTime
    }
    fun Soldier.isEmergencyTreatment(): Boolean {
        return emergencyTreatment
    }
    fun Soldier.isRadioactive(): Boolean {
        return radioactive
    }
    fun Soldier.isIsolation(): Boolean {
        return isolation
    }
    fun Soldier.isPoison(): Boolean {
        return poison
    }
    fun Soldier.isType(): Boolean {
        return type
    }
    fun Soldier.getAid(): String? {
        return aid
    }
    fun Soldier.getInjuredArea(): Array<String>? {
        return injuredArea
    }
    fun Soldier.getInjuredType(): Array<String>? {
        return injuredType
    }
    fun Soldier.getInjuredCondition(): Array<String>? {
        return injuredCondition
    }
    fun Soldier.getWoundType(): Array<String>? {
        return woundType
    }
    fun Soldier.getInjuredSeverity(): String? {
        return injuredSeverity
    }
    fun Soldier.getToxoid(): Int {
        return toxoid
    }
    fun Soldier.getTetanus(): Int {
        return tetanus
    }
    fun Soldier.getInjectedDrugs(): Map<String, Int>? {
        return injectedDrugs
    }
    fun Soldier.getTransfusion(): Pair<String, Int>? {
        return transfusion
    }
    fun Soldier.getInjection(): Pair<String, Int>? {
        return injection
    }
    fun Soldier.getPainkillers(): Triple<String, Int, String>? {
        return painkillers
    }
    fun Soldier.isOxygenInhalation(): Boolean {
        return oxygenInhalation
    }
    fun Soldier.isAntiShockPants(): Boolean {
        return antiShockPants
    }
    fun Soldier.getShockOther(): String? {
        return shockOther
    }
    fun Soldier.getEmergencySurgery(): Array<String>? {
        return emergencySurgery
    }
    fun Soldier.getTime(): String? {
        return time
    }
    fun Soldier.getDestination(): String? {
        return destination
    }
    fun Soldier.getVehicle(): String? {
        return vehicle
    }
    fun Soldier.getPosition(): String? {
        return position
    }
    fun Soldier.getFillDepartment(): String? {
        return fillDepartment
    }
    fun Soldier.getSurgeon(): String? {
        return surgeon
    }
}
