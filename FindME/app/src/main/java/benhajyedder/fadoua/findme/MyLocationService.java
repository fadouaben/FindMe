package benhajyedder.fadoua.findme;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.telephony.SmsManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MyLocationService extends Service {
    public MyLocationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String num = intent.getStringExtra("PHONE");
        //localisation

        /*
        LocationManager
         */
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(num,null,"FindMe: MA position est : #"+latitude +"#"+longitude,null,null);

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}