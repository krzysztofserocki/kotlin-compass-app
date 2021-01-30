package pl.kserocki.kotlin_compass_app.ui.base

interface BaseContract {

    interface View : BaseView {

    }

    abstract class ViewModel : BaseViewModel() {

    }

}