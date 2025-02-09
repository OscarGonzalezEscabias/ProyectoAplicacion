package com.example.proyectoaplicacion.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoaplicacion.R
import com.example.proyectoaplicacion.domain.model.POJO

class POJOAdapter(
    private val onDeleteClick: (POJO) -> Unit,
    private val onEditClick: (POJO) -> Unit
) : RecyclerView.Adapter<POJOAdapter.ItemViewHolder>() {

    private val pojos = mutableListOf<POJO>()

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val editButton: Button = itemView.findViewById(R.id.editButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val pojo = pojos[position]
        holder.imageView.setImageResource(pojo.imageResId)
        holder.titleTextView.text = pojo.title
        holder.descriptionTextView.text = pojo.description

        holder.deleteButton.setOnClickListener { onDeleteClick(pojo) }
        holder.editButton.setOnClickListener { onEditClick(pojo) }
    }

    override fun getItemCount(): Int = pojos.size

    fun updatePOJOs(newPOJOs: List<POJO>) {
        pojos.clear()
        pojos.addAll(newPOJOs)
        notifyDataSetChanged()
    }
}