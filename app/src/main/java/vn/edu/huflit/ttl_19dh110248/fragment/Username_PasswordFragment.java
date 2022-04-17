package vn.edu.huflit.ttl_19dh110248.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import vn.edu.huflit.ttl_19dh110248.R;
import vn.edu.huflit.ttl_19dh110248.SignIn;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Username_PasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Username_PasswordFragment extends Fragment {
    Button btnRegister;
    NavController navController;
    TextInputLayout email,pass,confirm;
    ProgressBar progressBar;
    FirebaseAuth fAuth=FirebaseAuth.getInstance();
    String userID;
    FirebaseDatabase firebaseDatabase;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Username_PasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Username_PasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Username_PasswordFragment newInstance(String param1, String param2) {
        Username_PasswordFragment fragment = new Username_PasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_username__password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        btnRegister = view.findViewById(R.id.button3);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.password);
        confirm = view.findViewById(R.id.confirmPassword);
        progressBar = view.findViewById(R.id.progressBar2);
        fAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        btnRegister.setOnClickListener(v -> {

            String Firstname = getArguments().getString("Firstname");
            String Lastname = getArguments().getString("Lastname");
            double latitude = getArguments().getDouble("latitude");
            double longitude = getArguments().getDouble("longitude");
            String Phone = getArguments().getString("Phone");
            String Address = getArguments().getString("Address");
            String Email = email.getEditText().getText().toString();
            String Pass = pass.getEditText().getText().toString();
            String Confirm = confirm.getEditText().getText().toString();
            if (Email.isEmpty() || Pass.isEmpty() || Confirm.isEmpty()) {
                Snackbar.make(view, "Vui lòng nhập đầy đủ thông tin!", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (!Confirm.equals(Pass)) {
                    Snackbar.make(view, "Mật khẩu không trùng khớp!", Snackbar.LENGTH_LONG).show();
                    return;
            }
            if(Pass.length()<6){
                Snackbar.make(view, "Vui lòng nhập mật khẩu 6 ký tự!", Snackbar.LENGTH_LONG).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            fAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if (!task.isComplete()){
                    Snackbar.make(view, "Quá trình đăng ký đã thất bại.", Snackbar.LENGTH_LONG).show();
                    return;
                }
                else {
                    userID = fAuth.getCurrentUser().getUid();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    Map<String,Object> user=new HashMap<>();
                    user.put("firstname",Firstname);
                    user.put("lastname",Lastname);
                    user.put("address",Address);
                    user.put("email",Email);
                    user.put("phone", Phone);
                    user.put("latitude", latitude);
                    user.put("longitude", longitude);
                    databaseReference.child("users").child(userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent=new Intent(view.getContext(), SignIn.class);
                            intent.putExtra("email",Email);
                            startActivity(intent);
                        }
                    });
                }
            });
        });
    }
}