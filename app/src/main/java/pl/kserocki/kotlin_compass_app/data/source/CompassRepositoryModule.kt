package pl.kserocki.kotlin_compass_app.data.source

import dagger.Module
import dagger.Provides
import pl.kserocki.kotlin_compass_app.di.scopes.ApplicationScope
import pl.kserocki.kotlin_compass_app.sensors.ReactiveSensors

@Module(
    includes = [CompassOrientationSourceModule::class]
)
class CompassRepositoryModule {
    @Provides
    @ApplicationScope
    internal fun provideCompassOrientationSource(reactiveSensors: ReactiveSensors): OrientationDataSource {
        return CompassOrientationSource(
            reactiveSensors
        )
    }
}