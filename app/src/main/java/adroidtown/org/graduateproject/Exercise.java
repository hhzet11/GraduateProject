package adroidtown.org.graduateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Exercise extends AppCompatActivity {

    Button start;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent passed_intent = getIntent();
        Bundle bundle = passed_intent.getExtras();
        id = bundle.getString("id");

        start = findViewById(R.id.exerciseStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExerciseList.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"운동시작 눌림", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
