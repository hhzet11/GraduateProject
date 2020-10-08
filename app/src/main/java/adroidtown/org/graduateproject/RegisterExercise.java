package adroidtown.org.graduateproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterExercise extends AppCompatActivity {

    Button register,cancel,duplicate;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_exercise);

        final EditText trainingName = findViewById(R.id.editTextExerciseName);
        final EditText trainingCalorie = findViewById(R.id.editTextExerciseCalorie);
        final EditText trainingURL = findViewById(R.id.editTextExerciseURL);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        duplicate = findViewById(R.id.duplicateName);
        duplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readTraining(trainingName.getText().toString());
            }
        });

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = trainingName.getText().toString();
                String getCalorie = trainingCalorie.getText().toString();
                String getURL = trainingURL.getText().toString();

                HashMap result = new HashMap<>();
                result.put("name", getName);
                result.put("calorie", getCalorie);
                result.put("url", getURL);
                writeNewTraining(getName, getCalorie, getURL);
                Toast.makeText(getApplicationContext(),"운동 등록 완료",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(getApplicationContext(),"취소 눌림 -> home 돌아오기", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void writeNewTraining(String trainingName, String trainingCalorie, String trainingURL) {
        Training training = new Training(trainingName, trainingCalorie, trainingURL);

        mDatabase.child("trainings").child(trainingName).setValue(training).addOnSuccessListener(new OnSuccessListener<Void>(){
            public void onSuccess(Void aVoid){
                Toast.makeText(RegisterExercise.this, "데이터베이스에 저장 완료", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){
                Toast.makeText(RegisterExercise.this, "데이터베이스에 저장 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readTraining(final String Name) {
        mDatabase.child("trainings").child(Name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Training.class) != null){
                    Training post = dataSnapshot.getValue(Training.class);
                    if(Name.equals(post.trainingName)) {
                        Toast.makeText(getApplicationContext(), "<중복된 운동입니다>", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "<등록가능한 운동입니다>", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "<확인 실패>", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
