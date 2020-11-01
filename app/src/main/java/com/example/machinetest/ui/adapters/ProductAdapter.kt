package com.example.machinetest.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.machinetest.R
import com.example.machinetest.ui.DetaildPage
import com.example.machinetest.ui.models.Person
import kotlinx.android.synthetic.main.product_list_single_item.view.*

class ProductAdapter(
    val list: List<Person>,
    val mContext: Context?
    ) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ProductAdapter.NotiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): NotiViewHolder {
        return NotiViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.product_list_single_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NotiViewHolder, p1: Int) {
        val listItem: Person = list.get(p1)
        holder.pName.text = listItem.name
        var requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)

       Glide.with(mContext!!).load(listItem.profile_image).apply(requestOptions).into(holder.pImage)
        holder.pImage.setOnClickListener {
            var intent = Intent(mContext, DetaildPage::class.java)
            intent.putExtra("id",listItem.id);
            intent.putExtra("name",listItem.name);
            intent.putExtra("image",listItem.profile_image);
            intent.putExtra("uname",listItem.username);
            intent.putExtra("email",listItem.email);
            intent.putExtra("website",listItem.website);
            mContext.startActivity(intent)
        }
    }

    inner class NotiViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val pName = itemView.pName
        val pImage = itemView.pImage
        val cName = itemView.cName
    }
}
