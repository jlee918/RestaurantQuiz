package ca.ubc.cs.cpsc210.quiz.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import ca.ubc.cs.cpsc210.quiz.model.Restaurant;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.heatmaps.Gradient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcarter on 14-11-06.
 *
 * Manager for markers plotted on map
 */
public class MarkerManager {
    private GoogleMap map;
    private List<Marker> lom;



    /**
     * Constructor initializes manager with map for which markers are to be managed.
     * @param map  the map for which markers are to be managed
     */
    public MarkerManager(GoogleMap map) {
        this.map = map;
        lom = new ArrayList<Marker>();




    }

    /**
     * Get last marker added to map
     * @return  last marker added
     */
    public Marker getLastMarker() {
        if(lom !=null && !lom.isEmpty()) {
            Marker last = lom.get(lom.size()-1);
            return last;
        }
        else
            return null;



    }

    /**
     * Add green marker to show position of restaurant
     * @param point   the point at which to add the marker
     * @param title   the marker's title
     */
    public void addRestaurantMarker(LatLng point, String title) {
      Marker m3 = map.addMarker(new MarkerOptions().position(point).title(title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
      lom.add(m3);


    }


    /**
     * Add a marker to mark latest guess from user.  Only the most recent two positions selected
     * by the user are marked.  The distance from the restaurant is used to create the marker's title
     * of the form "xxxx m away" where xxxx is the distance from the restaurant in metres (truncated to
     * an integer).
     *
     * The colour of the marker is based on the distance from the restaurant:
     * - red, if the distance is 3km or more
     * - somewhere between red (at 3km) and green (at 0m) (on a linear scale) for other distances
     *
     * @param latLng
     * @param distance
     */
    public void addMarker(LatLng latLng, double distance) {

        if(distance >= 3000) {
            int y = (int)distance;
            BitmapDescriptor bitmapDescriptor1 = BitmapDescriptorFactory.defaultMarker(getColour(distance));
            Marker m1 = map.addMarker(new MarkerOptions().position(latLng).title(y + " m away").icon(bitmapDescriptor1));
            lom.add(m1);

        }

        if (distance <= 3000) {
            int x = (int)distance;

            BitmapDescriptor bitmapDescriptor2 = BitmapDescriptorFactory.defaultMarker(getColour(distance));
            Marker m2 = map.addMarker(new MarkerOptions().position(latLng).title(x + " m away").icon(bitmapDescriptor2));
            lom.add(m2);



        }








        // m2.equals(m1); m1 equals newest marker
    }

    /**
     * Remove markers that mark user guesses from the map
     */
    public void removeMarkers() {
       for(int i = 0; i<lom.size(); i++) {
           lom.get(i).remove();
       }



    }

    /**
     * Produce a colour on a linear scale between red and green based on distance:
     *
     * - red, if distance is 3km or more
     * - somewhere between red (at 3km) and green (at 0m) (on a linear scale) for other distances
     * @param distance  distance from restaurant
     * @return  colour of marker
     */
    private float getColour(double distance) {


        if(distance <= 3000) {
            Double d = ((3000-distance)/3000)*120;
            float f = d.floatValue();
            return f;


        }

        else
            return 0;







    }
}
