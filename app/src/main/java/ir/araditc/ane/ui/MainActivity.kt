package ir.araditc.ane.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import ir.araditc.anc.Arad

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var packageInfo = Arad.getPackageName(this)

        var deviceName = Arad.getDeviceName()

        var version = Arad.getVersion(this)
    }
}