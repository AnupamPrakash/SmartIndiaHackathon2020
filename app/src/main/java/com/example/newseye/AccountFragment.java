package com.example.newseye;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {
    Button signOut;
    ProgressDialog progressdialog;
    ImageView userDp;
    TextView txtname,txtdep,txtemail,txtread,txtshared,txtfav;
    private static int RESULT_LOAD_IMAGE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account,container,false);
        signOut = root.findViewById(R.id.sign_out);
        userDp = root.findViewById(R.id.user_dp);
        txtname = root.findViewById(R.id.txt_name);
        txtdep = root.findViewById(R.id.txt_department);
        txtemail = root.findViewById(R.id.txt_email);
        txtread = root.findViewById(R.id.txt_read);
        txtshared = root.findViewById(R.id.txtshared);
        txtfav = root.findViewById(R.id.txtFavs);
        userDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
        });
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        loadProfile(currentUser);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
            }
        });
        return root;
    }

    private void loadProfile(final FirebaseUser currentUser) {
        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setMessage("Loading...");
        progressdialog.show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressdialog.dismiss();
//                  Toast.makeText(getActivity(), ""+dataSnapshot.child("username").getValue(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), ""+dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                    String name = (String) dataSnapshot.child("username").getValue();
                    String userDP = (String) dataSnapshot.child("userDp").getValue();
                    String userdep = (String) dataSnapshot.child("department").getValue();
                    long artReads = (long) dataSnapshot.child("articlesRead").getValue();
                    long artShared = (long) dataSnapshot.child("articlesShared").getValue();
                    long favorites = (long) dataSnapshot.child("favorites").getValue();
                    if(userDP.equals("Default"))
                        userDp.setImageResource(R.drawable.ic_account_circle_black_24dp);
                    else
                    {
                        Bitmap imageBitmap = null;
                        try {
                            imageBitmap = decodeFromFirebaseBase64(userDP);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        userDp.setImageBitmap(imageBitmap);
                    }
                    txtname.setText(name);
                    txtdep.setText(userdep);
                    txtemail.setText(currentUser.getEmail());
                    txtread.setText(""+artReads);
                    txtshared.setText(""+artShared);
                    txtfav.setText(""+favorites);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        ref.addValueEventListener(valueEventListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            userDp.setImageURI(selectedImage);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            encodeBitmapAndSaveToFirebase(bitmap);
        }
    }
    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference()
                .child("user")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.child("userDp").setValue(imageEncoded);
    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
