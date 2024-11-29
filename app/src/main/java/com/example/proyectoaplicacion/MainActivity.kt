package com.example.proyectoaplicacion

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoaplicacion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var POJOAdapter: POJOAdapter
    private val POJOS = mutableListOf<POJO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        POJOS.addAll(
            listOf(
                POJO(R.drawable.imagen1, "Xpecado", "Una franquicia de hamburgueserias al estilo smash burguer popular en Estados Unidos."),
                POJO(R.drawable.imagen2, "Museo Íbero", "Un museo dedicado a la cultura íbera, con exhibiciones fascinantes sobre su historia y arte."),
                POJO(R.drawable.imagen3, "Baños Árabes", "Un espacio histórico que combina arquitectura árabe con termas tradicionales."),
                POJO(R.drawable.imagen4, "Catedral de la Asunción", "Un majestuoso ejemplo de arquitectura renacentista en el corazón de la ciudad."),
                POJO(R.drawable.imagen5, "Mirador de la Cruz", "Un lugar emblemático para disfrutar de vistas panorámicas impresionantes."),
                POJO(R.drawable.imagen6, "Castillo de Santa Catalina", "Una fortaleza medieval con vistas espectaculares y una rica historia."),
                POJO(R.drawable.imagen7, "Basílica de San Ildefonso", "Un templo impresionante que destaca por su diseño barroco y su importancia religiosa."),
                POJO(R.drawable.imagen8, "Parque Natural de las Sierras de Cazorla, Segura y las Villas", "El mayor espacio natural protegido de España, ideal para los amantes de la naturaleza."),
                POJO(R.drawable.imagen9, "Ajo Mecánico", "Un restaurante con terraza con gran variedad de carta y muy buen servicio."),
                POJO(R.drawable.imagen10, "Real Monasterio de Santa Clara", "Un histórico monasterio que combina arte sacro con una arquitectura singular.")
            )
        )


        POJOAdapter = POJOAdapter(POJOS) { item ->
            deleteItem(item)
        }

        bindingMain.recyclerView.layoutManager = LinearLayoutManager(this)
        bindingMain.recyclerView.adapter = POJOAdapter
    }

    private fun deleteItem(POJO: POJO) {
        val position = POJOS.indexOf(POJO)
        if (position != -1) {
            POJOS.removeAt(position)
            POJOAdapter.notifyItemRemoved(position)
        }
    }
}
