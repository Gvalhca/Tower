package org.droidplanner.android.proxy.mission.item.markers;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.drone.mission.item.complex.Survey;

import org.droidplanner.android.R;
import org.droidplanner.android.maps.MarkerInfo;
import org.droidplanner.android.maps.MarkerWithText;
import org.droidplanner.android.proxy.mission.item.MissionItemProxy;

/**
 */
public class PolygonMarkerInfo extends MarkerInfo {

	private LatLong mPoint;
	private final MissionItemProxy markerOrigin;
    private final Survey survey;
    private final int polygonIndex;

	public PolygonMarkerInfo(LatLong point, MissionItemProxy origin, Survey mSurvey, int index) {
		this.markerOrigin = origin;
		mPoint = point;
		survey = mSurvey;
		polygonIndex = index;
	}

	public Survey getSurvey(){
		return survey;
	}

	public int getIndex(){
		return polygonIndex;
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
	public Bitmap getIcon(Resources res) {
		return MarkerWithText.getMarkerWithTextAndDetail(R.drawable.ic_wp_land, "", "", res);
	}

	@Override
	public com.o3dr.services.android.lib.coordinate.LatLong getPosition() {
		return mPoint;
	}

	@Override
	public void setPosition(LatLong coord) {
		mPoint = coord;
	}
	
	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public boolean isFlat() {
		return false;
	}

	public MissionItemProxy getMarkerOrigin() {
		return markerOrigin;
	}

	@Override
	public boolean isDraggable() {
		return true;
	}
}
