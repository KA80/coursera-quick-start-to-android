package com.example.appproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationFragment extends Fragment { // Фрагмент регистрации
    // Показывается при нажатии на кнопку "Регистрация"

    private EditText mLogin;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private Button mRegistration;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    private View.OnClickListener mOnRegistrationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) { // Нажатие на кнопку зарегистрироваться
            if (isInputValid()) {
                boolean isAdded =  mSharedPreferencesHelper.addUser
                        (new User(mLogin.getText().toString(), mPassword.getText().toString()));
                // isAdded хранит в себе true or false,
                // проверка на то, что этого пользователя не было, если не было - добавляется
                if (isAdded) {
                    showMessage(R.string.login_register_success);
                } else {
                    showMessage(R.string.login_register_error);
                }
            } else {
                showMessage(R.string.login_input_error);
            }
        }
    };

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // создание компонентов во фрагменте
        View v = inflater.inflate(R.layout.fr_registration,
                container, false);// создание java-объектов
        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        mLogin = v.findViewById(R.id.etLogin);
        mPassword = v.findViewById(R.id.etPassword);
        mPasswordAgain = v.findViewById(R.id.tvPasswordAgain);
        mRegistration = v.findViewById(R.id.btnRegistration);

        mRegistration.setOnClickListener(mOnRegistrationClickListener);
        return v;
    }

    private boolean isInputValid() { // метод, проверяющий на валидность введенных данных
        String email = mLogin.getText().toString();
        if (isEmailValid(email) && isPasswordValid()) {
            return true;
        }
        return false;
    }

    private boolean isEmailValid(String email) { // проверяет валидность email
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid() { // проверяет валидность пароля
        String password = mPassword.getText().toString();
        String passwordAgain = mPasswordAgain.getText().toString();

        return password.equals(passwordAgain) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain);
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }
}