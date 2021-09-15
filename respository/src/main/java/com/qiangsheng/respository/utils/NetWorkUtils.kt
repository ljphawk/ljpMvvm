package com.qiangsheng.respository.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Proxy
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.qiangsheng.respository.app.BaseApplication
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*


object NetWorkUtils {

    /**
     * 没有网络
     */
    const val NETWORKTYPE_INVALID = 0

    /**
     * wap网络
     */
    const val NETWORKTYPE_WAP = 1

    /**
     * 2G网络
     */
    const val NETWORKTYPE_2G = 2

    /**
     * 3G和3G以上网络，或统称为快速网络
     */
    const val NETWORKTYPE_3G = 3

    /**
     * wifi网络
     */
    const val NETWORKTYPE_WIFI = 4


    fun isNetworkConnected(): Boolean {
        val cm = BaseApplication.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return ni != null && ni.isConnectedOrConnecting
    }

    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @return int 网络状态 [.NETWORKTYPE_2G],[.NETWORKTYPE_3G],          *[.NETWORKTYPE_INVALID],[.NETWORKTYPE_WAP]*
     *
     *[.NETWORKTYPE_WIFI]
     */
    fun getNetWorkType(): Int {
        var mNetWorkType = NETWORKTYPE_INVALID
        val context: Context = BaseApplication.instance
        try {
            val manager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                val type = networkInfo.typeName
                if (type.equals("WIFI", ignoreCase = true)) {
                    mNetWorkType = NETWORKTYPE_WIFI
                } else if (type.equals("MOBILE", ignoreCase = true)) {
                    val proxyHost = Proxy.getDefaultHost()
                    mNetWorkType =
                        if (TextUtils.isEmpty(proxyHost)) if (isFastMobileNetwork(context)) NETWORKTYPE_3G else NETWORKTYPE_2G else NETWORKTYPE_WAP
                }
            }
        } catch (e: Exception) { //fixme ConnectivityService: Neither user 10111 nor current process has android.permission.ACCESS_NETWORK_STATE.
            e.printStackTrace()
        }
        return mNetWorkType
    }

    private fun isFastMobileNetwork(context: Context): Boolean {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                ?: return false
        return when (telephonyManager.networkType) {
            TelephonyManager.NETWORK_TYPE_1xRTT -> false // ~ 50-100 kbps
            TelephonyManager.NETWORK_TYPE_CDMA -> false // ~ 14-64 kbps
            TelephonyManager.NETWORK_TYPE_EDGE -> false // ~ 50-100 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> true // ~ 400-1000 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_A -> true // ~ 600-1400 kbps
            TelephonyManager.NETWORK_TYPE_GPRS -> false // ~ 100 kbps
            TelephonyManager.NETWORK_TYPE_HSDPA -> true // ~ 2-14 Mbps
            TelephonyManager.NETWORK_TYPE_HSPA -> true // ~ 700-1700 kbps
            TelephonyManager.NETWORK_TYPE_HSUPA -> true // ~ 1-23 Mbps
            TelephonyManager.NETWORK_TYPE_UMTS -> true // ~ 400-7000 kbps
            TelephonyManager.NETWORK_TYPE_EHRPD -> true // ~ 1-2 Mbps
            TelephonyManager.NETWORK_TYPE_EVDO_B -> true // ~ 5 Mbps
            TelephonyManager.NETWORK_TYPE_HSPAP -> true // ~ 10-20 Mbps
            TelephonyManager.NETWORK_TYPE_IDEN -> false // ~25 kbps
            TelephonyManager.NETWORK_TYPE_LTE -> true // ~ 10+ Mbps
            TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
            else -> false
        }
    }

    fun isWifi(): Boolean {
        return getNetWorkType() == NETWORKTYPE_WIFI
    }

    fun isInvalid(): Boolean {
        return getNetWorkType() == NETWORKTYPE_INVALID
    }

    fun isWwan(): Boolean {
        return getNetWorkType() == NETWORKTYPE_WAP || getNetWorkType() == NETWORKTYPE_2G || getNetWorkType() == NETWORKTYPE_3G
    }

    fun getNetworkStatus(): String {
        return when {
            isInvalid() -> {
                "noNetwork"
            }
            isWwan() -> {
                "wwan"
            }
            isWifi() -> {
                "wifi"
            }
            else -> {
                "unknown"
            }
        }
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    fun getLocalIpAddress(context: Context): String {
        try {
            val info =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                    .activeNetworkInfo
            if (info != null && info.isConnected) {
                if (info.type == ConnectivityManager.TYPE_MOBILE) { //当前使用2G/3G/4G网络
                    val en =
                        NetworkInterface.getNetworkInterfaces()
                    while (en.hasMoreElements()) {
                        val intf = en.nextElement()
                        val enumIpAddr =
                            intf.inetAddresses
                        while (enumIpAddr.hasMoreElements()) {
                            val inetAddress = enumIpAddr.nextElement()
                            if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                return inetAddress.getHostAddress()
                            }
                        }
                    }
                } else if (info.type == ConnectivityManager.TYPE_WIFI) { //当前使用无线网络
                    val wifiManager =
                        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                    val wifiInfo = wifiManager.connectionInfo
                    return int2ip(wifiInfo.ipAddress)
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    private fun int2ip(ipInt: Int): String {
        val sb = StringBuilder()
        sb.append(ipInt and 0xFF).append(".")
        sb.append(ipInt shr 8 and 0xFF).append(".")
        sb.append(ipInt shr 16 and 0xFF).append(".")
        sb.append(ipInt shr 24 and 0xFF)
        return sb.toString()
    }

}