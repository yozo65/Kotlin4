package com.example.kotlin3laba

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*



class MainFragment : Fragment(R.layout.fragment_content) {
    public var listOfCartoons = mutableListOf<cartoonInfo>()
    private lateinit var databaseHelper: DataBaseHelper
    private var filter = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is SecondActivity) filter = " WHERE status = 1"
        else filter = ""
        databaseHelper = DataBaseHelper(requireContext())
    }


    @SuppressLint("Range")
    private suspend fun fillist(adapter: RecycleAdapter) {
        val cartoons = databaseHelper.getAllFilms(filter)
        for (cartoon in cartoons) {
            if (adapter != null) {
                listOfCartoons.add(cartoon)

                //adapter.addItem(data)
                adapter.notyy()
            }
            delay(1000);


        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycle = view.findViewById<RecyclerView>(R.id.recyclerModule)
        recycle.layoutManager = LinearLayoutManager(context)

        val container = requireActivity().findViewById<View>(R.id.fragment)

        val adapter = activity?.let {
            RecycleAdapter(
                listOfCartoons,
                it, container
            )
        }
        recycle.adapter = adapter


        if (adapter != null) {
                GlobalScope.launch(Dispatchers.IO) {
                if (adapter != null) {
                    fillist(adapter)
                };
            }
        }
    }
}
