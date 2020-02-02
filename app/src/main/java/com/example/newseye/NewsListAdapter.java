package com.example.newseye;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.DataViewHolder> {
    private Context context;
    private List<Article> articles;
    private int count;
    public NewsListAdapter(Context context, List<Article> articles, int count) {
        this.context = context;
        this.articles = articles;
        this.count = count;
//        Toast.makeText(context, ""+articles.toString(), Toast.LENGTH_SHORT).show();
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
//        Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
        final Article article = articles.get(position);
//        Toast.makeText(context, ""+article.getTitle(), Toast.LENGTH_SHORT).show();
        holder.textView.setText(article.getTitle());
//        Toast.makeText(context,"http://springspree.in/media/"+dataCard.getFields().getLogo() , Toast.LENGTH_SHORT).show();
        Glide.with(holder.imgicon.getContext()).load(article.getUrlToImage()).into(holder.imgicon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Clicked: "+article.getTitle(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, dataCard.getFields().getName()+" is clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,NewsDetails.class).putExtra("Article",article);
                intent.putExtra("Count",count);
                context.startActivity(intent);
//                context.startActivity(new Intent(context,NewsDetails.class));
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
