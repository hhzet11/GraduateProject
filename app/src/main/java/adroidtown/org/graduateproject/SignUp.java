package adroidtown.org.graduateproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText editName, editId, editPw, editEmail;
    Button duplicateID, save, back;
    String str_result;
    private RadioButton diet, bulkUp, rehabilitation;
    private RadioGroup radioGroup;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        editName = findViewById(R.id.writeName);
        editId = findViewById(R.id.writeId);
        editPw = findViewById(R.id.writePw);
        editEmail = findViewById(R.id.writeEmail);

        duplicateID = findViewById(R.id.duplicateID);
        duplicateID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUser(editId.getText().toString());
            }
        });
        
        diet = findViewById(R.id.diet);
        bulkUp = findViewById(R.id.bulkUp);
        rehabilitation = findViewById(R.id. rehabilitation);
        
        radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.diet){
                    str_result = diet.getText().toString();
                    Toast.makeText(SignUp.this, "다이어트 눌림", Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.bulkUp){
                    str_result = bulkUp.getText().toString();
                    Toast.makeText(SignUp.this, "벌크업 눌림", Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.rehabilitation){
                    str_result = rehabilitation.getText().toString();
                    Toast.makeText(SignUp.this, "재활 눌림", Toast.LENGTH_SHORT).show();
                }
            }
        });

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = editName.getText().toString();
                String getId = editId.getText().toString();
                String getPw = editPw.getText().toString();
                String getType = str_result;
                String getEmail = editEmail.getText().toString();

                HashMap result = new HashMap<>();
                result.put("name", getName);
                result.put("Id", getId);
                result.put("Pw", getPw);
                result.put("Type", getType);
                result.put("Email", getEmail);
                writeNewUser(getName, getId, getPw, getType, getEmail);
                Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(getApplicationContext(),"이전으로 돌아가기", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void writeNewUser(String userName, String userId, String userPw, String userType, String userEmail) {
        User user = new User(userName, userId, userPw, userType, userEmail);

        mDatabase.child("users").child(userId).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>(){
            public void onSuccess(Void aVoid){
                Toast.makeText(SignUp.this, "데이터베이스에 저장 완료", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){
                Toast.makeText(SignUp.this, "데이터베이스에 저장 실패", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "<중복된 아이디입니다.>", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "<등록가능한 아이디입니다.>", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "<확인 실패>", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
