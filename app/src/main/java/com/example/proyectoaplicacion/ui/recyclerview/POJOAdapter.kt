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
import com.example.proyectoaplicacion.domain.model.POJO

class POJOAdapter(
    private val onDeleteClick: (POJO) -> Unit,
    private val onEditClick: (POJO) -> Unit
) : RecyclerView.Adapter<POJOAdapter.POJOViewHolder>() {

    private var pojos: List<POJO> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): POJOViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return POJOViewHolder(view)
    }

    override fun onBindViewHolder(holder: POJOViewHolder, position: Int) {
        val pojo = pojos[position]
        holder.bind(pojo)
    }

    override fun getItemCount(): Int = pojos.size

    fun updatePOJOs(newPOJOs: List<POJO>) {
        pojos = newPOJOs
        notifyDataSetChanged()
    }

    inner class POJOViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        private val editButton: Button = itemView.findViewById(R.id.editButton)

        fun bind(pojo: POJO) {
            titleTextView.text = pojo.title
            descriptionTextView.text = pojo.description

            if (pojo.imageBase64 != null) {
                val imageBytes = Base64.decode(pojo.imageBase64, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                imageView.setImageBitmap(bitmap)
            } else {
                imageView.setImageResource(pojo.imageResId)
            }

            deleteButton.setOnClickListener {
                onDeleteClick(pojo)
            }

            editButton.setOnClickListener {
                onEditClick(pojo)
            }
        }
    }
}