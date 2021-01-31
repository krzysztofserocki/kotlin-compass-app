package pl.kserocki.kotlin_compass_app.ui.splash

import pl.kserocki.kotlin_compass_app.ui.base.BaseContract

interface SplashContract {

    interface View : BaseContract.View {
        fun goToCompassActivity()
    }

    abstract class ViewModel : BaseContract.ViewModel() {

    }

}