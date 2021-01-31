package pl.kserocki.kotlin_compass_app.ui.destination


import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_destination.*
import pl.kserocki.kotlin_compass_app.R
import pl.kserocki.kotlin_compass_app.data.models.GeoPosition
import pl.kserocki.kotlin_compass_app.ui.base.BaseFragment
import pl.kserocki.kotlin_compass_app.utils.ClickCallback
import pl.kserocki.kotlin_compass_app.utils.Extensions.clickAnimation
import pl.kserocki.kotlin_compass_app.utils.Extensions.shakeAnimation
import pl.kserocki.kotlin_compass_app.utils.KeyboardUtils
import java.lang.Double
import javax.inject.Inject


class DestinationFragment @Inject constructor() : BaseFragment(), DestinationContract.View,
    View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_destination
    }

    override fun getMyViewModelFactory(): ViewModelProvider.Factory {
        return viewModelFactory
    }

    override fun setUp() {
        KeyboardUtils.setupUI(destinationLayout, requireActivity())

        saveDestinationButton.setOnClickListener(this)
        backDestinationButton.setOnClickListener(this)
    }

    /**
     *
     */
    private fun onSaveDestinationClick() {
        if (latitudeTxt.text.isEmpty() || latitudeTxt.text.matches(Regex("[0-9]+\\.?[0-9]+"))
                .not()
        ) {
            showMessage(R.string.wrong_latitude)
            saveDestinationButton.shakeAnimation()
            return
        }

        val doubledLatitude = Double.parseDouble(latitudeTxt.text.toString())
        if (doubledLatitude > 90.0 || doubledLatitude < -90.0) {
            showMessage(R.string.latitude_out_of_range)
            saveDestinationButton.shakeAnimation()
            return
        }

        if (longitudeTxt.text.isEmpty() || longitudeTxt.text.matches(Regex("[0-9]+\\.?[0-9]+"))
                .not()
        ) {
            showMessage(R.string.wrong_longitude)
            saveDestinationButton.shakeAnimation()
            return
        }

        val doubledLongitude = Double.parseDouble(longitudeTxt.text.toString())
        if (doubledLongitude > 90.0 || doubledLongitude < -90.0) {
            showMessage(R.string.longitude_out_of_range)
            saveDestinationButton.shakeAnimation()
            return
        }

        saveDestinationButton.clickAnimation(object : ClickCallback {
            override fun onClickFinish() {
                viewModel?.setDestinationPosition(
                    GeoPosition(
                        doubledLatitude,
                        doubledLongitude
                    )
                )
                goBackToCompassActivity()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.saveDestinationButton -> onSaveDestinationClick()
            R.id.backDestinationButton -> onDestinationBackButton(v)
        }
    }

    private fun onDestinationBackButton(v: View) {
        backDestinationButton.clickAnimation(object : ClickCallback {
            override fun onClickFinish() {
                goBackToCompassActivity()
            }
        })
    }

    override fun goBackToCompassActivity() {
        requireActivity().finish()
        requireActivity().overridePendingTransition(
            R.anim.fragment_fade_enter,
            R.anim.fragment_fade_exit
        )
    }

}
