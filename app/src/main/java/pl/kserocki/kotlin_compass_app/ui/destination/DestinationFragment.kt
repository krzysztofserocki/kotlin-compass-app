package pl.kserocki.kotlin_compass_app.ui.destination


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import pl.kserocki.kotlin_compass_app.R
import pl.kserocki.kotlin_compass_app.ui.compass.CompassViewModel
import javax.inject.Inject


class DestinationFragment @Inject constructor() : DaggerFragment(), DestinationContract.View {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var viewModel: CompassViewModel? = null

    private var mSubscription = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CompassViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_destination, container, false)
    }

    override fun onResume() {
        super.onResume()
        bindViewModel()
    }

    override fun onPause() {
        super.onPause()
        unbindViewModel()
    }

    override fun bindViewModel() {

    }

    override fun unbindViewModel() {

    }

}
