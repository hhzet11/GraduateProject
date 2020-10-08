package adroidtown.org.graduateproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExerciseList extends AppCompatActivity {

    //다이어트 리스트
    static final String[] diet_list_1 = {"사이드런지","레그레이즈","더블크런치","와이드스쿼트","팔벌려뛰기"};
    static final String[] diet_list_2 = {"마운틴클라이머","제자리니킥","플랭크","리어레그레이즈","힙브릿지"};
    static final String[] diet_list_3 = {"스탠딩원레그레이즈","점프스쿼트","런지","카프레이즈","버피테스트"};
    //벌크업 리스트
    static final String[] bulk_list_1 = {"스쿼트","카프레이즈","버피테스트","마운틴클라이머","제자리니킥"};
    static final String[] bulk_list_2 = {"크랩스쿼트","레그레이즈","더블크런치","힙브릿지","플래크"};
    static final String[] bulk_list_3 = {"와이드스쿼트","팔벌려뛰기","카프레이즈","런지","버피테스트"};
    //재활 리스트
    static final String[] re_list_1 = {"사이드런지","레그레이즈","더블크런치","와이드스쿼트","팔벌려뛰기"};
    static final String[] re_list_2 = {"마운틴클라이머","제자리니킥","플랭크","리어레그레이즈","힙브릿지"};
    static final String[] re_list_3 = {"스탠딩원레그레이즈","카프레이즈","점프스쿼트","플랭크","런지"};

    private DatabaseReference mDatabase;
    Button start, cancel;
    String strText, id, type;
    ArrayAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent passed_intent = getIntent();
        Bundle bundle = passed_intent.getExtras();
        id = bundle.getString("id");

        //id로 type 불러오기
        listView = (ListView) findViewById(R.id.exerciseListView);
        readUser(id);

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExerciseResult.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"운동 완료 가정", Toast.LENGTH_SHORT).show();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"이전으로 돌아가기", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readUser(final String ID){
        mDatabase.child("users").child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(User.class) != null){
                    User post = dataSnapshot.getValue(User.class);
                    if(ID.equals(post.userId)){
                        type = post.userType;
                        Toast.makeText(getApplicationContext(), "<운동 유형> " + type, Toast.LENGTH_SHORT).show();
                        if(type.equals("다이어트")) {
                            int ran = (int) (Math.random() * 3 + 1);
                            if (ran == 1)
                                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, diet_list_1);
                            else if (ran == 2)
                                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, diet_list_2);
                            else if (ran == 3)
                                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, diet_list_3);
                            else Toast.makeText(getApplicationContext(), "random 오류", Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(), "운동유형 - 불러오기 성공", Toast.LENGTH_SHORT).show();
                        }
                        else if(type.equals("벌크업")){
                            int ran = (int) (Math.random() * 3 + 1);
                            if (ran == 1)
                                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bulk_list_1);
                            else if (ran == 2)
                                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bulk_list_2);
                            else if (ran == 3)
                                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bulk_list_3);
                            else Toast.makeText(getApplicationContext(), "random 오류", Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(), "운동유형 - 불러오기 성공", Toast.LENGTH_SHORT).show();
                        }
                        else if(type.equals("재활")){
                            int ran = (int) (Math.random() * 3 + 1);
                            if (ran == 1)
                                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, re_list_1);
                            else if (ran == 2)
                                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, re_list_2);
                            else if (ran == 3)
                                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, re_list_3);
                            else Toast.makeText(getApplicationContext(), "random 오류", Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(), "운동유형 - 불러오기 성공", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "운동 유형불러오기 오류", Toast.LENGTH_SHORT).show();
                        }

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                strText = (String) parent.getItemAtPosition(position);
                                Intent intent = new Intent(getApplicationContext(), ExerciseVideo.class);
                                intent.putExtra("name", strText);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),strText + " 클릭 됨", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "<운동 유형 불러오기 실패>", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "<데이터베이스 오류>", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
