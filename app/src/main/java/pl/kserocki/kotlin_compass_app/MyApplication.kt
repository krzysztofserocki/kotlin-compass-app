package pl.kserocki.kotlin_compass_app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import pl.kserocki.kotlin_compass_app.di.components.DaggerAppComponent

class MyApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}
