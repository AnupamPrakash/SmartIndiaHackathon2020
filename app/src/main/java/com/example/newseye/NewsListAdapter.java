package com.example.newseye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.DataViewHolder> {
    private Context context;
    private News news;
    private List<Article> articles;
    public NewsListAdapter(Context context, News news) {
        this.context = context;
        this.news = news;
        articles = news.getArticles();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.newscard,parent,false);
        return  new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        final Article article = articles.get(position);
        holder.textView.setText(article.getTitle());
//        Toast.makeText(context,"http://springspree.in/media/"+dataCard.getFields().getLogo() , Toast.LENGTH_SHORT).show();
        Glide.with(holder.imgicon.getContext()).load(article.getUrlToImage()).into(holder.imgicon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked: "+article.getTitle(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, dataCard.getFields().getName()+" is clicked", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context,DetailsActivity.class).putExtra("Data",dataCard.getFields());
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        ImageView imgicon;
        TextView textView;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            imgicon= itemView.findViewById(R.id.newsDp);
            textView=itemView.findViewById(R.id.newstitle);
        }
    }
}
