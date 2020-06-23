package com.reynaldlancer.reynaldlancer;

import com.google.gson.annotations.SerializedName;

public class ModelKab {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("province_id")
    String province_id;

    public ModelKab(String id, String name, String province_id) {
        this.id = id;
        this.name = name;
        this.province_id = province_id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }
}
