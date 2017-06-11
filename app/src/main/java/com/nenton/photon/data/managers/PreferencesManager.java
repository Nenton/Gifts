package com.nenton.photon.data.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nenton.photon.data.storage.dto.UserInfoDto;

public class PreferencesManager {

    public static final String PROFILE_USER_ID = "PROFILE_USER_ID";
    public static final String PROFILE_NAME = "PROFILE_NAME";
    public static final String PROFILE_LOGIN = "PROFILE_LOGIN";
    public static final String PROFILE_AVATAR_KEY = "PROFILE_AVATAR_KEY";
    public static final String PROFILE_AUTH_TOKEN_KEY = "PROFILE_AUTH_TOKEN_KEY";
    public static final String PRODUCT_LAST_UPDATE_KEY = "PRODUCT_LAST_UPDATE_KEY";
    public static final String DEFAULT_LAST_UPDATE = "Thu Jan 1 1970 00:00:00 GMT+0000 (UTC)";

    private final SharedPreferences mSharedPreferences;

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public PreferencesManager(Context context) {
        mSharedPreferences = context.getSharedPreferences("Photon", Context.MODE_PRIVATE);
    }

    public String getLastProductUpdate(){
        return mSharedPreferences.getString(PRODUCT_LAST_UPDATE_KEY, DEFAULT_LAST_UPDATE);
    }

    public void saveLastProductUpdate(String lastModified){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PRODUCT_LAST_UPDATE_KEY, lastModified);
        editor.apply();
    }

    public void saveUserAvatar(@NonNull String uri){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PROFILE_AVATAR_KEY, uri);
        editor.apply();
    }



    public void saveToken(String token){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PROFILE_AUTH_TOKEN_KEY, token);
        editor.apply();
    }

    public boolean isUserAuth(){
        return mSharedPreferences.getString(PROFILE_AUTH_TOKEN_KEY, null) != null;
    }



    public void saveUserInfo(UserInfoDto infoDto) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PROFILE_USER_ID, infoDto.getId());
        editor.putString(PROFILE_NAME, infoDto.getName());
        editor.putString(PROFILE_LOGIN, infoDto.getLogin());
        editor.putString(PROFILE_AVATAR_KEY, infoDto.getAvatar());
        editor.putString(PROFILE_AUTH_TOKEN_KEY, infoDto.getToken());
        editor.apply();
    }

    public void removeUserInfo(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.remove(PROFILE_USER_ID);
            editor.remove(PROFILE_NAME);
            editor.remove(PROFILE_LOGIN);
            editor.remove(PROFILE_AVATAR_KEY);
            editor.remove(PROFILE_AUTH_TOKEN_KEY);
        editor.apply();
    }

    @Nullable
    public String getUserId() {
        return mSharedPreferences.getString(PROFILE_USER_ID, null);
    }

    @NonNull
    public String getUserName() {
        return mSharedPreferences.getString(PROFILE_NAME, "Не авторизован");
    }

    @NonNull
    public String getUserLogin() {
        return mSharedPreferences.getString(PROFILE_LOGIN, "");
    }

    @Nullable
    public String getUserAvatar() {
        return mSharedPreferences.getString(PROFILE_AVATAR_KEY, null);
    }

    public String getAuthToken(){
        return mSharedPreferences.getString(PROFILE_AUTH_TOKEN_KEY, null);
    }

    public UserInfoDto getUserInfo(){
        return new UserInfoDto(mSharedPreferences.getString(PROFILE_USER_ID, ""),
                mSharedPreferences.getString(PROFILE_NAME, ""),
                mSharedPreferences.getString(PROFILE_LOGIN, ""),
                mSharedPreferences.getString(PROFILE_AVATAR_KEY, ""));
    }

}