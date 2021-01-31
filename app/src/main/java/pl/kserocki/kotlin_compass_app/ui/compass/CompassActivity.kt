package pl.kserocki.kotlin_compass_app.ui.compass

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.android.support.DaggerAppCompatActivity
import pl.kserocki.kotlin_compass_app.R
import pl.kserocki.kotlin_compass_app.utils.Extensions.addFragmentToActivity
import javax.inject.Inject

class CompassActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mInjectedFragment: CompassFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)

        // permission granted -> continue
        if (appHasPermissions()) launchFragment()
        else requestPermission()
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted -> continue
                launchFragment()
            } else requestPermission()
        }
    }

    // Basic method that launches specified fragment
    private fun launchFragment() {
        var fragment =
            supportFragmentManager.findFragmentById(R.id.contentFrame) as CompassFragment?
        if (fragment == null) {
            fragment = mInjectedFragment
            supportFragmentManager.addFragmentToActivity(fragment, R.id.contentFrame)
        }
    }

    private fun appHasPermissions(): Boolean {
        return checkFinePermission() && checkCoarsePermission()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), 1
        )
    }

    private fun checkFinePermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCoarsePermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        return result == PackageManager.PERMISSION_GRANTED
    }
}