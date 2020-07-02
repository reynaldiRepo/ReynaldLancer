package com.reynaldlancer.reynaldlancer;

import android.net.Uri;

public class ImageDeskripsiModel {
    Uri image_uri;

    public Uri getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(Uri image_uri) {
        this.image_uri = image_uri;
    }

    public ImageDeskripsiModel(Uri image_uri) {
        this.image_uri = image_uri;
    }
}
