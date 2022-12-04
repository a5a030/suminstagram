package kr.ac.mjc.itc2019261019.suminstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JoinActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        EditText emailEt = findViewById(R.id.email_et);
        EditText fullNameEt = findViewById(R.id.fullname_et);
        EditText usernameEt = findViewById(R.id.username_et);
        EditText passwordEt = findViewById(R.id.password_et);

        Button joinBtn = findViewById(R.id.join_btn);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String fullName = fullNameEt.getText().toString();
                String username = usernameEt.getText().toString();
                String password = passwordEt.getText().toString();

                int strLength = password.length();

                if(email.equals("")) {
                    Toast.makeText(JoinActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(fullName.equals("")) {
                    Toast.makeText(JoinActivity.this, "성명을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(username.equals("")) {
                    Toast.makeText(JoinActivity.this, "사용자 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.equals("")) {
                    Toast.makeText(JoinActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth auth = FirebaseAuth.getInstance();

//                if(strLength >= 6) {
//                    auth.createUserWithEmailAndPassword(email, password);
//                } else {
//                    Toast.makeText(JoinActivity.this, "비밀번호는 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
//                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser user = authResult.getUser();
                                Log.d("JoinActivity", user.getEmail());
                                finish();
                                Toast.makeText(JoinActivity.this, "회원가입이 완료됐습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("JoinActivity", e.getMessage());
                                Toast.makeText(JoinActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}
