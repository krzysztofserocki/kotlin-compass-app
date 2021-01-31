package pl.kserocki.kotlin_compass_app.data.source

import android.app.Application

import dagger.Module
import dagger.Provides
import pl.kserocki.kotlin_compass_app.di.scopes.ApplicationScope
import pl.kserocki.kotlin_compass_app.sensors.ReactiveSensors

@Module
class CompassOrientationSourceModule {
    @ApplicationScope
    @Provides
    internal fun provideReactiveSensors(context: Application): ReactiveSensors {
        return ReactiveSensors(context)
    }
}
