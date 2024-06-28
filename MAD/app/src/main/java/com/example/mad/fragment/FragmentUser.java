package com.example.mad.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mad.MainActivity;
import com.example.mad.R;
import com.example.mad.SignInActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUser.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentUser newInstance(String param1, String param2) {
        FragmentUser fragment = new FragmentUser();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    private SharedPreferences sharedPreferences;
    private TextView nameOfUser, dobOfUser, phoneNumberOfUser;
    private Button btnLogout;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInstance(view);
        fetchUserInfo();
        setupListeners();
    }

    private void fetchUserInfo() {
        sharedPreferences = requireContext().getSharedPreferences("login_status", Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("name" ,"") + " ID: "+sharedPreferences.getInt("userId" ,0);
        nameOfUser.setText(nameOfUser.getText()+s);
        dobOfUser.setText(dobOfUser.getText()+sharedPreferences.getString("dob" ,""));
        phoneNumberOfUser.setText(phoneNumberOfUser.getText()+sharedPreferences.getString("phoneNumber" ,""));
    }

    private void setupListeners() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = requireContext().getSharedPreferences("login_status", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();

                edit.clear();
                edit.commit();
                Intent login = new Intent(requireActivity(), SignInActivity.class);
                startActivity(login);
            }
        });
    }

    private void initInstance(View view) {
        nameOfUser = view.findViewById(R.id.nameOfUser);
        dobOfUser = view.findViewById(R.id.dobOfUser);
        phoneNumberOfUser = view.findViewById(R.id.phoneNumberOfUser);
        btnLogout = view.findViewById(R.id.btnLogout);
    }

}