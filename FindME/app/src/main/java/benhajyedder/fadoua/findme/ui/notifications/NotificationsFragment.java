package benhajyedder.fadoua.findme.ui.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import benhajyedder.fadoua.findme.MainActivity;
import benhajyedder.fadoua.findme.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // lancer une notification dans le canal myapplication_channel
        NotificationCompat.Builder mynotif =
                new NotificationCompat.Builder(
                        getActivity().getApplicationContext(),
                        "myapplication_channel");

        mynotif.setContentTitle("titre içi!");
        mynotif.setContentText("votre message.");
        mynotif.setSmallIcon(android.R.drawable.ic_dialog_map);
        mynotif.setAutoCancel(true);

        Intent i=new Intent(getActivity().getApplicationContext(),
                MainActivity.class);
        PendingIntent pi= PendingIntent.getActivity(
                getActivity().getApplicationContext(),
                0,
                i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mynotif.setContentIntent(pi);

        Uri son= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mynotif.setSound(son);
        // instance du gestionnaire des notifications de l'appareil
        NotificationManagerCompat manager=
                NotificationManagerCompat.from(getActivity().getApplicationContext());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
/* creation du canal si la version android de l'appareil est supérieur à
Oreo */
            NotificationChannel canal=new
                    NotificationChannel("myapplication_channel",
                    // l'ID exacte du canal
                    "canal pour lapplication find me",
                    NotificationManager.IMPORTANCE_DEFAULT);

            AudioAttributes attr=new AudioAttributes.Builder()

                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            // ajouter du son pour le canal
            canal.setSound(son,attr);
            // creation du canal dans l'appareil
            manager.createNotificationChannel(canal);

        }
        try {
            manager.notify(0,mynotif.build());

        }
        catch (SecurityException e){

        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}