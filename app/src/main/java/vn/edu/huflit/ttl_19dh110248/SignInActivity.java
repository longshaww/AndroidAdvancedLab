package vn.edu.huflit.ttl_19dh110248;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    Button SignUp,SignIn;
    TextInputLayout userName,password;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userName=findViewById(R.id.username);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar);
        firebaseAuth=FirebaseAuth.getInstance();
        SignUp=findViewById(R.id.btnSignUp);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        //sign in
        SignIn=findViewById(R.id.btnSignIn);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=userName.getEditText().getText().toString();
                final String Pass=password.getEditText().getText().toString();
                if(TextUtils.isEmpty(Email)||TextUtils.isEmpty(Pass)){
                    Snackbar.make(v,"Vui lòng nhập đầy đủ thông tin!",Snackbar.LENGTH_LONG).show();
                    return;
                }
//                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                            Snackbar.make(v,"Quá trình đăng nhập đã thất bại.",Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            Intent intent=new Intent(SignInActivity.this, vn.edu.huflit.ttl_19dh110248.main_page.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}