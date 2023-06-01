package com.example.kotlin3laba

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class cartooninfoFragment: Fragment(R.layout.cartoon_layout) {
    private lateinit var databaseHelper: DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DataBaseHelper(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var text = requireActivity().findViewById<TextView>(R.id.name)
        text.text = arguments?.getString("name")
        var image = requireActivity().findViewById<ImageView>(R.id.imageView2)
        var bitmap = arguments?.getParcelable<Bitmap>("bitmap")
//        bitmap= bitmap?.let { resizeBitmap(it,1600,1600) }
        image.setImageBitmap(bitmap)
        var descriprion = requireActivity().findViewById<TextView>(R.id.descriprion)
        descriprion.text = arguments?.getString("description")
        var favorite = requireActivity().findViewById<ImageView>(R.id.imageView4)

        if (putArgs()[1] == "1") {
            favorite.setImageResource(R.drawable.star1)
        } else {
            favorite.setImageResource(R.drawable.star)
        }


        favorite.setOnClickListener {
            val currentStatus = putArgs()[1].toInt()
            val newStatus = if (currentStatus == 1) 0 else 1
            val itemId = putArgs()[2].toInt()
            if (newStatus == 1) {
                favorite.setImageResource(R.drawable.star1)

                databaseHelper.updateCartoonStatus(itemId, 1)
            } else {
                favorite.setImageResource(R.drawable.star)
                databaseHelper.updateCartoonStatus(itemId, 0)
            }
        }


    }


        fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
            bitmap.recycle()
            return resizedBitmap
        }

    fun putArgs():Array<String>   {
    val bundle = arguments
    var args = arrayOf("","","")
    if(bundle!=null) {
        args[0] = bundle?.getString("name").toString()
        args[1]= bundle?.getString("status").toString()
        args[2]=bundle?.getInt("id").toString()
        return args
    }
    return arrayOf("","","")

    }
}
