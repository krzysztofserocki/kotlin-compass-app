package pl.kserocki.kotlin_compass_app.ui.compass

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pl.kserocki.kotlin_compass_app.data.models.GeoPosition
import pl.kserocki.kotlin_compass_app.data.source.CompassOrientationSource
import pl.kserocki.kotlin_compass_app.data.source.CompassRepository
import pl.kserocki.kotlin_compass_app.sensors.ReactiveSensors

class CompassViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var reactiveSensors: ReactiveSensors
    lateinit var compassOrientationSource: CompassOrientationSource
    lateinit var compassRepository: CompassRepository
    lateinit var compassViewModel: CompassViewModel

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        compassOrientationSource = CompassOrientationSource(reactiveSensors)
        compassRepository = CompassRepository(compassOrientationSource)
        compassViewModel = CompassViewModel(compassRepository)
    }

    @Test
    fun checkSetter() {
        val geoPosition = GeoPosition(13.2, 123.1)
        compassViewModel.setDestinationPosition(geoPosition)

        val viewModelLatitude = compassViewModel.getDestinationPosition().blockingGet().latitude
        Assert.assertEquals(geoPosition.latitude, viewModelLatitude, 0.0)
        println("Setter of destination position is working good!")
    }

    @Test
    fun testDefaultPosition() {
        val defaultPosition = compassViewModel.getDestinationPosition().blockingGet()

        Assert.assertNotNull(defaultPosition)
        println("Default position is properly!")
    }

}