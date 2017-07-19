package vn.cusc.examm2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AnimalView extends AppCompatActivity {

    TextView tvTen;
    ImageView img;
    Button btnVoice;
    Intent intent;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_view);

        tvTen = (TextView) findViewById(R.id.tvName);
        img = (ImageView) findViewById(R.id.img);
        btnVoice = (Button) findViewById(R.id.btnVoice);

        intent = getIntent();
        String name = intent.getStringExtra("name");
        String image = intent.getStringExtra("image");
        final String voice = intent.getStringExtra("voice");

        tvTen.setText(name);
        String url = Setting.serverAddress + "image/" + image;

        new DownloadImage().execute(url);

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Setting.serverAddress +"sound/"+ voice;
                Uri u = Uri.parse(url);
                if (mediaPlayer != null && mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(AnimalView.this, u);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    class DownloadImage extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                Bitmap b;
                URL u = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                InputStream i = conn.getInputStream();
                b = BitmapFactory.decodeStream(i);
                return b;
            } catch (Exception ex) {
                Log.d("Loi hinh: ", ex.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            img.setImageBitmap(bitmap);
        }
    }
}
