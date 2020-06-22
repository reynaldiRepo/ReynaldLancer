package com.reynaldlancer.reynaldlancer;

import android.net.Uri;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class FirebaseHelper {
    public FirebaseHelper() {
    }


    private final String PhotoProfileDir = "photo_profile";

    public String getPhotoProfileDir() {
        return PhotoProfileDir;
    }

    public  void load_iamge(String dir, String fileName, ImageView Target){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathtofolder_pp = storageReference.child(dir);
        pathtofolder_pp.child(fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Target);
            }
        });
    }
}
