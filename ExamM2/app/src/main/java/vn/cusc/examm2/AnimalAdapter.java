package vn.cusc.examm2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Admin on 03/07/2017.
 */

public class AnimalAdapter extends BaseAdapter {

    ArrayList<Animal> lst;
    Context c;

    public AnimalAdapter(ArrayList<Animal> lst, Context context) {
        this.lst = lst;
        this.c = context;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lst.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Animal_Cell cell;
        if(convertView == null)
        {
            cell = new Animal_Cell();
            LayoutInflater inflater = ((Activity)c).getLayoutInflater();
            convertView = inflater.inflate(R.layout.animal_cell, parent, false);
            cell.tvID = (TextView)convertView.findViewById(R.id.tvId);
            cell.tvName = (TextView)convertView.findViewById(R.id.tvName);
            cell.img = (ImageView)convertView.findViewById(R.id.img);

            convertView.setTag(cell);
        }
        else
        {
            cell = (Animal_Cell)convertView.getTag();
        }

        cell.tvID.setText(lst.get(position).getId()+"");
        cell.tvName.setText(lst.get(position).getName());

        String url = Setting.serverAddress + "image/" + lst.get(position).getImage();

        new DownloadImage(cell.img).execute(url);

        return convertView;
    }
}

class Animal_Cell
{
    TextView tvID;
    TextView tvName;
    ImageView img;
}

class DownloadImage extends AsyncTask<String, Integer, Bitmap>
{
    ImageView image;

    public DownloadImage(ImageView v) {
        this.image = v;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            Bitmap b;
            URL u = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection)u.openConnection();
            InputStream i = conn.getInputStream();
            b = BitmapFactory.decodeStream(i);
            return b;
        }
        catch (Exception ex) {
            Log.d("Loi hinh: ", ex.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        image.setImageBitmap(bitmap);
    }
}
