package pl.kserocki.kotlin_compass_app.ui.compass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import pl.kserocki.kotlin_compass_app.R
import pl.kserocki.kotlin_compass_app.di.scopes.ActivityScope
import javax.inject.Inject


@ActivityScope
class CompassFragment @Inject constructor() : DaggerFragment(), CompassContract.View {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var viewModel: CompassViewModel? = null

    private var mSubscription = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CompassViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_compass, container, false)
    }

    override fun bindViewModel() {

    }

    override fun unbindViewModel() {
        // clear subscriptions
        mSubscription.clear()
    }

}
