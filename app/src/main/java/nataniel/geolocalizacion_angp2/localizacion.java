package nataniel.geolocalizacion_angp2;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class localizacion extends ActionBarActivity {
    //Declaramos las variables a utilizar
    private Button actualizar, desactivar;

    String la,longi,pres;
    private TextView latitud, longitud, precision, estadoprov;
    String msg;
    private LocationManager manager;
    private LocationListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);
    //enlazamos el xml con el java
        actualizar = (Button) findViewById(R.id.actualizar);
        desactivar = (Button) findViewById(R.id.desactivar);
        latitud = (TextView) findViewById(R.id.latitud);
        longitud = (TextView) findViewById(R.id.longitud);
        precision = (TextView) findViewById(R.id.presicion);
        estadoprov = (TextView) findViewById(R.id.estadores);
        //declaramos que al dar clic en los botones actualizar se obtendra la localizacion
        //y en desactivar se quitaran las actualizaciones
        actualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                comenzar();
            }
        });

        desactivar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                manager.removeUpdates(listener);
            }
        });
    }
    //declaramos el metodo que usaremos para obtener nuestra localizacion
    private void comenzar()
    {
        //Obtenemos una referencia al LocationManager
        manager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida
        Location local = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Mostramos la última posición conocida
        mostrarPosicion(local);

        //Nos registramos para recibir actualizaciones de la posición
        listener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }
            public void onProviderDisabled(String provider){
                estadoprov.setText(R.string.desactivado);
            }
            public void onProviderEnabled(String provider){
                estadoprov.setText(R.string.activado);
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                estadoprov.setText(status);
            }
        };
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, listener);
    }
    //mostramos los datos obtenidos en la vista
    private void mostrarPosicion(Location local) {
        if(local != null)
        {

            latitud.setText(String.valueOf(local.getLatitude()));
            longitud.setText(String.valueOf(local.getLongitude()));
            precision.setText(String.valueOf(local.getAccuracy()));
            Log.i("", String.valueOf(local.getLatitude() + " - " + String.valueOf(local.getLongitude())));
        }
        else
        {
            latitud.setText(R.string.nlat);
            longitud.setText(R.string.nlongi);
            precision.setText(R.string.npresc);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_localizacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapan:
                Intent i = new Intent(this, Mapa.class);
                msg = "1";
                i.putExtra("valor", msg);
                startActivity(i);
                return true;
            case R.id.mapat:
                Intent a = new Intent(this, Mapa.class);
                msg = "3";
                a.putExtra("valor", msg);
                startActivity(a);
                return true;
            case R.id.mapas:
                Intent b = new Intent(this, Mapa.class);
                msg = "2";
                b.putExtra("valor", msg);
                startActivity(b);
                return true;
            case R.id.mapah:
                Intent c = new Intent(this, Mapa.class);
                msg = "4";
                c.putExtra("valor", msg);
                startActivity(c);
                return true;
            default:

                return super.onOptionsItemSelected(item);

        }
    }
}
