package com.example.ibec_test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private List<News> newsList = new ArrayList();
    private Context context;

    private ArrayList<GetNewsResponse> mModel;


    private static int currentPosition = 0;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setNews(List<News> newsList) {
        if (this.newsList == null) {
            this.newsList = newsList;
        } else {
            this.newsList.addAll(newsList);
        }
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription;
        ImageView imageView;

        NewsViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.txtTitle);
            textViewDescription = itemView.findViewById(R.id.txtDescription);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {      //NewsAdapter
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout_news, parent, false);
        return  new NewsViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
        final News news = newsList.get(position);
        holder.textViewTitle.setText(news.getTitle());

        if (news.getDescription() == null){
            holder.textViewDescription.setText("No description");
        } else {
            holder.textViewDescription.setText(news.getDescription());
        }

        Glide.with(context)
                .load(news.getUrlToImage())
                .apply(RequestOptions.placeholderOf(R.drawable.noimage).error(R.drawable.noimage))
                .into(holder.imageView);

        //if the position is equals to the item position which is to be expanded
        if (currentPosition == position) {
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = position;

                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("news_title", news.getTitle());
                intent.putExtra("news_description", news.getDescription());
                intent.putExtra("news_url", news.getUrl());
                intent.putExtra("news_author", news.getAuthor());
                intent.putExtra("news_publishedAt", news.getPublishedAt());
                intent.putExtra("news_urlToImage", news.getUrlToImage());
                context.startActivity(intent);

                //reloding the list
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (newsList == null) ? 0 : newsList.size();
    }
}
