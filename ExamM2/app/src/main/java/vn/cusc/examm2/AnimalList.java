package vn.cusc.examm2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class AnimalList extends AppCompatActivity{

    String url = Setting.serverAddress + "list.php";
    ArrayList<Animal> list;
    AnimalAdapter adp;
    GridView grd;

    Boolean permissionGrant = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_list);

        list = new ArrayList<>();
        grd = (GridView) findViewById(R.id.grdList);

        grd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AnimalList.this, AnimalView.class);
                intent.putExtra("id", ((Animal)adp.getItem(position)).getId());
                intent.putExtra("name", ((Animal)adp.getItem(position)).getName());
                intent.putExtra("image", ((Animal)adp.getItem(position)).getImage());
                intent.putExtra("voice", ((Animal)adp.getItem(position)).getVoice());

                startActivity(intent);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final View itemView = info.targetView;
        if(item.getItemId() == R.id.mnuAdd)
        {
            Intent intent = new Intent(AnimalList.this, AnimalAdd.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Android 6 ho?c cao hon
        if (Build.VERSION.SDK_INT >= 23) {
            // Kiem tra quyen
            if (ContextCompat.checkSelfPermission(AnimalList.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(AnimalList.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(AnimalList.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(AnimalList.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(AnimalList.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        || ActivityCompat.shouldShowRequestPermissionRationale(AnimalList.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || ActivityCompat.shouldShowRequestPermissionRationale(AnimalList.this, Manifest.permission.ACCESS_NETWORK_STATE)
                        || ActivityCompat.shouldShowRequestPermissionRationale(AnimalList.this, Manifest.permission.INTERNET)) {
                } else {
                    // Hien thi hop thoai cap quyen va cho nguoi dung xac nhan cho phep hay khong.
                    ActivityCompat.requestPermissions(AnimalList.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET}, 113);
                }
            } else {
                permissionGrant = true;
            }
        }

        if (permissionGrant)
            new Download().execute(url);
        else
            Toast.makeText(AnimalList.this, "Không có quyền.", Toast.LENGTH_SHORT).show();
    }

    class Download extends AsyncTask<String, Integer, String>
    {
        @Override
        protected String doInBackground(String... params) {
            try {
                String b;
                URL u = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)u.openConnection();
                InputStream i = conn.getInputStream();

                StringBuilder temp = new StringBuilder();

                String line = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(i));
                while ((line = br.readLine()) != null)
                {
                    temp.append(line);
                    temp.append("\n");
                }
                br.close();

                return temp.toString();
            }
            catch (Exception ex) {
                Log.d("Loi: ", ex.toString());
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try
            {
                list = new ArrayList<>();
                JSONArray arr = new JSONArray(s);
                for (int i = 0; i   <arr.length(); i++)
                {
                    Animal a = new Animal();
                    JSONObject json = arr.optJSONObject(i);
                    a.setId(Integer.parseInt(arr.getJSONObject(i).getString("id")));
                    a.setName(json.getString("name"));
                    a.setImage(json.getString("image"));
                    a.setVoice(json.getString("voice"));

                    list.add(a);
                }

                adp = new AnimalAdapter(list, AnimalList.this);
                grd.setAdapter(adp);
                registerForContextMenu(grd);
            }
            catch (Exception ex)
            {
                Log.d("Loi: ", ex.toString());
            }
        }
    }
}


