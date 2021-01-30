package pl.kserocki.kotlin_compass_app.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object Extensions {

    fun FragmentManager.addFragmentToActivity(fragment: Fragment, frameId: Int) {
        val transaction = this.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
    }

}