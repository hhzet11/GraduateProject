package adroidtown.org.graduateproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView homeTraining, settings, foodRecipe;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent passed_intent = getIntent();
        Bundle bundle = passed_intent.getExtras();
        id = bundle.getString("id");


        homeTraining = findViewById(R.id.homeTraining);
        homeTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Exercise.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"home-training 눌림", Toast.LENGTH_SHORT).show();
            }
        });

        foodRecipe = findViewById(R.id.foodRecipe);
        foodRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Food.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"food 눌림", Toast.LENGTH_SHORT).show();
            }
        });

        settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"settings 눌림", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
