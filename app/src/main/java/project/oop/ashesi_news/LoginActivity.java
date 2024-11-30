package project.oop.ashesi_news;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText loginUsername, loginEmail, loginPassword;
    Button loginButton;

    ImageButton googleSigninButton;

    TextView signupRedirectText;

    FirebaseAuth auth;

    GoogleSignInClient googleSignInClient;
    
    ActivityResultLauncher<Intent> googleSignInLauncher;
    
    FirebaseDatabase database;
    
    DatabaseReference reference;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loginUsername = findViewById(R.id.login_username);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        auth = FirebaseAuth.getInstance();
        googleSigninButton = findViewById(R.id.google_login_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        try {
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            firebaseAuthWithGoogle(account);
                        } catch(ApiException e) {
                            Toast.makeText(LoginActivity.this, "Sign Up Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString();
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if(!password.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else{
                        loginPassword.setError("Password Cannot Be Empty");
                    }
                } else if(username.isEmpty()) {
                    loginUsername.setError("Username Cannot Be Empty");
                }else if(email.isEmpty()) {
                    loginEmail.setError("Email Cannot Be Empty");
                } else{
                    loginEmail.setError("Please Enter Valid Email");
                }
            }
        });

        googleSigninButton.setOnClickListener(view -> {
            Intent intent = googleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(intent);
        });
        
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.child(userId).child("name").setValue(user.getDisplayName());
                            reference.child(userId).child("email").setValue(user.getEmail());
                            reference.child(userId).child("username").setValue(user.getEmail().split("@")[0]);
                            Toast.makeText(LoginActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
//    public Boolean validateUsername() {
//        String val = loginUsername.getText().toString();
//        if (val.isEmpty()) {
//            loginUsername.setError("Username cannot be empty");
//            return false;
//        } else {
//            loginUsername.setError(null);
//            return true;
//        }
//    }
//    public Boolean validatePassword(){
//        String val = loginPassword.getText().toString();
//        if (val.isEmpty()) {
//            loginPassword.setError("Password cannot be empty");
//            return false;
//        } else {
//            loginPassword.setError(null);
//            return true;
//        }
//    }
//    public void checkUser(){
//        String userUsername = loginUsername.getText().toString().trim();
//        String userPassword = loginPassword.getText().toString().trim();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    loginUsername.setError(null);
//                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
//                    if (passwordFromDB.equals(userPassword)) {
//                        loginUsername.setError(null);
//                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
//                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
//                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("name", nameFromDB);
//                        intent.putExtra("email", emailFromDB);
//                        intent.putExtra("username", usernameFromDB);
//                        intent.putExtra("password", passwordFromDB);
//                        startActivity(intent);
//                    } else {
//                        loginPassword.setError("Invalid Credentials");
//                        loginPassword.requestFocus();
//                    }
//                } else {
//                    loginUsername.setError("User does not exist");
//                    loginUsername.requestFocus();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
}