package adroidtown.org.graduateproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private DatabaseReference mDatabase;
    EditText id, pw;
    Button login, signup, find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        id = findViewById(R.id.id);
        pw = findViewById(R.id.pw);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUser(id.getText().toString());
            }
        });

        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"회원가입 눌림", Toast.LENGTH_SHORT).show();
            }
        });

        find = findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Find.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"ID/PW 찾기 눌림", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void readUser(final String ID) {
        mDatabase.child("users").child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(User.class) != null){
                    User post = dataSnapshot.getValue(User.class);
                    if(id.getText().toString().equals(post.userId)) {
                        if(pw.getText().toString().equals(post.userPw)) {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            Bundle bundle = new Bundle();
                            String text_id = id.getText().toString();
                            bundle.putString("id",text_id);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "<로그인 성공>", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "<잘못된 패스워드입니다>", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "<등록되지않은 아이디입니다>", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "<데이터베이스 연결 실패>", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
