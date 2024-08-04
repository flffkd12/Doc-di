package com.example.doc_di.domain.model

data class Pill(
    val bizrno: String,
    val changeDate: String,
    val chart: String?,
    val className: String,
    val classNo: String,
    val colorClass1: String?,
    val colorClass2: String?,
    val drugShape: String?,
    val ediCode: String,
    val entpName: String,
    val entpSeq: String,
    val etcOtcName: String,
    val formCodeName: String?,
    val imgRegistTs: String,
    val itemEngName: String,
    val itemImage: String,
    val itemName: String,
    val itemPermitDate: String,
    val itemSeq: Int,
    val lengLong: String,
    val lengShort: String,
    val lineBack: String?,
    val lineFront: String?,
    val markCodeBack: String,
    val markCodeBackAnal: String,
    val markCodeBackImg: String,
    val markCodeFront: String,
    val markCodeFrontAnal: String,
    val markCodeFrontImg: String,
    val printBack: String?,
    val printFront: String?,
    val thick: String
)