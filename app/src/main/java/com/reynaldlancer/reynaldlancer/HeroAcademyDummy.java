package com.reynaldlancer.reynaldlancer;

import java.util.ArrayList;

public class HeroAcademyDummy {
    public HeroAcademyDummy() {
    }

    public ArrayList<HeroAcademyModel> dummy(){
        ArrayList<HeroAcademyModel> data;
        data = new ArrayList<HeroAcademyModel>();

        int image[] = {R.mipmap.academy1, R.mipmap.academy2, R.mipmap.academy3 };
        for (int i = 0; i < image.length; i++){
            data.add(new HeroAcademyModel(image[i]));
        }

        return data;

    }

}
