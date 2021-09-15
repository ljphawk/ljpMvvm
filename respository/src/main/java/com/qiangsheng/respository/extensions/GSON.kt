package com.qiangsheng.respository.extensions

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.qiangsheng.base.utils.LOG
import java.lang.reflect.Type

val GSON by lazy {
    GsonBuilder()
        .disableInnerClassSerialization()
        .create()
}

fun toJsonString(any: Any?): String {
    return GSON.toJson(any)
}

fun <T> parseObject(json: String?, clazz: Class<T>): T? {
    if (json == null) return null
    return try {
        GSON.fromJson(json, clazz)
    } catch (e: Exception) {
        e.printStackTrace()
        LOG.d("parseObject = ${e.message}")
        null
    }
}

fun <T> parseObject(json: String?, type: Type): T? {
    if (json == null) return null
    return try {
        GSON.fromJson(json, type)
    } catch (e: Exception) {
        e.printStackTrace()
        LOG.d("parseObject = ${e.message}")
        null
    }
}

fun <T> parseList(json: String?, clazz: Class<T>): List<T> {
    if (json == null || json == "null" || json == "[]") return mutableListOf()
    val type = TypeToken.getParameterized(List::class.java, clazz).type
    return GSON.fromJson(json, type)
}