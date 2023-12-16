package ir.araditc.ane.ui

import android.app.Application
import ir.araditc.anc.Arad

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Arad.initialize(this);
    }
}