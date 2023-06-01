package com.example.kotlin3laba

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class RecycleAdapter(private val names: MutableList<cartoonInfo>,private val linkActivity: FragmentActivity, private val container:View): RecyclerView.Adapter<RecycleAdapter.MyViewHolder>(){

    val handler = Handler(Looper.getMainLooper())

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)
        val imageview:ImageView=itemView.findViewById(R.id.imageView)
        val card:CardView=itemView.findViewById(R.id.cardView)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(com.example.kotlin3laba.R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }


    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.largeTextView.text = names[position].name
        holder.smallTextView.text = names[position].season.toString()
        Picasso.get()
            .load(names[position].image)
            .into(holder.imageview)

        val fragment = cartooninfoFragment()
        holder.card.setOnClickListener {

            val bitmap = getBitmapFromImageView(holder.imageview)
            val header = names[position].name
            val description = names[position].description
            val status = names[position].status.toString()
            val id = names[position].id
            val args = Bundle().apply {
                putString("name", header)
                putParcelable("bitmap", bitmap)
                putString("description", description)
                putString("status",status)
                putInt("id",id)
            }
            fragment.arguments = args

            linkActivity.supportFragmentManager.beginTransaction()
                .replace(container.id, fragment)
                .commit()
        }

    }


    private fun getBitmapFromImageView(view: View): Bitmap? {

        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val drawable = view.background
        drawable?.draw(canvas)
        view.draw(canvas)
        return bitmap
    }

    fun notyy() {
        handler.post{
            notifyDataSetChanged()
        }
    }



}
