package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.room.AsteroidModel

class AsteroidAdapter(val onItemClick: (AsteroidModel) -> Unit) :
    ListAdapter<AsteroidModel,AsteroidAdapter.AsteroidViewHolder>(SelectCityDiffUtil()) {

    inner class AsteroidViewHolder(private val binding: ItemAsteroidBinding) :
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

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SelectCityDiffUtil : DiffUtil.ItemCallback<AsteroidModel>() {
        override fun areItemsTheSame(oldItem: AsteroidModel, newItem: AsteroidModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AsteroidModel, newItem: AsteroidModel): Boolean {
            return oldItem == newItem
        }
    }
}