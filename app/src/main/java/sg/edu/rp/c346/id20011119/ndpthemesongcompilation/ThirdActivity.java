package sg.edu.rp.c346.id20011119.ndpthemesongcompilation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText etTitle, etSinger,etYear,etID;
    RadioGroup radgrp;
    RadioButton rb1,rb2,rb3,rb4,rb5;
    Button btnUpdate,btnDelete,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        etID = findViewById(R.id.etID);
        radgrp = findViewById(R.id.radGrp);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        final Song currentSong = (Song) i.getSerializableExtra("song");
        etID.setText(currentSong.get_id() + "");
        etTitle.setText(currentSong.getTitle());
        etSinger.setText(currentSong.getSingers());
        etYear.setText(currentSong.getYear());
        switch(currentSong.getStars()){
            case 5:
                rb5.setChecked(true);
            case 4:
                rb4.setChecked(true);
            case 3:
                rb3.setChecked(true);
            case 2:
                rb2.setChecked(true);
            case 1:
                rb1.setChecked(true);

        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentSong.setTitle((etTitle.getText().toString().trim()));
                currentSong.setSingers(etSinger.getText().toString().trim());
                int year = Integer.valueOf(etYear.getText().toString().trim());
                currentSong.setYear(year);

                int selectedRB = radgrp.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(selectedRB);
                currentSong.setStars(Integer.parseInt(rb.getText().toString()));
                int result = dbh.updateSong(currentSong);
                if(result < 0){
                    Toast.makeText(ThirdActivity.this, "Song updated",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                }else{
                    Toast.makeText(ThirdActivity.this,"Update Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                int result = dbh.deleteSong(currentSong.get_id());
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}