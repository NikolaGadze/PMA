package ba.sum.fpmoz.pma.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import ba.sum.fpmoz.pma.MainActivity;
import ba.sum.fpmoz.pma.R;



public class LogoutFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Sign out the user
        mAuth.signOut();

        // Redirect the user to the MainActivity
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }


}