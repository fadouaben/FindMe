package benhajyedder.fadoua.findme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MySMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String messageBody, phoneNumber;
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    messageBody = messages[0].getMessageBody();
                    phoneNumber = messages[0].getDisplayOriginatingAddress();

                    Toast.makeText(context,
                                    "Message : " + messageBody + "Reçu de la part de;" + phoneNumber,
                                    Toast.LENGTH_LONG)
                            .show();
                    if (messageBody.contains("FindMe: Envoyer moi votre position")){
                        Intent i = new Intent(context,MyLocationService.class);
                        i.putExtra("PHONE",phoneNumber);
                        context.startService(i);
                    }
                    if(messageBody.contains("FindMe: MA position est : ")){
                        String []t=messageBody.split("#");
                        String longit=t[2];
                        String latitude=t[1];

                        // lancer une notification dans le canal myapplication_channel
                        NotificationCompat.Builder mynotif =
                                new NotificationCompat.Builder(
                                        context,
                                        "canal");

                        mynotif.setContentTitle("FindMe!");
                        mynotif.setContentText("c'est la position demandée.");
                        mynotif.setSmallIcon(android.R.drawable.ic_dialog_map);
                        mynotif.setAutoCancel(true);

                        Intent i=new Intent(context,MapsActivity.class);
                        i.putExtra("Longit",longit);
                        i.putExtra("Latitude",latitude);
                        PendingIntent pi = PendingIntent.getActivity(context,1,i,PendingIntent.FLAG_MUTABLE);
                        mynotif.setContentIntent(pi);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
                        NotificationChannel channel = new NotificationChannel("canal","canal pour lapplication find me",
                                NotificationManager.IMPORTANCE_DEFAULT);
                        managerCompat.createNotificationChannel(channel);
                        managerCompat.notify(1,mynotif.build());

                        }
                    }


                }
            }
        }
    }
