package com.example.proyectoaplicacion.ui.recyclerview

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoaplicacion.R
import com.example.proyectoaplicacion.domain.model.Review

class POJOAdapter(
    private val onDeleteClick: (Review) -> Unit,
    private val onEditClick: (Review) -> Unit
) : RecyclerView.Adapter<POJOAdapter.POJOViewHolder>() {

    private var reviews: List<Review> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): POJOViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return POJOViewHolder(view)
    }

    override fun onBindViewHolder(holder: POJOViewHolder, position: Int) {
        val pojo = reviews[position]
        holder.bind(pojo)
    }

    override fun getItemCount(): Int = reviews.size

    fun updatePOJOs(newReviews: List<Review>) {
        reviews = newReviews
        notifyDataSetChanged()
    }

    inner class POJOViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        private val editButton: Button = itemView.findViewById(R.id.editButton)

        fun bind(review: Review) {
            titleTextView.text = review.title
            descriptionTextView.text = review.description

            if (review.image != null) {
                val imageName = review.image.replace(".jpg", "") // Elimina la extensión .jpg
                val imageResId = itemView.context.resources.getIdentifier(imageName, "drawable", itemView.context.packageName)
                if (imageResId != 0) {
                    imageView.setImageResource(imageResId)
                } else {
                    imageView.setImageResource(R.drawable.imagen_placeholder)
                }
            } else {
                imageView.setImageResource(R.drawable.imagen_placeholder)
            }

            deleteButton.setOnClickListener {
                onDeleteClick(review)
            }

            editButton.setOnClickListener {
                onEditClick(review)
            }
        }
    }
}