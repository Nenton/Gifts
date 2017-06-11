package com.nenton.photon.data.network.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by serge on 07.06.2017.
 */

public class UserInfo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("albumCount")
    @Expose
    private int albumCount;
    @SerializedName("photocardCount")
    @Expose
    private int photocardCount;
    @SerializedName("albums")
    @Expose
    private List<Album> albums = null;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getAlbumCount() {
        return albumCount;
    }

    public int getPhotocardCount() {
        return photocardCount;
    }

    public List<Album> getAlbums() {
        return albums;
    }
}


