package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class FirebaseHelper {
    public FirebaseHelper() {
    }


    private final String PhotoProfileDir = "photo_profile";

    public String getPhotoProfileDir() {
        return PhotoProfileDir;
    }

    public  void load_iamge(Context context ,String dir, String fileName, ImageView Target){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathtofolder_pp = storageReference.child(dir);
        pathtofolder_pp.child(fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).centerCrop().into(Target);
            }
        });
    }

    public void uploadImage(Uri filePath, String dir, String filename , Context ctx, FragmentManager fmforLoading) {
        if(filePath != null)
        {
            LoadingDialog loadingDialog = new LoadingDialog();
            loadingDialog.show(fmforLoading, "Loading");
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            StorageReference path_to_firebase = storageReference.child(dir);
            StorageReference path_for_upload = path_to_firebase.child(filename);
            path_for_upload.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ctx, "Success Upload", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ctx, "Failed Upload"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }
            });
        }else{
            Toast.makeText(ctx, "No File", Toast.LENGTH_SHORT).show();
        }
    }
}
