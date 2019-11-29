package com.example.ilm.pra1moviles.drawer.session;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.ilm.pra1moviles.LoginActivity;
import com.example.ilm.pra1moviles.R;

public class SignOutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_send, container, false);
        new AlertDialog.Builder(root.getContext())
                .setTitle("Cerrando sesión...")
                .setMessage("¿Deseas cerrar la sesión?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.confirm_dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(root.getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("registered", false);
                        editor.putString("username",  null);
                        editor.putString("password", null);
                        editor.apply();
                        Intent intent = new Intent(root.getContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(root.getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(R.string.confirm_dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    //GO TO PRODUCTS LIST
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_gallery);}}).show();

        return root;
    }
}