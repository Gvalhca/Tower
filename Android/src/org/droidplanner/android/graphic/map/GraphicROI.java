package org.droidplanner.android.graphic.map;

import static com.o3dr.services.android.lib.drone.property.VehicleMode.COPTER_GUIDED;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.o3dr.android.client.Drone;
import com.o3dr.android.client.apis.ControlApi;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.attribute.AttributeType;
import com.o3dr.services.android.lib.drone.property.GuidedState;
import com.o3dr.services.android.lib.drone.property.State;
import com.o3dr.services.android.lib.drone.property.VehicleMode;

import org.droidplanner.android.R;
import org.droidplanner.android.maps.MarkerInfo;

/**
 * Created by Fredia Huya-Kouadio on 1/27/15.
 */
public class GraphicROI extends MarkerInfo {
    private final static String TAG = GraphicROI.class.getSimpleName();

    public static final double DEFAULT_ROI_ALTITUDE = 15; //meters
    private LatLongAlt roiCoord;

    private final Drone drone;

    public GraphicROI(Drone drone) {
        this.drone = drone;
    }

    @Override
    public void setPosition(LatLong roiCoord) {
        if (roiCoord == null || roiCoord instanceof LatLongAlt) {
            this.roiCoord = (LatLongAlt) roiCoord;
        } else {
            double defaultHeight = DEFAULT_ROI_ALTITUDE;
            if (this.roiCoord != null)
                defaultHeight = this.roiCoord.getAltitude();

            this.roiCoord = new LatLongAlt(roiCoord.getLatitude(), roiCoord.getLongitude(), defaultHeight);
            try {
                ControlApi.getApi(drone).lookAt(this.roiCoord, true, null);
            } catch (Exception e) {
                Log.e(TAG, "Unable to update guided roi point.", e);
            }
        }
    }

    @Override
    public LatLongAlt getPosition() {
        GuidedState guidedPoint = drone.getAttribute(AttributeType.GUIDED_STATE);
        return guidedPoint == null ? null : guidedPoint.getRoiPoint();
    }

    @Override
    public Bitmap getIcon(Resources res) {
        return BitmapFactory.decodeResource(res, R.drawable.ic_roi);
    }

    @Override
    public boolean isVisible() {
        GuidedState guidedPoint = drone.getAttribute(AttributeType.GUIDED_STATE);
        return guidedPoint != null &&
                guidedPoint.isRoiValid() &&
                Drone.currentLongPressState == Drone.LongPressState.LOOK_AT &&
                ((State) drone.getAttribute(AttributeType.STATE)).getVehicleMode() == COPTER_GUIDED;
    }

    @Override
    public float getAnchorU() {
        return 0.5f;
    }

    @Override
    public float getAnchorV() {
        return 0.5f;
    }

    @Override
    public boolean isDraggable() {
        return true;
    }
}
