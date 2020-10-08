package adroidtown.org.graduateproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserEdit extends AppCompatActivity {

    private DatabaseReference mDatabase;
    TextView writeName, writeId;
    EditText writePw, writeEmail;

    private RadioButton diet, bulkUp, rehabilitation;
    private RadioGroup radioGroup;

    Button save, back;
    String id, str_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent passed_intent = getIntent();
        Bundle bundle = passed_intent.getExtras();
        id = bundle.getString("id");

        writeName = findViewById(R.id.writeName);
        writeId = findViewById(R.id.writeId);
        writePw = findViewById(R.id.writePw);
        writeEmail = findViewById(R.id.writeEmail);

        readUser(id);

        diet = findViewById(R.id.diet);
        bulkUp = findViewById(R.id.bulkUp);
        rehabilitation = findViewById(R.id. rehabilitation);

        radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.diet){
                    str_result = diet.getText().toString();
                    Toast.makeText(getApplicationContext(), "다이어트 눌림", Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.bulkUp){
                    str_result = bulkUp.getText().toString();
                    Toast.makeText(getApplicationContext(), "벌크업 눌림", Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.rehabilitation){
                    str_result = rehabilitation.getText().toString();
                    Toast.makeText(getApplicationContext(), "재활 눌림", Toast.LENGTH_SHORT).show();
                }
            }
        });

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getName = writeName.getText().toString();
                String getId = writeId.getText().toString();
                String getPw = writePw.getText().toString();
                String getType = str_result;
                String getEmail = writeEmail.getText().toString();

                HashMap result = new HashMap<>();
                result.put("Id", getId);
                result.put("Name", getName);
                result.put("Pw", getPw);
                result.put("Type", getType);
                result.put("Email", getEmail);
                editUser(getName, getId, getPw, getType, getEmail);
                Toast.makeText(getApplicationContext(),"회원정보 수정하기", Toast.LENGTH_SHORT).show();
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(getApplicationContext(),"이전으로 돌아가기", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editUser(String Name, String Id, String Pw, String Type, String Email){
            User user = new User(Name, Id, Pw, Type, Email);

            mDatabase.child("users").child(Id).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>(){
                public void onSuccess(Void aVoid){
                    finish();
                    Toast.makeText(getApplicationContext(), "데이터베이스에 저장 완료", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e){
                    Toast.makeText(getApplicationContext(), "데이터베이스에 저장 실패", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void readUser(final String Id){
        mDatabase.child("users").child(Id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(User.class) != null){
                    User post = dataSnapshot.getValue(User.class);
                    if(Id.equalsIgnoreCase(post.userId)) {
                        writeName.setText(post.userName);
                        writeId.setText(post.userId);
                        writePw.setText(post.userPw);
                        writeEmail.setText(post.userEmail);
                        Toast.makeText(getApplicationContext(), "<회원정보 세팅 완료>", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "<회원정보 세팅 실패>", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "<데이터베이스 오류>", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
