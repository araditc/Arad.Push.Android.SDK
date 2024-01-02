package com.araditc.ane.ui

import android.app.Application
import com.araditc.anc.Arad
import com.araditc.anc.model.FirebaseConfig

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        //================================= Init APN SDK ===========================================
//        Arad.init(this , FirebaseConfig("{API_KEY}", "{APPLICATION_ID}" , "PROJECT_ID"))
    }
}