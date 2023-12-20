package ir.araditc.ane.ui

import android.app.Application
import ir.araditc.anc.Arad
import ir.araditc.anc.model.FirebaseConfig

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Arad.init(this , FirebaseConfig("AIzaSyA4OoILi_0g9xyX5wvYE9EbUOEBbpwUmvs", "1:522440954210:android:a29e3b6664dfec4bf2dc3b" , "omidbank-3dd96"))
    }
}