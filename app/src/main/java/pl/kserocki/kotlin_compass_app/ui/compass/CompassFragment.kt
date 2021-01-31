package pl.kserocki.kotlin_compass_app.ui.compass

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_compass.*
import pl.kserocki.kotlin_compass_app.R
import pl.kserocki.kotlin_compass_app.data.models.CompassOrientation
import pl.kserocki.kotlin_compass_app.data.models.GeoPosition
import pl.kserocki.kotlin_compass_app.di.scopes.ActivityScope
import pl.kserocki.kotlin_compass_app.ui.base.BaseFragment
import pl.kserocki.kotlin_compass_app.ui.destination.DestinationActivity
import pl.kserocki.kotlin_compass_app.utils.ClickCallback
import pl.kserocki.kotlin_compass_app.utils.Extensions.adjustArrow
import pl.kserocki.kotlin_compass_app.utils.Extensions.clickAnimation
import javax.inject.Inject


@ActivityScope
class CompassFragment @Inject constructor() : BaseFragment(), CompassContract.View,
    View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mSubscription = CompositeDisposable()
    private var currentDestination = Location("")

    private var lastLocation: Location? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_compass
    }

    override fun getMyViewModelFactory(): ViewModelProvider.Factory {
        return viewModelFactory
    }

    override fun setUp() {
        setupLocation()

        setDestinationButton.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null)
            metersToDestinationTxt.text = savedInstanceState.get("currentDistance").toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentDistance", metersToDestinationTxt.text.toString())
    }

    /**
     * Setup location callback to set distance on every change
     */
    private fun setupLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                lastLocation = locationResult.lastLocation
                setDistance(lastLocation!!.distanceTo(currentDestination).toInt())
            }
        }
    }

    /**
     * Function to manipulate and display distance
     */
    private fun setDistance(meters: Int) {
        val myDistance = when {
            meters > 5000 -> (meters / 1000).toString() + " " + getString(R.string.kilometers)
            meters in 1001..5000 -> (meters / 1000).toString() + "." + (meters % 1000).toString() + getString(
                R.string.kilometers
            )
            else -> meters.toString() + " " + getString(R.string.meters)
        }
        metersToDestinationTxt.text = myDistance
    }

    override fun onResume() {
        super.onResume()
        bindViewModel()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        unbindViewModel()
        stopLocationUpdates()
    }

    /**
     *  Set orientation and location callbacks
     */
    private fun bindViewModel() {
        mSubscription = CompositeDisposable()

        mSubscription.add(
            viewModel?.getCompassOrientation()
                ?.subscribe(
                    // onNext
                    { uiModel -> this.updateDirections(uiModel) },
                    // onError
                    { showMessage(R.string.error_loading_directions) })!!
        )

        mSubscription.add(
            viewModel?.getDestinationPosition()
                ?.subscribe(
                    // onNext
                    { uiModel -> this.updateDestinationLocation(uiModel) },
                    // onError
                    { showMessage(R.string.error_loading_location) })!!
        )
    }

    private fun unbindViewModel() {
        // dump subscriptions
        mSubscription.clear()
    }

    /**
     *  Start location updates and save it to the 'lastLocation' variable
     */
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 3000
            fastestInterval = 500
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            lastLocation = it
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    /**
     *  Updates UI arrow and hands when orientation is changed
     */
    override fun updateDirections(compassOrientation: CompassOrientation) {
        compassHandsImage.adjustArrow(
            compassOrientation.polesDirection,
            compassOrientation.lastPolesDirection
        )
        arrowImage.adjustArrow(
            compassOrientation.destinationDirection,
            compassOrientation.lastDestinationDirection
        )
    }

    /**
     *  When destination location is updated, meters in UI are recounted
     */
    override fun updateDestinationLocation(geoPosition: GeoPosition) {
        currentDestination.latitude = geoPosition.latitude
        currentDestination.longitude = geoPosition.longitude

        if (lastLocation != null)
            setDistance(lastLocation!!.distanceTo(currentDestination).toInt())
    }

    /**
     *  Finish DestinationActivity with custom animation
     */
    override fun goToDestinationActivity() {
        startActivity(Intent(context, DestinationActivity::class.java))
        requireActivity().overridePendingTransition(
            R.anim.fragment_fade_enter,
            R.anim.fragment_fade_exit
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.setDestinationButton -> onSetDestinationClick(v)
        }
    }

    private fun onSetDestinationClick(view: View) {
        setDestinationButton.clickAnimation(object : ClickCallback {
            override fun onClickFinish() {
                goToDestinationActivity()
            }
        })
    }

}
