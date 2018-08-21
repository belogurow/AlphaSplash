package ru.belogurow.alphasplash

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        initLeakCanary()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.enableDisplayLeakActivity(this)

        refWatcher = LeakCanary.install(this)
    }

    companion object {
        private var refWatcher: RefWatcher? = null

        fun watchObject(anyObject: Any) {
            refWatcher?.watch(anyObject)
        }
    }
}