package adroidtown.org.graduateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    TextView edit, register;
    Button back;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent passed_intent = getIntent();
        Bundle bundle = passed_intent.getExtras();
        id = bundle.getString("id");

        edit = findViewById(R.id.userEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"회원정보 수정으로 넘어가기", Toast.LENGTH_SHORT).show();
            }
        });

        register = findViewById(R.id.exerciseRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterExercise.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"운동등록 창으로 넘어가기", Toast.LENGTH_SHORT).show();
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
}
