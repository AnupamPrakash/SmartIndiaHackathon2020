package com.example.newseye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class NewsDetails extends AppCompatActivity {

    TextView newsContent;
    ImageView newsDp;
    Toolbar toolbar;
    ImageView favorite,share,link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        final Article article = (Article) getIntent().getSerializableExtra("Article");
        newsContent = findViewById(R.id.textcard);
        newsDp = findViewById(R.id.toolbar_image);
        toolbar = findViewById(R.id.AppBar);
        favorite = findViewById(R.id.mark_favorite);
        share = findViewById(R.id.share);
        link = findViewById(R.id.openlink);
        Glide.with(newsDp.getContext()).load(article.getUrlToImage()).into(newsDp);
        newsContent.setText(article.getContent());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW",
                        Uri.parse(article.getUrl()));
                startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Checkout this latest news update :"+article.getUrl()+"/");
                startActivity(Intent.createChooser(intent,"Share Event via"));
            }
        });
    }
}
