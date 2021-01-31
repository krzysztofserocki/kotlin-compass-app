package pl.kserocki.kotlin_compass_app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import pl.kserocki.kotlin_compass_app.ui.compass.CompassViewModel

abstract class BaseFragment : DaggerFragment() {

    var viewModel: CompassViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, getMyViewModelFactory()).get(CompassViewModel::class.java)
    }

    /**
     * @return ViewModel factory from each fragment
     */
    abstract fun getMyViewModelFactory(): ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    /**
     * @return layout of each fragment
     */
    abstract fun getFragmentLayout(): Int

    /**
     *  Display simple toast by String resource
     */
    fun showMessage(@StringRes message: Int) {
        Toast.makeText(requireActivity(), getString(message), Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    abstract fun setUp()

}