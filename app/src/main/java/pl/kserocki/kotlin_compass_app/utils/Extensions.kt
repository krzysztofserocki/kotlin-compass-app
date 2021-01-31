package pl.kserocki.kotlin_compass_app.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object Extensions {

    fun FragmentManager.addFragmentToActivity(fragment: Fragment, frameId: Int) {
        val transaction = this.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
    }

    fun ImageView.adjustArrow(azimuth: Float, currentAzimuth: Float) {
        val an = RotateAnimation(
            -currentAzimuth, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )

        an.duration = 500
        an.repeatCount = 0
        an.fillAfter = true

        this.startAnimation(an)
    }

    fun View.clickAnimation(clickCallback: ClickCallback) {
        this.animate().apply {
            duration = 100
            scaleXBy(.2f)
            scaleYBy(.2f)
        }.withEndAction {
            this.animate().apply {
                duration = 100
                scaleXBy(-.2f)
                scaleYBy(-.2f)
            }.withEndAction {
                clickCallback.onClickFinish()
            }
        }.start()
    }

    fun View.shakeAnimation() {
        this.animate().apply {
            duration = 50
            rotation(15f)
        }.withEndAction {
            this.animate().apply {
                duration = 50
                rotation(-15f)
            }.withEndAction {
                this.animate().apply {
                    duration = 50
                    rotation(15f)
                }.withEndAction {
                    this.animate().apply {
                        duration = 50
                        rotation(0f)
                    }
                }
            }
        }
    }

}