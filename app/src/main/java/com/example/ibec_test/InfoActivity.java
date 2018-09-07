package com.example.ibec_test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class InfoActivity extends AppCompatActivity {

    TextView txtTitle, txtDescription, txtUrl, txtAuthor, txtPublishedAt;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        txtTitle = findViewById(R.id.textTitle);
        txtTitle.setText(getIntent().getExtras().getString("news_title"));

        txtDescription = findViewById(R.id.textDescription);
        if (getIntent().getExtras().getString("news_description") == null){
            txtDescription.setText("No description");
        } else {
            txtDescription.setText(getIntent().getExtras().getString("news_description"));
        }

        txtUrl = findViewById(R.id.textUrl);
        txtUrl.setText(getIntent().getExtras().getString("news_url"));

        txtAuthor = findViewById(R.id.textAuthor);
        if (getIntent().getExtras().getString("news_author") == null){
            txtAuthor.setText("No author");
        } else {
            txtAuthor.setText(getIntent().getExtras().getString("news_author"));
        }

        txtPublishedAt = findViewById(R.id.textPublishedAt);
        txtPublishedAt.setText(getIntent().getExtras().getString("news_publishedAt"));

        imageView = findViewById(R.id.bigImageView);
        if (getIntent().getExtras().getString("news_urlToImage") == null) {
            imageView.setImageResource(R.drawable.noimage);
        } else {
            new DownloadImageTask(imageView)
                    .execute(getIntent().getExtras().getString("news_urlToImage"));
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
