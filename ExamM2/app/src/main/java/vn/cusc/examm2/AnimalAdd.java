package vn.cusc.examm2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AnimalAdd extends AppCompatActivity {

    EditText etName;
    TextView tvImage, tvVoice;
    Button btnVoice, btnAdd, btnList;
    ImageView img;

    Boolean permissionGrant = true;
    String duongdan, duongdan_voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_add);

        etName = (EditText)findViewById(R.id.etName);
        tvImage = (TextView) findViewById(R.id.tvImage);
        tvVoice = (TextView) findViewById(R.id.tvVoice);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnList = (Button) findViewById(R.id.btnList);
        btnVoice = (Button) findViewById(R.id.btnVoice);
        img = (ImageView)findViewById(R.id.img);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalAdd.this, AnimalList.class);
                startActivity(intent);
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Android 6 ho?c cao hon
                if (Build.VERSION.SDK_INT >= 23) {
                    // Kiem tra quyen
                    if (ContextCompat.checkSelfPermission(AnimalAdd.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(AnimalAdd.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(AnimalAdd.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(AnimalAdd.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(AnimalAdd.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(AnimalAdd.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(AnimalAdd.this, Manifest.permission.ACCESS_NETWORK_STATE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(AnimalAdd.this, Manifest.permission.INTERNET)) {
                        } else {
                            // Hien thi hop thoai cap quyen va cho nguoi dung xac nhan cho phep hay khong.
                            ActivityCompat.requestPermissions(AnimalAdd.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET}, 114);
                        }
                    } else {
                        permissionGrant = true;
                    }
                }
                if (permissionGrant)
                {
                    // goi chuc nang chon noi dung tu he thong
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    // xac dinh loai noi dung la tap tin file, image, video, audio
                    intent.setType("file/*");
                    // mo giao dien liet kenoi dung cua he thong
                    startActivityForResult(intent, 113);
                }
                else
                    Toast.makeText(AnimalAdd.this, "Không có quyền.", Toast.LENGTH_SHORT).show();

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etName.getText().toString().equals("")
                        &&  !tvImage.getText().toString().equals("")) {
                   new Upload().execute(duongdan, etName.getText().toString());
                }
                if(!etName.getText().toString().equals("")
                        &&  !tvVoice.getText().toString().equals(""))
                {
                    new Upload_Voice().execute(duongdan_voice, etName.getText().toString());
                }
            }
        });

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Android 6 ho?c cao hon
                if (Build.VERSION.SDK_INT >= 23) {
                    // Kiem tra quyen
                    if (ContextCompat.checkSelfPermission(AnimalAdd.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(AnimalAdd.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(AnimalAdd.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(AnimalAdd.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(AnimalAdd.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(AnimalAdd.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(AnimalAdd.this, Manifest.permission.ACCESS_NETWORK_STATE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(AnimalAdd.this, Manifest.permission.INTERNET)) {
                        } else {
                            // Hien thi hop thoai cap quyen va cho nguoi dung xac nhan cho phep hay khong.
                            ActivityCompat.requestPermissions(AnimalAdd.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET}, 114);
                        }
                    } else {
                        permissionGrant = true;
                    }
                }
                if (permissionGrant)
                {
                    // goi chuc nang chon noi dung tu he thong
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    // xac dinh loai noi dung la tap tin file, image, video, audio
                    intent.setType("file/*");
                    // mo giao dien liet kenoi dung cua he thong
                    startActivityForResult(intent, 115);
                }
                else
                    Toast.makeText(AnimalAdd.this, "Không có quyền.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 113: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGrant = true;

                } else {

                    permissionGrant = false;
                }
                return;
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 113) {
            Uri filePath = data.getData();
            // phan tien trinh dua noi dung file len server
            // file szie
            duongdan = layDuongDanThucTe(filePath, 1);
            tvImage.setText(duongdan);
            File f = new File(duongdan);
            Bitmap hinh = BitmapFactory.decodeFile(duongdan);

            img.setImageBitmap(hinh);
        }
		
		if (resultCode == RESULT_OK && requestCode == 115) {
            Uri filePath = data.getData();
            // phan tien trinh dua noi dung file len server
            // file szie
            duongdan_voice = layDuongDanThucTe(filePath, 2);
            tvVoice.setText(duongdan_voice);
        }
    }

    // lay duong dan thuc te cua doi tuong
    private String layDuongDanThucTe(Uri contentURI, int type) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = 0;
			if(type == 1)	
				idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			if(type == 2)	
				idx = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    class Upload extends AsyncTask<String, Integer, Integer>
    {
        String fileName;
        String fullname;
        @Override
        protected Integer doInBackground(String... params) {
            fileName = params[0];
            fullname = params[1];
		
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 512;
            File sourceFile = new File(fileName);

            try {
                // tham chieu toi file vua chon
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                // ket noki voi web server
                URL url = new URL(Setting.serverAddress+"upload.php");
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("fileDuocChon", fileName);
                conn.setRequestProperty("fullname", fullname);

                dos = new DataOutputStream(conn.getOutputStream());

                // cung cap thong tin ve hinh
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"fileDuocChon\";filename=\"" + fileName + "\"" + lineEnd);
				dos.writeBytes(lineEnd);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // doc noi dung vao bo nho tap buffer
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                publishProgress(bytesRead);
                while (bytesRead > 0) {
                    // upload noi dung vua doc duoc len server
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    // doc noi dung vao bo nho tap buffer
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    publishProgress(bytesRead);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                // kem du lieu text
                dos.writeBytes("Content-Disposition: form-data; name=\"fullname\""+lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(fullname+lineEnd);

                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                int serverResponseCode = conn.getResponseCode();
                int serverResponseMessage = conn.getResponseCode();

                return serverResponseMessage;
            } catch (Exception ex) {
                Log.d("Loi:", ex.toString());
                return 500;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(tvVoice.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Add successful!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AnimalAdd.this, AnimalList.class);
                startActivity(intent);
                finish();
            }
        }
    }

    class Upload_Voice extends AsyncTask<String, Integer, Integer>
    {
        String fileName;
        String fullname;
        @Override
        protected Integer doInBackground(String... params) {
            fileName = params[0];
            fullname = params[1];

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 512;
            File sourceFile = new File(fileName);

            try {
                // tham chieu toi file vua chon
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                // ket noki voi web server
                URL url = new URL(Setting.serverAddress+"upload_voice.php");
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("fileDuocChon", fileName);
                conn.setRequestProperty("fullname", fullname);

                dos = new DataOutputStream(conn.getOutputStream());

                // cung cap thong tin ve hinh
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"fileDuocChon\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // doc noi dung vao bo nho tap buffer
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                publishProgress(bytesRead);
                while (bytesRead > 0) {
                    // upload noi dung vua doc duoc len server
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    // doc noi dung vao bo nho tap buffer
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    publishProgress(bytesRead);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                // kem du lieu text
                dos.writeBytes("Content-Disposition: form-data; name=\"fullname\""+lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(fullname+lineEnd);

                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                int serverResponseCode = conn.getResponseCode();
                int serverResponseMessage = conn.getResponseCode();

                return serverResponseMessage;
            } catch (Exception ex) {
                Log.d("Loi:", ex.toString());
                return 500;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Toast.makeText(getApplicationContext(), "Add successful!",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AnimalAdd.this, AnimalList.class);
            startActivity(intent);
            finish();
        }
    }
}
