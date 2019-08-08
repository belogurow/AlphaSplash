package ru.belogurow.alphasplash

import android.app.Application
import com.codemonkeylabs.fpslibrary.TinyDancer
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        initFPSShowing()
        initLeakCanary()
    }

    private fun initFPSShowing() {
//        TinyDancer.create()
//                .show(this);

        //alternatively
        TinyDancer.create()
                .redFlagPercentage(.1f) // set red indicator for 10%....different from default
                .startingXPosition(200)
                .startingYPosition(600)
                .show(this);
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        refWatcher = LeakCanary.install(this)
    }

    companion object {
        private var refWatcher: RefWatcher? = null

        fun watchObject(anyObject: Any) {
            refWatcher?.watch(anyObject)
        }
    }
}