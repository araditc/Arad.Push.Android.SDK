package ir.araditc.anc

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import ir.araditc.anc.model.FirebaseConfig
import java.util.concurrent.ExecutionException


object Arad{

    private var firebaseApp: FirebaseApp? = null

    fun init(context: Context , firebaseConfig:FirebaseConfig) {

        val option = FirebaseOptions.Builder()
            .setApiKey(firebaseConfig.ApiKey)
            .setApplicationId(firebaseConfig.ApplicationId)
            .setProjectId(firebaseConfig.ProjectId)
            .build();

        firebaseApp = FirebaseApp.initializeApp(context , option, "Arad_SDK_FCM");
    }

    fun getToken(): String? {
        if (firebaseApp == null)
            return null

        val fcmInstance: FirebaseMessaging = firebaseApp!!.get(FirebaseMessaging::class.java)

        val tokenTask = fcmInstance.token
        return try {
            Tasks.await(tokenTask)
        } catch (e: ExecutionException) {
            throw tokenTask.exception!!
        }
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