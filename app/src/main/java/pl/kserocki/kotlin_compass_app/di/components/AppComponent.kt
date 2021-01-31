package pl.kserocki.kotlin_compass_app.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.kserocki.kotlin_compass_app.MyApplication
import pl.kserocki.kotlin_compass_app.data.source.CompassRepositoryModule
import pl.kserocki.kotlin_compass_app.di.modules.ActivityBindingModule
import pl.kserocki.kotlin_compass_app.di.modules.AppModule
import pl.kserocki.kotlin_compass_app.di.modules.ViewModelsModule
import pl.kserocki.kotlin_compass_app.di.scopes.ApplicationScope

@ApplicationScope
@Component(
    modules = [AppModule::class,
        ActivityBindingModule::class,
        CompassRepositoryModule::class,
        ViewModelsModule::class,
        AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}