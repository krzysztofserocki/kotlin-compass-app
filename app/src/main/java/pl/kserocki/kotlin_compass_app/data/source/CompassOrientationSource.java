package pl.kserocki.kotlin_compass_app.data.source;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import pl.kserocki.kotlin_compass_app.data.models.CompassOrientation;
import pl.kserocki.kotlin_compass_app.data.models.GeoPosition;
import pl.kserocki.kotlin_compass_app.di.scopes.ApplicationScope;
import pl.kserocki.kotlin_compass_app.sensors.ReactiveSensors;

@ApplicationScope
public class CompassOrientationSource implements OrientationDataSource {
    private final ReactiveSensors reactiveSensors;
    private final float[] gravity = new float[3];
    private final float[] geomagnetic = new float[3];
    private final float[] matrixR = new float[9];
    private final float[] matrixI = new float[9];
    private GeoPosition destinationPosition = new GeoPosition(52.2230633, 19.1479710);
    private GeoPosition currentPosition = new GeoPosition(54.2230633, 18.14170797);
    private float lastPolesAzimuth;
    private float lastDestinationAzimuth;

    @Inject
    public CompassOrientationSource(ReactiveSensors reactiveSensors) {
        this.reactiveSensors = reactiveSensors;
    }

    /**
     * Function which set current orientation based on sensors.
     *
     * @return
     */
    @Override
    public Flowable<CompassOrientation> getOrientation() {
        return reactiveSensors.observeSensors(Sensor.TYPE_ACCELEROMETER,
                Sensor.TYPE_MAGNETIC_FIELD, SensorManager.SENSOR_DELAY_GAME)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).flatMap(sensorEvent -> {

                    CompassOrientation compassOrientation = new CompassOrientation();

                    final float alpha = 0.97f;

                    synchronized (this) {
                        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                            geomagnetic[0] = alpha * geomagnetic[0] + (1 - alpha) * sensorEvent.values[0];
                            geomagnetic[1] = alpha * geomagnetic[1] + (1 - alpha) * sensorEvent.values[1];
                            geomagnetic[2] = alpha * geomagnetic[2] + (1 - alpha) * sensorEvent.values[2];
                        }

                        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                            gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
                            gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
                            gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];
                        }

                        boolean success = SensorManager.getRotationMatrix(matrixR, matrixI, gravity, geomagnetic);
                        if (success) {
                            float[] orientation = new float[3];
                            SensorManager.getOrientation(matrixR, orientation);

                            float azimuth = (float) Math.toDegrees(orientation[0]); // orientation
                            azimuth = (azimuth + 360) % 360;

                            compassOrientation.setPolesDirection(azimuth);
                            compassOrientation.setLastPolesDirection(lastPolesAzimuth);

                            // update last pole azimuth for next iteration
                            lastPolesAzimuth = azimuth;

                            double destinationAzimuth = azimuth -
                                    calcBearing(currentPosition.getLatitude(), currentPosition.getLongitude(),
                                            destinationPosition.getLatitude(), destinationPosition.getLongitude());

                            compassOrientation.setDestinationDirection((float) destinationAzimuth);
                            compassOrientation.setLastDestinationDirection(lastDestinationAzimuth);

                            // update last destination azimuth for next iteration
                            lastDestinationAzimuth = (float) destinationAzimuth;
                        }
                    }
                    return Flowable.just(compassOrientation);
                });
    }

    @Override
    public void updateCurrentLocation(GeoPosition position) {
        this.currentPosition = position;
    }

    @Override
    public Single<GeoPosition> getDestinationPosition() {
        return Single.just(destinationPosition);
    }

    @Override
    public void updateDestinationPosition(GeoPosition destinationPosition) {
        this.destinationPosition = destinationPosition;
    }

    // Internal method that allows us to calculate the bearing between two points
    private double calcBearing(double startLat, double startLng, double endLat, double endLng) {
        double latitude1 = Math.toRadians(startLat);
        double latitude2 = Math.toRadians(endLat);
        double longDiff = Math.toRadians(endLng - startLng);
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }
}
