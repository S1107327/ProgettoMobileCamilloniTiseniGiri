package com.example.progettomobilecamillonitisenigiri

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Corso.CorsoActivity
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.net.URL

class MyAdapter (val data: List<Corso>, val monPopularAdapter: OnMyAdapterListener): RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder>() {
    class MyAdapterViewHolder(val box: View,val onMyAdapterListener: OnMyAdapterListener) : RecyclerView.ViewHolder(box) , View.OnClickListener {
        init{
            box.setOnClickListener(this)

        }
        val cardpopular = box.findViewById<CardView>(R.id.cardpopular)
        override fun onClick(v: View?) {

            onMyAdapterListener.onCorsoClick(adapterPosition,v)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyAdapterViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_corso,
            parent, false
        )
        return MyAdapterViewHolder(layout, monPopularAdapter)
    }

    override fun onBindViewHolder(holder: MyAdapterViewHolder, position: Int) {
        holder.cardpopular.findViewById<TextView>(R.id.corsoId).text =
            data.get(position).id
        holder.cardpopular.findViewById<TextView>(R.id.textView3).text =
            data.get(position).titolo
        try {
            System.out.println("immagine:"+(data.get(position).immagine) )
            Picasso.get().load(data.get(position).immagine).into(holder.cardpopular.findViewById<ImageView>(R.id.imageView))
        }catch (e:Exception){
            Picasso.get().load("https://png.pngtree.com/png-vector/20191120/ourmid/pngtree-training-course-online-computer-chat-flat-color-icon-vector-png-image_2007114.jpg").into(holder.cardpopular.findViewById<ImageView>(R.id.imageView))
        }


    }

    override fun getItemCount(): Int = data.size

    interface OnMyAdapterListener{
        fun onCorsoClick(position: Int, v: View?)
    }
}