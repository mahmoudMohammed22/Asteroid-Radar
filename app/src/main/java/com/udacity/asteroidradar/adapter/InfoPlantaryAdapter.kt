package com.udacity.asteroidradar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ListItemBinding
import com.udacity.asteroidradar.roomDatabase.AsteroidEntitry

class InfoPlantaryAdapter(val onClickLisner :OnClickLisner) :ListAdapter<AsteroidEntitry,InfoPlantaryAdapter.InfoViewHolder>(DiffCallBack) {
    
     class InfoViewHolder(val binding : ListItemBinding):RecyclerView.ViewHolder(binding.root) {

         //to access in dara
         fun bind(Asteroid :AsteroidEntitry){

             binding.asteroid = Asteroid
             binding.executePendingBindings()

         }

    }

    //to inflate with ListItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InfoViewHolder(ListItemBinding.inflate(layoutInflater,parent,false))

    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val infoItem = getItem(position)
        holder.bind(infoItem)
        holder.itemView.setOnClickListener {
            onClickLisner.onClick(infoItem)
        }
    }

    // to compare Data
    object DiffCallBack : DiffUtil.ItemCallback<AsteroidEntitry>(){
        override fun areItemsTheSame(oldItem: AsteroidEntitry, newItem: AsteroidEntitry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AsteroidEntitry, newItem: AsteroidEntitry): Boolean {
            return oldItem == newItem
        }

    }

    // to add click in item
    class OnClickLisner(val clickLisner : (asteroidEntinty:AsteroidEntitry)->Unit){
        fun onClick(astroid:AsteroidEntitry) = clickLisner(astroid)
    }
}
