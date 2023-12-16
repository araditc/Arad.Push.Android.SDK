package ir.araditc.anc

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging

object Arad {

    fun initialize(context: Context) {
        val option = FirebaseOptions.Builder()
            .setApiKey(context.getString(R.string.ApiKey))
            .setApplicationId(context.getString(R.string.ApplicationId))
            .setProjectId(context.getString(R.string.ProjectId))
            .build();

        val firebaseApp = FirebaseApp.initializeApp(context , option, "Arad_SDK_FCM_OMID_BANK");

        val fcmInstance: FirebaseMessaging = firebaseApp.get(FirebaseMessaging::class.java)

        fcmInstance.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
        })
    }

    fun getPackageName(context: Context): String? {
        var packageInfo: PackageInfo? = null
        return try {
            packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.applicationInfo.packageName
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true
        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(c.uppercaseChar())
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }
        return phrase.toString()
    }

    fun getVersion(context: Context): String {
        var version = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return version
    }
}