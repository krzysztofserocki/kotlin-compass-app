package pl.kserocki.kotlin_compass_app.ui.splash

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import pl.kserocki.kotlin_compass_app.R
import pl.kserocki.kotlin_compass_app.utils.Extensions.addFragmentToActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mInjectedFragment: SplashFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        launchFragment()
    }

    private fun launchFragment() {
        var fragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as SplashFragment?
        if (fragment == null) {
            fragment = mInjectedFragment
            supportFragmentManager.addFragmentToActivity(fragment, R.id.contentFrame)
        }
    }

}