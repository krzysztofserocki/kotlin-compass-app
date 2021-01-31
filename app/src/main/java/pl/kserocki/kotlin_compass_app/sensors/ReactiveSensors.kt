package pl.kserocki.kotlin_compass_app.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Handler
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableOnSubscribe
import java.util.*

class ReactiveSensors constructor(context: Context) {
    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    /**
     * Retrieve lists sensors available on device
     *
     * @return list of sensors
     */
    val sensors: List<Sensor>
        get() = sensorManager.getSensorList(Sensor.TYPE_ALL)

    /**
     * Check if device has certain sensor available
     *
     * @param sensorType from Sensor class available in SDK
     */
    private fun hasSensor(sensorType: Int): Boolean {
        return sensorManager.getDefaultSensor(sensorType) != null
    }

    @JvmOverloads
    fun observeSensors(
        sensorType1: Int, sensorType2: Int, samplingPeriodInUs: Int,
        handler: Handler? = null, strategy: BackpressureStrategy = BackpressureStrategy.BUFFER
    ): Flowable<SensorEvent> {

        if (!hasSensor(sensorType1) || !hasSensor(sensorType2)) {
            val format = "Sensors with id = %d  and id = %d are not available on this device"
            val message = String.format(Locale.getDefault(), format, sensorType1, sensorType2)
            return Flowable.error(SensorNotFoundException(message))
        }

        val sensor1 = sensorManager.getDefaultSensor(sensorType1)
        val sensor2 = sensorManager.getDefaultSensor(sensorType2)

        val wrapper = SensorEventListenerWrapper()
        val listener = wrapper.create()

        return Flowable.create(FlowableOnSubscribe<SensorEvent> { emitter ->
            wrapper.emitter = emitter

            if (handler == null) {
                sensorManager.registerListener(listener, sensor1, samplingPeriodInUs)
                sensorManager.registerListener(listener, sensor2, samplingPeriodInUs)
            } else {
                sensorManager.registerListener(listener, sensor1, samplingPeriodInUs, handler)
                sensorManager.registerListener(listener, sensor2, samplingPeriodInUs, handler)
            }
        }, strategy).doOnCancel { sensorManager.unregisterListener(listener) }
    }
}
