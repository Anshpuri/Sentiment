package com.example.android.project4.RecyclerViewAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.project4.R
import com.example.android.project4.models.ValueItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item_list.view.*

/**
 * Created by amandhapola on 10/07/17.
 */
public class NewsAdapter(var newsList : ArrayList<ValueItem>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    override fun onBindViewHolder(holder: NewsViewHolder?, position: Int) {
        var valueItem=newsList.get(position)
        var thumbnail=valueItem.image?.thumbnail
        var provider = valueItem.provider

        if(holder!=null){
            Picasso.with(holder.v.context).load(thumbnail?.contentUrl).into(holder.img_news)
            holder.tv_news_name.setText(valueItem.name)
            holder.tv_news_desc.setText(valueItem.description)
            holder.tv_oraganisation.setText(provider?.get(0)?.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.news_item_list,parent,false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        var img_news = itemView.img_news
        var tv_news_name = itemView.tv_news_name
        var tv_news_desc = itemView.tv_news_desc
        var tv_oraganisation= itemView.tv_organisation
        var v = itemView
    }
}