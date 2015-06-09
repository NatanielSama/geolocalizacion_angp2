package nataniel.geolocalizacion_angp2;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    String nuevoval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);


        Bundle bundle = getIntent().getExtras();
        if(bundle!= null) {
            nuevoval = bundle.getString("valor");
        }

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        // Habilitar la capa MyLocation de Google Map
        mMap.setMyLocationEnabled(true);

        // Obtener objeto LocationManager del Sistema de Servicio
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Crear un objeto de criterios para obtener un proveedor
        Criteria criteria = new Criteria();

        // Obtener el nombre de los mejores proveedores
        String provider = locationManager.getBestProvider(criteria, true);

        // Obtener Ubicación actual
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // Establecer el tipo de mapa
        if (nuevoval.equals("2")) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (nuevoval.equals("1")) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (nuevoval.equals("3")) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        if (nuevoval.equals("4")) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        // Obtener la latitud de la ubicación actual
        double latitude = myLocation.getLatitude();

        // Obtener la longitud de la ubicación actual
        double longitude = myLocation.getLongitude();

        // Crear un objeto LatLng para la ubicación actual
        LatLng latLng = new LatLng(latitude, longitude);

        // Muestra la ubicación actual en Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom en el mapa de Google
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Estoy Aqui!").snippet("juaz juaz"));
    }
}
