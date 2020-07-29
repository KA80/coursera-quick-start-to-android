package com.example.appproject;

import androidx.fragment.app.Fragment;

public class AuthActivity extends SingleFragmentActivity { // основное Активити,
    // действующее в авторизации и регистрации пользователя

    @Override
    protected Fragment getFragment() { // Создание фрагмента
        return AuthFragment.newInstance();
    }

    @Override
    protected String getFragName() {
        return AuthActivity.class.getName();
    }
}
