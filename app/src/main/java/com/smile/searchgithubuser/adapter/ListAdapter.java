package com.smile.searchgithubuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.smile.searchgithubuser.R;
import com.smile.searchgithubuser.model.GithubUser;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hendrigunawan on 5/28/17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private Context mContext;
    private List<GithubUser> articleList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, description;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_text);
            date = (TextView) view.findViewById(R.id.date);
            image = (ImageView) view.findViewById(R.id.image);
            description = (TextView) view.findViewById(R.id.description);
        }
    }

    public ListAdapter(Context mContext, List<GithubUser> articleList) {
        this.mContext = mContext;
        this.articleList = articleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        GithubUser githubUser = articleList.get(position);
        holder.title.setText(githubUser.getLogin());
        holder.date.setText(githubUser.getNodeId());
        holder.description.setText(githubUser.getHtmlUrl());

        String image = githubUser.getAvatarUrl();
        Picasso.get().load(image).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        articleList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<GithubUser> list) {
        articleList.addAll(list);
        notifyDataSetChanged();
    }

    public void add(GithubUser article) {
        articleList.add(article);
        notifyDataSetChanged();
    }
}
