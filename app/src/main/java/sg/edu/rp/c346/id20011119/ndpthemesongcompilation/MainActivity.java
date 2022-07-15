package sg.edu.rp.c346.id20011119.ndpthemesongcompilation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle, tvSinger, tvYear, tvStars;
    EditText etTitle, etSinger, etYear;
    RadioGroup radioGroup;
    Button btnInsert, btnLv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle().toString() + " ~ " + "Insert Song");


        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        radioGroup = findViewById(R.id.rg);
        btnInsert = findViewById(R.id.btnInsert);
        btnLv = findViewById(R.id.btnLv);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                String title = etTitle.getText().toString().trim();
                String singer = etSinger.getText().toString().trim();

                String year_str = etYear.getText().toString().trim();
                int year = Integer.valueOf(year_str);
                int stars = getStars();
                dbh.insertSongs(title, singer, year_str, stars);
                dbh.close();
                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                etTitle.setText("");
                etSinger.setText("");
                etYear.setText("");
                return;

            }

        });

        btnLv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });


    }

    private int getStars() {
        int stars = 1;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb1:
                stars = 1;
                break;
            case R.id.rb2:
                stars = 2;
                break;
            case R.id.rb3:
                stars = 3;
                break;
            case R.id.rb4:
                stars = 4;
                break;
            case R.id.rb5:
                stars = 5;


        }

        return stars;
    }
}