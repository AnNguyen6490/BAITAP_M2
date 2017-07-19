package vn.cusc.examm2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle;
    Button btnList;
    Button btnAdd;
    CheckBox chkPhatAmThanh;

    SharedPreferences pre;
    SharedPreferences.Editor edt;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font.ttf"));

        pre = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        btnList = (Button)findViewById(R.id.btnList);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        chkPhatAmThanh = (CheckBox)findViewById(R.id.chkPhatAmThanh);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.animalsoundsong);

        if(pre.getString("PhatAmThanh","").equals("")) {
            chkPhatAmThanh.setChecked(false);
        }
        else
        {
            chkPhatAmThanh.setChecked(true);
            mediaPlayer.start();
        }

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnimalList.class);
                startActivity(intent);
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnimalAdd.class);
                startActivity(intent);
                finish();
            }
        });

        chkPhatAmThanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edt = pre.edit();
                    edt.putString("PhatAmThanh", "true");
                    edt.commit();

                    mediaPlayer.start();
                }
                else
                {
                    edt = pre.edit();
                    edt.putString("PhatAmThanh", "");
                    edt.commit();

                    mediaPlayer.stop();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
}