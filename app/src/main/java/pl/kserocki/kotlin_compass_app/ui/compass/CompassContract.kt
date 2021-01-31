package pl.kserocki.kotlin_compass_app.ui.compass

import pl.kserocki.kotlin_compass_app.data.models.CompassOrientation
import pl.kserocki.kotlin_compass_app.data.models.GeoPosition
import pl.kserocki.kotlin_compass_app.ui.base.BaseContract

interface CompassContract {

    interface View : BaseContract.View {
        fun updateDirections(compassOrientation: CompassOrientation)
        fun updateDestinationLocation(geoPosition: GeoPosition)
        fun goToDestinationActivity()
    }

    abstract class ViewModel : BaseContract.ViewModel() {

    }

}