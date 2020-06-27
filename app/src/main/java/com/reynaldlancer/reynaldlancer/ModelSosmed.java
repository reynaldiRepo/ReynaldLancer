package com.reynaldlancer.reynaldlancer;

import com.google.gson.annotations.SerializedName;

public class ModelSosmed {
    @SerializedName("_id")
    String _id;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("sosmed_type")
    String sosmed_type;
    @SerializedName("link_sosmed")
    String link_sosmed;

    public ModelSosmed(String _id, String user_id, String sosmed_type, String link_sosmed) {
        this._id = _id;
        this.user_id = user_id;
        this.sosmed_type = sosmed_type;
        this.link_sosmed = link_sosmed;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSosmed_type() {
        return sosmed_type;
    }

    public void setSosmed_type(String sosmed_type) {
        this.sosmed_type = sosmed_type;
    }

    public String getLink_sosmed() {
        return link_sosmed;
    }

    public void setLink_sosmed(String link_sosmed) {
        this.link_sosmed = link_sosmed;
    }
}
