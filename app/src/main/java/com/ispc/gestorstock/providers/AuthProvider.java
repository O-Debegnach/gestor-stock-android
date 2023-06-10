package com.ispc.gestorstock.providers;

import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ispc.gestorstock.models.User;

public class AuthProvider {

    FirebaseAuth mAuth;
    private UserProvider mUserProvider;

    public AuthProvider(){
        mAuth = FirebaseAuth.getInstance();
        mUserProvider = new UserProvider();
    }


    public String getUID() {
        var id = mAuth.getCurrentUser().getUid();
        if(!id.isBlank()){
            return id;
        }
        return "";
    }
    public Task<AuthResult> createUser(String name, String email, String password) {
        var cu = mAuth.createUserWithEmailAndPassword(email, password);
        cu.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = new User(getUID(), name, email);
                mUserProvider.create(user);
            }
        });
        return cu;
    }

}
