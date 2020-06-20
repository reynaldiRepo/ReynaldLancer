package com.reynaldlancer.reynaldlancer;

import android.widget.EditText;

import java.util.regex.Pattern;

public class EDValidation {
    public EDValidation() {
    }

    public boolean required(EditText editText) {
        if (editText.getText().toString().length() == 0) {
            editText.setError("Harus diisi");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public boolean email_validate(EditText editText) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        String email = editText.getText().toString();
        Pattern pat = Pattern.compile(emailRegex);
        if (email == "") {
            editText.setError("Harus Diisi");
            return false;
        } else {
            if (pat.matcher(email).matches()){
                editText.setError(null);
                return true;
            }else{
                editText.setError("Format email");
                return  false;
            }
        }
    }

    public  boolean input_password(EditText editText, int lenght){
        if (editText.getText().toString().length() < lenght){
            editText.setError("Kurang dari 8 Character");
            return  false;
        }else{
            editText.setError(null);
            return  true;
        }
    }


}
