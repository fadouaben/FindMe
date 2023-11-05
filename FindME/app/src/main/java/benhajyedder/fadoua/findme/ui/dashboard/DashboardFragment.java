package benhajyedder.fadoua.findme.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import benhajyedder.fadoua.findme.R;
import benhajyedder.fadoua.findme.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        /*LayoutInflater inf = LayoutInflater.from(con);
        View l = inf.inflate(R.layout.fragment_dashboard,null);
        btnsend = l.findViewById(R.id.btnsendSMS);
        edtnum = l.findViewById(R.id.edNum_dashboard);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(con, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                    sendsms(edtnum.getText().toString(),"Bonjour");
                }
                else {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},1);
                }
            }
        });
        */
        binding.btnsendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendsms(binding.edNumDashboard.getText().toString(),"FindMe: Envoyer moi votre position");
                SmsManager smsManager = SmsManager.getDefault();//carte sim par defaut
                smsManager.sendTextMessage(binding.edNumDashboard.getText().toString(),null,"FindMe: Envoyer moi votre position",null,null);
            }
        });
        return root;
    }

    public void sendsms(String numero,String msg){
        SmsManager smsManager = SmsManager.getDefault();//carte sim par defaut
        smsManager.sendTextMessage(numero,null,msg,null,null);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}