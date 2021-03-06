package com.example.appproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesHelper { // для хранения пользователей

    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    public static final String USERS_KEY = "USERS_KEY";
    public static final Type USERS_TYPE = new TypeToken<List<User>>() {}.getType();

    private SharedPreferences mSharedPreferences;
    private Gson mGson = new Gson();

    public SharedPreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public List<User> getUsers() { // получить лист всех пользователей
        List<User> users = mGson.fromJson(mSharedPreferences.getString(USERS_KEY, ""), USERS_TYPE);
        return users == null ? new ArrayList<User>() : users;
    }

    public boolean addUser(User user) { // Добавление пользователя
        List<User> users = getUsers();
        for (User u : users) {
            if (u.getLogin().equalsIgnoreCase(user.getLogin())) { // Если такой логин уже зарегистрирован
                return false;
            }
        }
        users.add(user);
        mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(users, USERS_TYPE)).apply();
        return true;
    }

    public User login(String login, String password) {
        List<User> users = getUsers();
        for (User u: users) {
            if (login.equalsIgnoreCase(u.getLogin()) && password.equals(u.getPassword())) {
                u.setHasSuccessLogin(true);
                mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(users, USERS_TYPE)).apply();
                return u;
            }
        }
        return null;
    }

    public List<String> getSuccessLogins() {
        List<String> successLogins = new ArrayList<>();
        List<User> allUsers = getUsers();
        for (User u : allUsers) {
            if (u.hasSuccessLogin()) {
                successLogins.add(u.getLogin());
            }
        }
        return successLogins;
    }
}
