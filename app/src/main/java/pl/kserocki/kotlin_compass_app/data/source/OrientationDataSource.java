package pl.kserocki.kotlin_compass_app.data.source;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import pl.kserocki.kotlin_compass_app.data.models.CompassOrientation;
import pl.kserocki.kotlin_compass_app.data.models.GeoPosition;

public interface OrientationDataSource {
    Flowable<CompassOrientation> getOrientation();

    Single<GeoPosition> getDestinationPosition();

    void updateDestinationPosition(GeoPosition destinationPosition);

    void updateCurrentLocation(GeoPosition position);
}
