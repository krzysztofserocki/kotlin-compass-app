package pl.kserocki.kotlin_compass_app.ui.destination

import pl.kserocki.kotlin_compass_app.ui.base.BaseContract

interface DestinationContract {

    interface View : BaseContract.View {
        fun goBackToCompassActivity()
    }

    abstract class ViewModel : BaseContract.ViewModel() {

    }

}