package com.qiangsheng.base.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class ToastCompat(context: Context?) : Toast(context) {
    override fun show() {
        if (checkIfNeedToHack()) {
            tryToHack()
        }
        super.show()
    }

    private fun checkIfNeedToHack(): Boolean {
        return Build.VERSION.SDK_INT == 25
    }

    private fun tryToHack() {
        try {
            val mTN = getFieldValue(this, "mTN")
            if (mTN != null) {
                var isSuccess = false
                //a hack to some device which use the code between android 6.0 and android 7.1.1
                val rawShowRunnable = getFieldValue(mTN, "mShow")
                if (rawShowRunnable != null && rawShowRunnable is Runnable) {
                    isSuccess = setFieldValue(
                        mTN,
                        "mShow",
                        InternalRunnable(rawShowRunnable)
                    )
                }
                // hack to android 7.1.1,these cover 99% devices.
                if (!isSuccess) {
                    val rawHandler =
                        getFieldValue(mTN, "mHandler")
                    if (rawHandler != null && rawHandler is Handler) {
                        isSuccess = setFieldValue(
                            rawHandler,
                            "mCallback",
                            InternalHandlerCallback(rawHandler)
                        )
                    }
                }
                if (!isSuccess) {
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private inner class InternalRunnable(private val mRunnable: Runnable) :
        Runnable {
        override fun run() {
            try {
                mRunnable.run()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }

    }

    private inner class InternalHandlerCallback(private val mHandler: Handler) :
        Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            try {
                mHandler.handleMessage(msg)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            return true
        }

    }

    companion object {
        private const val TAG = "ToastCompat"
        /**
         * Make a standard toast that just contains a text view.
         *
         * @param context  The context to use.  Usually your [android.app.Application]
         * or [android.app.Activity] object.
         * @param text     The text to show.  Can be formatted text.
         * @param duration How long to display the message.  Either [.LENGTH_SHORT] or
         * [.LENGTH_LONG]
         */
        fun makeText(
            context: Context,
            text: CharSequence?,
            duration: Int
        ): ToastCompat {
            val result = ToastCompat(context)
            val inflate =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val resources = context.resources
            val v = inflate.inflate(
                resources.getIdentifier(
                    "transient_notification",
                    "layout",
                    "android"
                ), null
            )
            val tv = v.findViewById<View>(
                resources.getIdentifier(
                    "message",
                    "id",
                    "android"
                )
            ) as TextView
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            tv.text = text
            result.view = v
            result.duration = duration
            return result
        }

        /**
         * Make a standard toast that just contains a text view with the text from a resource.
         *
         * @param context  The context to use.  Usually your [android.app.Application]
         * or [android.app.Activity] object.
         * @param resId    The resource id of the string resource to use.  Can be formatted text.
         * @param duration How long to display the message.  Either [.LENGTH_SHORT] or
         * [.LENGTH_LONG]
         * @throws Resources.NotFoundException if the resource can't be found.
         */
        @Throws(Resources.NotFoundException::class)
        fun makeText(context: Context, resId: Int, duration: Int): Toast {
            return makeText(
                context,
                context.resources.getText(resId),
                duration
            )
        }

        private fun setFieldValue(
            `object`: Any,
            fieldName: String,
            newFieldValue: Any
        ): Boolean {
            val field =
                getDeclaredField(`object`, fieldName)
            if (field != null) {
                try {
                    val accessFlags = field.modifiers
                    if (Modifier.isFinal(accessFlags)) {
                        val modifiersField =
                            Field::class.java.getDeclaredField("accessFlags")
                        modifiersField.isAccessible = true
                        modifiersField.setInt(
                            field,
                            field.modifiers and Modifier.FINAL.inv()
                        )
                    }
                    if (!field.isAccessible) {
                        field.isAccessible = true
                    }
                    field[`object`] = newFieldValue
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return false
        }

        private fun getFieldValue(obj: Any, fieldName: String): Any? {
            val field =
                getDeclaredField(obj, fieldName)
            return getFieldValue(obj, field)
        }

        private fun getFieldValue(obj: Any, field: Field?): Any? {
            if (field != null) {
                try {
                    if (!field.isAccessible) {
                        field.isAccessible = true
                    }
                    return field[obj]
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return null
        }

        private fun getDeclaredField(
            obj: Any,
            fieldName: String
        ): Field? {
            var superClass: Class<*> = obj.javaClass
            while (superClass != Any::class.java) {
                return try {
                    superClass.getDeclaredField(fieldName)
                } catch (e: NoSuchFieldException) {
                    superClass = superClass.superclass
                    continue  // new add
                }
                superClass = superClass.superclass
            }
            return null
        }
    }
}