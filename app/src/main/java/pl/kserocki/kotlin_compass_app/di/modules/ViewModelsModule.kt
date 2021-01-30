package pl.kserocki.kotlin_compass_app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.kserocki.kotlin_compass_app.di.factory.CompassViewModelFactory
import pl.kserocki.kotlin_compass_app.di.scopes.ApplicationScope
import pl.kserocki.kotlin_compass_app.di.scopes.ViewModelMapKey
import pl.kserocki.kotlin_compass_app.ui.compass.CompassViewModel

@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelMapKey(CompassViewModel::class)
    abstract fun bindCompassViewModel(compassViewModel: CompassViewModel): ViewModel

    @Binds
    @ApplicationScope
    abstract fun bindViewModelFactory(factory: CompassViewModelFactory): ViewModelProvider.Factory
}