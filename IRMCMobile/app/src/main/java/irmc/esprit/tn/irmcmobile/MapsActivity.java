package irmc.esprit.tn.irmcmobile;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.android.volley.Response;
import org.json.JSONArray;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String url="http://1b44f4fb.ngrok.io/IRMCJEE-web/IRMC/institute/";

        // Add a marker in Sydney and move the camera
      // LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
     //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

      //  System.out.println("******************");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response "+response);

                            JSONArray array = new JSONArray(response);
                            System.out.println(array);

                            //System.out.println("response1 "+items.toString());
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(32.796244,11.1774)));
                            for (int i=0; i<array.length();i++){
                                JSONObject obj1 = array.getJSONObject(i);
                              Institute inst = new Institute();
                               double lat= obj1.getDouble("latitude");
                                double lon= obj1.getDouble("longitude");
                                String name= obj1.getString("name");
                                String sigle= obj1.getString("sigle");

                                String type= obj1.getString("type");
                                LatLng markerInst = new LatLng(lat,lon);
                                mMap.addMarker(new MarkerOptions().position(markerInst).title(name+" ("+sigle+")").snippet("Institute de type :"+type));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!"+error);
            }
        }) ;

        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
        queue.add(stringRequest);



    }
}
