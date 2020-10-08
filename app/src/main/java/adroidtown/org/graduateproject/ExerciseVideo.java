package adroidtown.org.graduateproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExerciseVideo extends YouTubeBaseActivity {

    private DatabaseReference mDatabase;
    YouTubePlayerView youtubeView;
    YouTubePlayer.OnInitializedListener listener;
    Button youtubeBtn, back;
    TextView textView;
    String name, strURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_video);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        textView = findViewById(R.id.textView);
        textView.setText(name);

        youtubeView = findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b){
                mDatabase.child("trainings").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int state = 0;
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            if(name.equalsIgnoreCase(ds.getValue(Training.class).trainingName)){
                                strURL = ds.getValue(Training.class).trainingURL;
                                Toast.makeText(getApplicationContext(), "url 주소:"+ds.getValue(Training.class).trainingURL, Toast.LENGTH_SHORT).show();
                                state = 1;
                                youTubePlayer.loadVideo(strURL);
                            }
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

                Toast.makeText(getApplicationContext(),"동영상 불러오기 성공", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(),"동영상 불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        };

        youtubeBtn = findViewById(R.id.youtubeBtn);
        youtubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubeView.initialize("anyKey",listener);
            }
        });

        back = findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void readTraining() {

    }
}
