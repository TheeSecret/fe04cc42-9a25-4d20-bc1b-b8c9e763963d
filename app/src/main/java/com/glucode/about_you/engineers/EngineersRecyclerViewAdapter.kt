package com.glucode.about_you.engineers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glucode.about_you.databinding.ItemEngineerBinding
import com.glucode.about_you.engineers.models.Engineer

class EngineersRecyclerViewAdapter(
    private var engineers: List<Engineer>,
    private val onClick: (Engineer) -> Unit
) : RecyclerView.Adapter<EngineersRecyclerViewAdapter.EngineerViewHolder>() {

    override fun getItemCount() = engineers.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngineerViewHolder {
        return EngineerViewHolder(ItemEngineerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EngineerViewHolder, position: Int) {

        // here i will sort all engineers by the years in an ascending order
        val sortedEngineers= engineers.sortedBy { it.quickStats.years }

        holder.bind(sortedEngineers[position], onClick)

     //   holder.bind(engineers[position], onClick)

    }

    inner class EngineerViewHolder(private val binding: ItemEngineerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(engineer: Engineer, onClick: (Engineer) -> Unit) {

            binding.name.text = engineer.name
            binding.role.text = engineer.role

            binding.root.setOnClickListener {
                onClick(engineer)
            }
            if ( engineer.defaultImageName.isNotEmpty()) {

                // image name will hold image uri from the phone Gallery
              //  binding.profileImage.setImageDrawable() = engineer.defaultImageName
                //   profileView.image = engineer.defaultImageName
            }


            //TODO - set profile picture
       //    statusIcon.setDrawable(item.icon)
        }
    }
}