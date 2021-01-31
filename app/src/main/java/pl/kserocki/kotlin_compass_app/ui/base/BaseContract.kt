package pl.kserocki.kotlin_compass_app.ui.base

interface BaseContract {

    interface View

    abstract class ViewModel : androidx.lifecycle.ViewModel()

}