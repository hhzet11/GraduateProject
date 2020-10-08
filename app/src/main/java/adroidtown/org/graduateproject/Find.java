package adroidtown.org.graduateproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Find extends AppCompatActivity {

    EditText name, email;
    Button find, back;
    TextView result;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        name = findViewById(R.id.writeName);
        email = findViewById(R.id.writeEmail);

        find = findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUser();
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(getApplicationContext(),"뒤로 돌아가기", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readUser() {
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int state = 0;

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    if(name.getText().toString().equalsIgnoreCase(ds.getValue(User.class).userName) &&
                            email.getText().toString().equals(ds.getValue(User.class).userEmail))
                    {
                        result = findViewById(R.id.result);
                        result.setText("아이디: " + ds.getValue(User.class).userId + "\n비밀번호: " + ds.getValue(User.class).userPw);
                        state = 1;
                    }
                }

                if(state == 0){
                    Toast.makeText(getApplicationContext(), "<이름, 이메일을 확인해주세요>", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
