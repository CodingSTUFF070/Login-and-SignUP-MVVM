package com.codingstuff.loginsignupmvvm.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codingstuff.loginsignupmvvm.R;
import com.codingstuff.loginsignupmvvm.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends Fragment {

    private EditText emailEdit, passEdit;
    private TextView signInText;
    private Button signUpBtn;
    private AuthViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        viewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                  navController.navigate(R.id.action_signUpFragment_to_signInFragment);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailEdit = view.findViewById(R.id.emailEditSignUp);
        passEdit = view.findViewById(R.id.passEditSignUp);
        signInText = view.findViewById(R.id.signInText);
        signUpBtn = view.findViewById(R.id.signUpBtn);

        navController = Navigation.findNavController(view);

        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String pass = passEdit.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()){
                    viewModel.register(email , pass);
                }
            }
        });
    }
}