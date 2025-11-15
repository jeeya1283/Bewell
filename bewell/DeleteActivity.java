package com.example.bewell;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
   private TextView deleteacc_back;
    private EditText deleteacc_email, deleteacc_pass;
    private Button deleteacc_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        auth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        deleteacc_back=findViewById(R.id.deleteacc_back);
        deleteacc_email=findViewById(R.id.deleteacc_email);
        deleteacc_pass=findViewById(R.id.deleteacc_pass);
        deleteacc_delete=findViewById(R.id.deleteacc_delete);
        deleteacc_delete.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        String EMAILID = deleteacc_email.getText().toString();
        String PASS = deleteacc_pass.getText().toString();

        if (EMAILID.isEmpty() || PASS.isEmpty()) {
            Toast.makeText(DeleteActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthCredential credential = EmailAuthProvider.getCredential(EMAILID, PASS);
        FirebaseUser user = auth.getCurrentUser();
        user.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.delete().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        DatabaseReference userReference = firebaseDatabase.getReference("user_data" + user.getUid());
                        userReference.removeValue();

                        auth.signOut();
                        Intent intent = new Intent(DeleteActivity.this, loginActivity.class);
                        Toast.makeText(DeleteActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(DeleteActivity.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(DeleteActivity.this, "Failed to re-authenticate user", Toast.LENGTH_SHORT).show();
            }
        });
        deleteacc_back.setOnClickListener(v -> {
            Intent i = new Intent(DeleteActivity.this, loginActivity.class);
            startActivity(i);
        });
    }
}