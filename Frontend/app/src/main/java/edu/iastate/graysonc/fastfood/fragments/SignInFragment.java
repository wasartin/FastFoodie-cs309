package edu.iastate.graysonc.fastfood.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.iastate.graysonc.fastfood.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    private Button signInButton; // TODO: make this open SignInActivity

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        signInButton = container.findViewById(R.id.sign_in_button);
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

}
