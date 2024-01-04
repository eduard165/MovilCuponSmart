package uv.tc.cuponsmartclientemovil

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import uv.tc.cuponsmartclientemovil.databinding.ActivityPromocionesBinding
import uv.tc.cuponsmartclientemovil.interfaces.NotificarClick
import uv.tc.cuponsmartclientemovil.poko.Categoria
import uv.tc.cuponsmartclientemovil.poko.Promocion
import uv.tc.cuponsmartclientemovil.util.Constantes

class PromocionesActivity : AppCompatActivity(), NotificarClick {

    private lateinit var binding:ActivityPromocionesBinding
    private var id_categoria:Int =0
    private var listaPromociones:ArrayList<Promocion> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromocionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        id_categoria = intent.getIntExtra("idCategoria",0)
        consultarPromocionesCategoria(id_categoria)

    }

    private fun consultarPromocionesCategoria(id_categoria: Int) {
        Ion.with(this@PromocionesActivity)
            .load("GET", Constantes.URL_WS+"GestionOfertas/PromocionesEmpresa/"+id_categoria)
            .asString()
            .setCallback { e , result ->
                if (e == null && result !=null){
                    serializarInformacionPromociones(result)
                    cargarInfoRecycler()
                }
                else{
                    Toast.makeText(this@PromocionesActivity, "No se pudo obtener la información" +
                            "de las promociones",Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun cargarInfoRecycler() {
        binding.recyclerPromociones.layoutManager = LinearLayoutManager(this)
        binding.recyclerPromociones.setHasFixedSize(true)
        if(listaPromociones.size>0){
            binding.tvNoPromociones.visibility = View.GONE
            binding.recyclerPromociones.adapter = PromocionAdapter(listaPromociones,this)

        }
    }

    private fun serializarInformacionPromociones(json: String) {
        val gson = Gson()
        val typeLista = object: TypeToken<ArrayList<Promocion>>(){}.type
        listaPromociones = gson.fromJson(json,typeLista)
    }

    override fun seleccionarItem(posicion: Int, promocion: Promocion) {
        Toast.makeText(this@PromocionesActivity, posicion.toString(),
            Toast.LENGTH_LONG).show()
        mostrarInformacionPromocion(promocion)
    }

    fun mostrarInformacionPromocion(promocion:Promocion){
        val alert = AlertDialog.Builder(this@PromocionesActivity)
        alert.setTitle("Promocion seleccionada")
        alert.setMessage("Promocion" + promocion.nombre_promocion)
        alert.setPositiveButton("Ver detalles", DialogInterface.OnClickListener { dialogInterface,
                                                                                  i -> verDetalles() })
        alert.setNegativeButton("Cerrar", null)
        val dialog = alert.create()
        dialog.show()
    }

    private fun verDetalles() {

        val intent = Intent(this@PromocionesActivity, DetallesPromocionActivity::class.java)
        intent.putExtra("id_categoria", id_categoria)
        startActivity(intent)
    }


}