package pl.kserocki.kotlin_compass_app.data.source

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import pl.kserocki.kotlin_compass_app.data.models.CompassOrientation
import pl.kserocki.kotlin_compass_app.data.models.GeoPosition
import pl.kserocki.kotlin_compass_app.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class CompassRepository @Inject
constructor(
    private val compassOrientationSource: OrientationDataSource
) : OrientationDataSource {

    override fun getOrientation(): Flowable<CompassOrientation> {
        return compassOrientationSource.orientation
    }

    override fun updateCurrentLocation(position: GeoPosition) {
        compassOrientationSource.updateCurrentLocation(position)
    }

    override fun getDestinationPosition(): Single<GeoPosition> {
        return compassOrientationSource.destinationPosition
    }

    override fun updateDestinationPosition(position: GeoPosition) {
        compassOrientationSource.updateDestinationPosition(position)
    }
}
