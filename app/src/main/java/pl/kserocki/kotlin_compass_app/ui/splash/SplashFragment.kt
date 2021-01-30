package pl.kserocki.kotlin_compass_app.ui.splash


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_splash.*
import pl.kserocki.kotlin_compass_app.R
import pl.kserocki.kotlin_compass_app.ui.compass.CompassActivity
import pl.kserocki.kotlin_compass_app.ui.compass.CompassViewModel
import javax.inject.Inject


class SplashFragment @Inject constructor() : DaggerFragment(), SplashContract.View,
    View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var viewModel: CompassViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CompassViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        turtleImage.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.turtleImage -> onTurtleClickListener(v)
        }
    }

    private fun onTurtleClickListener(view: View) {
        view.animate().apply {
            duration = 150
            scaleXBy(.3f)
            scaleYBy(.3f)
        }.withEndAction {
            view.animate().apply {
                duration = 150
                scaleXBy(-.3f)
                scaleYBy(-.3f)
            }.withEndAction {
                startActivity(Intent(requireContext(), CompassActivity::class.java))
                requireActivity().overridePendingTransition(
                    R.anim.fragment_fade_enter,
                    R.anim.fragment_fade_exit
                )
            }
        }.start()
    }

}
