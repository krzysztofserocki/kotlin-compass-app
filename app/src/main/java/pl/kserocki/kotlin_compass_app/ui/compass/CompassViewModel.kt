package pl.kserocki.kotlin_compass_app.ui.compass

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import pl.kserocki.kotlin_compass_app.data.models.CompassOrientation
import pl.kserocki.kotlin_compass_app.data.models.GeoPosition
import pl.kserocki.kotlin_compass_app.data.source.CompassRepository
import pl.kserocki.kotlin_compass_app.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class CompassViewModel @Inject constructor(private val repository: CompassRepository) :
    CompassContract.ViewModel() {
    fun getCompassOrientation(): Flowable<CompassOrientation> {
        return repository.orientation
    }

    fun getDestinationPosition(): Single<GeoPosition> {
        return repository.destinationPosition
    }

    fun setDestinationPosition(location: GeoPosition) {
        repository.updateDestinationPosition(location)
    }
}