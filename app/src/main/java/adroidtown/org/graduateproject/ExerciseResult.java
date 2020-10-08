package adroidtown.org.graduateproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExerciseResult extends AppCompatActivity {

    private DatabaseReference mDatabase;
    static final String[] exercise_list = {"사이드런지","레그레이즈","더블크런치","와이드스쿼트","팔벌려뛰기"};
    static final double[] exercise_time = {10, 12, 22, 14, 15};
    static final String[] finalList = new String[5];
    Button home;
    TextView calorie;
    double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        calorie = findViewById(R.id.calorie);

        mDatabase.child("trainings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int state = 0;
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    for(int i = 0; i < exercise_list.length; i++){
                        String name = exercise_list[i];
                        if(name.equalsIgnoreCase(ds.getValue(Training.class).trainingName)){
                            String calorie = ds.getValue(Training.class).trainingCalorie;
                            double dbCalorie = Double.parseDouble(calorie);
                            double finalCalorie = (dbCalorie / 10 ) * exercise_time[i];
                            total += finalCalorie;
                            state = 1;
                        }
                    }
                    calorie.setText(Double.toString(Math.round(total * 1000) / 1000));
                }
                if(state == 0){
                    Toast.makeText(getApplicationContext(), "존재하지 않는 운동 이름", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "<확인 실패>", Toast.LENGTH_SHORT).show();
            }
        });


        for(int i = 0; i < exercise_list.length; i++){
            String time = Double.toString(exercise_time[i]);
            finalList[i] = exercise_list[i] + " -> " + time + "분";
        }


        ListView listView = findViewById(R.id.exerciseResultView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, finalList);
        listView.setAdapter(adapter);

        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"홈으로 돌아가기", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
