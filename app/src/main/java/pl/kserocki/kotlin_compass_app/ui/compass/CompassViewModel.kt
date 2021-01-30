package pl.kserocki.kotlin_compass_app.ui.compass

import pl.kserocki.kotlin_compass_app.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class CompassViewModel @Inject constructor() :
    CompassContract.ViewModel() {

}