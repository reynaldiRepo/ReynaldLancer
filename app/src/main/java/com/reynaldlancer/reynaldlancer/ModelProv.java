package com.reynaldlancer.reynaldlancer;

import com.google.gson.annotations.SerializedName;

public class ModelProv {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name ;

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

    public ModelProv(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
