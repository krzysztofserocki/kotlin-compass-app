package pl.kserocki.kotlin_compass_app.ui.splash


import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_splash.*
import pl.kserocki.kotlin_compass_app.R
import pl.kserocki.kotlin_compass_app.ui.base.BaseFragment
import pl.kserocki.kotlin_compass_app.ui.compass.CompassActivity
import pl.kserocki.kotlin_compass_app.utils.ClickCallback
import pl.kserocki.kotlin_compass_app.utils.Extensions.clickAnimation
import javax.inject.Inject


class SplashFragment @Inject constructor() : BaseFragment(), SplashContract.View,
    View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_splash
    }

    override fun getMyViewModelFactory(): ViewModelProvider.Factory {
        return viewModelFactory
    }

    override fun setUp() {
        turtleImage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.turtleImage -> onTurtleClickListener(v)
        }
    }

    private fun onTurtleClickListener(view: View) {
        view.clickAnimation(object : ClickCallback {
            override fun onClickFinish() {
                goToCompassActivity()
            }
        })
    }

    override fun goToCompassActivity() {
        startActivity(Intent(requireContext(), CompassActivity::class.java))
        requireActivity().overridePendingTransition(
            R.anim.fragment_fade_enter,
            R.anim.fragment_fade_exit
        )
    }

}
