package com.example.appproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthFragment extends Fragment { // Фрагмент авторизации
    // высвечивается при запуске приложения

    private AutoCompleteTextView mLogin;
    private EditText mPassword;
    private Button mEnter;
    private Button mRegister;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    private ArrayAdapter<String> mLoginedUsersAdapter; // Адаптер для AutoCompleteTextView

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) { // нажатие на кнопку "Войти"

            if (isEmailValid() && isPasswordValid()) {
                User user = mSharedPreferencesHelper.login(
                        mLogin.getText().toString(), mPassword.getText().toString());
                if (user != null) {
                    Intent startProfileIntent = new Intent(getActivity(), ProfileActivity.class);
                    startProfileIntent.putExtra(ProfileActivity.USER_KEY, user);
                    startActivity(startProfileIntent); // Смена активити intent'ом
                    getActivity().finish();
                } else {
                    showMessage(R.string.login_error);
                }
            } else {
                showMessage(R.string.login_input_error);
            }

        }
    };

    private View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
                    .addToBackStack(RegistrationFragment.class.getName()).commit();
            // Смена фрагмента Фрагмент Менеджером
        }
    };

    private View.OnFocusChangeListener mOnLoginFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                mLogin.showDropDown();
            }
        }
    };

    private boolean isEmailValid() { // метод, проверяющий валидность email
        return !TextUtils.isEmpty(mLogin.getText()) && Patterns
                .EMAIL_ADDRESS.matcher(mLogin.getText()).matches();
    }

    private boolean isPasswordValid() {// метод, проверяющий валидность password
        return !TextUtils.isEmpty(mPassword.getText());
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // создание компонентов во фрагменте
        View v = inflater.inflate(R.layout.fr_auth,
                container, false); // создание java-объектов

        mLogin = v.findViewById(R.id.etLogin);
        mPassword = v.findViewById(R.id.etPassword);
        mEnter = v.findViewById(R.id.buttonEnter);
        mRegister = v.findViewById(R.id.buttonRegister);
        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);

        mLoginedUsersAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line,
                mSharedPreferencesHelper.getSuccessLogins());

        mLogin.setAdapter(mLoginedUsersAdapter);
        mLogin.setOnFocusChangeListener(mOnLoginFocusChangeListener);

        return v;
    }

    public static AuthFragment newInstance() { // конструктор
        
        Bundle args = new Bundle();
        
        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }
}