package kr.ac.mjc.itc2019261019.suminstagram;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddFragment extends Fragment {
    ImageView imageIv;
    EditText textEt;
    Button uploadBtn;

    Uri selectedImage;

    final int REQ_IMAGE_PICK = 1000;

    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseFirestore fireStore;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageIv = view.findViewById(R.id.image_iv);
        textEt = view.findViewById(R.id.text_et);
        uploadBtn = view.findViewById(R.id.upload_btn);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQ_IMAGE_PICK);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedImage == null) {
                    Toast.makeText(getContext(), "이미지를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                String text = textEt.getText().toString();

                String fileName = UUID.randomUUID().toString();
                storage.getReference().child("image").child(fileName)
                        .putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String imageUri = uri.toString();
                                                Log.d("AddFragment", imageUri);

                                                Post post = new Post();

                                                String username;
                                                String text = textEt.getText().toString();

//                                                post.getUsername(username); //가입 시 입력한 username
                                                post.setText(text);
                                                post.setImageUri(imageUri);

                                                uploadPost(post);
                                            }
                                        });
                            }
                        });
            }
        });
    }

    public void uploadPost(Post post) {
        fireStore.collection("posts"). add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        imageIv.setImageDrawable(getResources().getDrawable(R.drawable.add_circle));
                        textEt.setText("");
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_IMAGE_PICK && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            imageIv.setImageURI(selectedImage);
        }
    }
}
