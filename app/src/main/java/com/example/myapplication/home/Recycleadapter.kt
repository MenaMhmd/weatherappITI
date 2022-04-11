package com.example.myapplication.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class recycleadapter: RecyclerView.Adapter<recycleadapter.ViewHolder>() {
private var names= arrayOf("Satureday","Sunday","Monday","Tuesday","Wensday","Thursday","Friday")
   inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
   var mintem:TextView
   var maxtem:TextView
   var name:TextView




   init {
       mintem=itemView.findViewById(R.id.text1)
       maxtem=itemView.findViewById(R.id.text2)
       name=itemView.findViewById(R.id.textn)
   }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.cardview,parent,false)
        return ViewHolder (v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.mintem.text=names[position]
    }

    override fun getItemCount(): Int {
      return names.size;
    }

}