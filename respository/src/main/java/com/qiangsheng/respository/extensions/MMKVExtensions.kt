package com.qiangsheng.respository.extensions

import android.os.Parcelable
import com.qiangsheng.base.utils.LOG
import com.qiangsheng.respository.mmkv.BasePreference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/*
 *@创建者       L_jp
 *@创建时间     6/5/21 4:16 PM.
 *@描述
 */
/**
 * 代理模式给mmkv工具管理类用
 */
private inline fun <T> delegate(
    key: String? = null,
    defaultValue: T,
    crossinline getter: (String, T) -> T,
    crossinline setter: (String, T) -> Boolean
): ReadWriteProperty<Any, T> =
    object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            LOG.d("===ljp get11", property.name)
            return getter(key ?: property.name, defaultValue)
        }


        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            LOG.d("===ljp set11", property.name)
            setter(key ?: property.name, value)
        }
    }


fun BasePreference.booleanValue(
    defaultValue: Boolean = false,
    key: String? = null
): ReadWriteProperty<BasePreference, Boolean> =
    delegate(key, defaultValue, getMMKV()::decodeBool, getMMKV()::encode)

fun BasePreference.intValue(
    defaultValue: Int = -1,
    key: String? = null
): ReadWriteProperty<BasePreference, Int> =
    delegate(key, defaultValue, getMMKV()::decodeInt, getMMKV()::encode)

fun BasePreference.longValue(
    defaultValue: Long = 0L,
    key: String? = null
): ReadWriteProperty<BasePreference, Long> =
    delegate(key, defaultValue, getMMKV()::decodeLong, getMMKV()::encode)

fun BasePreference.floatValue(
    defaultValue: Float = 0.0F,
    key: String? = null
): ReadWriteProperty<BasePreference, Float> =
    delegate(key, defaultValue, getMMKV()::decodeFloat, getMMKV()::encode)

fun BasePreference.doubleValue(
    defaultValue: Double = 0.0,
    key: String? = null
): ReadWriteProperty<BasePreference, Double> =
    delegate(key, defaultValue, getMMKV()::decodeDouble, getMMKV()::encode)

private inline fun <T> nullableDefaultValueDelegate(
    key: String? = null,
    defaultValue: T?,
    crossinline getter: (String, T?) -> T?,
    crossinline setter: (String, T?) -> Boolean
): ReadWriteProperty<Any, T?> =
    object : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            LOG.d("===ljp get11", property.name)
            return getter(key ?: property.name, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
            LOG.d("===ljp set11", property.name)
            setter(key ?: property.name, value)
        }
    }

fun BasePreference.byteArrayValue(
    defaultValue: ByteArray? = null,
    key: String? = null
): ReadWriteProperty<BasePreference, ByteArray?> =
    nullableDefaultValueDelegate(key, defaultValue, getMMKV()::decodeBytes, getMMKV()::encode)

fun BasePreference.stringValue(
    defaultValue: String? = null,
    key: String? = null
): ReadWriteProperty<BasePreference, String?> =
    nullableDefaultValueDelegate(key, defaultValue, getMMKV()::decodeString, getMMKV()::encode)

fun BasePreference.stringSetValue(
    defaultValue: Set<String>? = null,
    key: String? = null
): ReadWriteProperty<BasePreference, Set<String>?> =
    nullableDefaultValueDelegate(key, defaultValue, getMMKV()::decodeStringSet, getMMKV()::encode)

inline fun <reified T : Parcelable> BasePreference.parcelableValue(
    defaultValue: T? = null,
    key: String? = null
): ReadWriteProperty<BasePreference, T?> =
    object : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            LOG.d("===ljp get11", property.name)
            return getMMKV().decodeParcelable(key ?: property.name, T::class.java, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
            LOG.d("===ljp set11", property.name)
            getMMKV().encode(key ?: property.name, value)
        }
    }


///**
// * put和get方法给非mmkv管理类用
// */
//fun BaseMMKV.putMmValue(key: String, value: Any?) {
//    when (value) {
////      is Boolean -> delegate(key, value, getMMKV()::decodeBool, getMMKV()::encode)
//        is Boolean -> getMMKV().encode(key, value)
//        is Boolean -> delegate(key, value, getMMKV()::decodeBool, getMMKV()::encode)
//        is Int -> getMMKV().encode(key, value)
//        is Long -> getMMKV().encode(key, value)
//        is Float -> getMMKV().encode(key, value)
//        is Double -> getMMKV().encode(key, value)
//        is String -> getMMKV().encode(key, value)
//        is ByteArray -> getMMKV().encode(key, value)
//        is Parcelable -> getMMKV().encode(key, value)
//        is Set<*> -> {
//            //set 泛型String不能为null,如果参数支持,也会被过滤
//            @Suppress("UNCHECKED_CAST")
//            getMMKV().encode(key, value as Set<String>?)
//        }
//        is MutableSet<*> -> {
//            @Suppress("UNCHECKED_CAST")
//            getMMKV().encode(key, value as MutableSet<String>?)
//        }
//        else -> throw Exception("不支持的类型")
//    }
//}
//
///**
// * 对应泛型来取值;
// * 如key相同,泛型类型不同,取到的值也不同
// */
//inline fun <reified T> BaseMMKV.getMmValue(key: String, defaultValue: T? = null): T? {
//    if (T::class == Boolean::class) {
//        return getMMKV().decodeBool(key, defaultValue as Boolean? ?: false) as T?
//    }
//    if (T::class == Int::class) {
//        return getMMKV().decodeInt(key, defaultValue as Int? ?: -1) as T?
//    }
//    if (T::class == Long::class) {
//        return getMMKV().decodeLong(key, defaultValue as Long? ?: 0L) as T?
//    }
//    if (T::class == Float::class) {
//        return getMMKV().decodeFloat(key, defaultValue as Float? ?: 0F) as T?
//    }
//    if (T::class == Double::class) {
//        return getMMKV().decodeDouble(key, defaultValue as Double? ?: 0.0) as T?
//    }
//    if (T::class == String::class) {
//        return getMMKV().decodeString(key, defaultValue as String?) as T?
//    }
//    if (T::class == ByteArray::class) {
//        return getMMKV().decodeBytes(key, defaultValue as ByteArray?) as T?
//    }
//    if (T::class == Set::class) {
//        @Suppress("UNCHECKED_CAST")
//        return getMMKV().decodeStringSet(key, defaultValue as Set<String>?) as T?
//    }
//    if (T::class == MutableSet::class) {
//        @Suppress("UNCHECKED_CAST")
//        return getMMKV().decodeStringSet(key, defaultValue as MutableSet<String>?) as T?
//    }
//    throw Exception("不支持的类型")
//}
//
//fun <T : Parcelable> BaseMMKV.getMmValue(
//    key: String,
//    tClass: Class<T>,
//    defaultValue: T? = null
//): T? {
//    return getMMKV().decodeParcelable(key, tClass, defaultValue)
//}
