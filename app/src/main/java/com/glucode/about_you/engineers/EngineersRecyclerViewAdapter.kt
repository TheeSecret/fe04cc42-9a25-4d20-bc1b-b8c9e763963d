package com.glucode.about_you.engineers

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glucode.about_you.R
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


            if (engineer.defaultImageName != null) {
                binding.profileImage.setImageBitmap(engineer.defaultImageName)
                // Clear any tint if the image is not null
                binding.profileImage.clearColorFilter()
            } else {
                // If the image is null, set the tint to black
                binding.profileImage.setImageResource(R.drawable.ic_person) // Assuming you have a placeholder image
                binding.profileImage.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
            }


            binding.root.setOnClickListener {
                onClick(engineer)
            }

            //TODO - set profile picture
       //    statusIcon.setDrawable(item.icon)
        }
    }
}