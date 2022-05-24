package com.cuongngo.cinemax

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.cuongngo.cinemax.di.appMovieModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class App : Application(), KodeinAware, LifecycleObserver {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))
        import(appMovieModule)
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        @Volatile
        private var instance: App? = null

        @JvmStatic
        fun getInstance(): App = instance ?: synchronized(this) {
            instance ?: App().also {
                instance = it
            }
        }
    }
}