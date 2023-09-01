package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.room.AsteroidModel

class AsteroidAdapter(val onItemClick: (AsteroidModel) -> Unit) :
    RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>() {

    private var listAsteroid: List<AsteroidModel> = mutableListOf()

    fun setData(list: List<AsteroidModel>) {
        listAsteroid = list
        notifyDataSetChanged()
    }

    inner class AsteroidViewHolder(val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AsteroidModel) {
            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
            binding.asteroid = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_asteroid,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listAsteroid.size
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(listAsteroid[position])
    }
}