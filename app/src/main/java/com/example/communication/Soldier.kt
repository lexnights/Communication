package com.example.communication

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
    private var Painkillers: Triple<String, Int, String>? = null, //止痛药信息 (药名, 剂量, 时间)
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
)


