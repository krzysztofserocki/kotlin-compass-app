package pl.kserocki.kotlin_compass_app.ui.compass

import pl.kserocki.kotlin_compass_app.ui.base.BaseContract

interface CompassContract {

    interface View : BaseContract.View {

    }

    abstract class ViewModel : BaseContract.ViewModel() {

    }

}