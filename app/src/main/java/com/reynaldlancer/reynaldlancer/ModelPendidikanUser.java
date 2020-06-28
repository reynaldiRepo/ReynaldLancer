package com.reynaldlancer.reynaldlancer;

import com.google.gson.annotations.SerializedName;

public class ModelPendidikanUser {
    @SerializedName("_id")
    String _id;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("pendidikan")
    String pendidikan;
    @SerializedName("tingkat")
    String tingkat;

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

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getTingkat() {
        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }

    public ModelPendidikanUser(String _id, String user_id, String pendidikan, String tingkat) {
        this._id = _id;
        this.user_id = user_id;
        this.pendidikan = pendidikan;
        this.tingkat = tingkat;
    }
}
