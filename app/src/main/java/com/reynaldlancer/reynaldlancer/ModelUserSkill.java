package com.reynaldlancer.reynaldlancer;

import com.google.gson.annotations.SerializedName;

public class ModelUserSkill {
    @SerializedName("_id")
    String _id;
    @SerializedName("skill")
    String skill;
    @SerializedName("user_id")
    String user_id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ModelUserSkill(String _id, String skill, String user_id) {
        this._id = _id;
        this.skill = skill;
        this.user_id = user_id;
    }
}
