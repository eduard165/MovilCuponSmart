package uv.tc.cuponsmartclientemovil

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import uv.tc.cuponsmartclientemovil.interfaces.NotificarClick
import uv.tc.cuponsmartclientemovil.poko.Promocion
import uv.tc.cuponsmartclientemovil.util.Constantes

class BusquedaPromocionesActivity : AppCompatActivity(), NotificarClick {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var id_estatus:Int = 0
    private var id_promocion:Int =0
    private var listaPromociones:ArrayList<Promocion> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busqueda_empresa)

        recyclerView = findViewById(R.id.rv_promos)
        searchView = findViewById(R.id.sv_buscar)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        consultarPromocionesDisponibles(id_estatus)

    }

    private fun consultarPromocionesDisponibles(id_estatus: Int) {
        Ion.with(this@BusquedaPromocionesActivity)
            .load("GET", Constantes.URL_WS+"GestionOfertas/obtenerPromocionesDisponibles/"+ this.id_estatus)
            .asString()
            .setCallback { e , result ->
                if (e == null && result !=null){
                    serializarInformacionPromociones(result)
                    cargarInfoRecycler()
                }
                else{
                    Toast.makeText(this@BusquedaPromocionesActivity, "No se pudo obtener la información" +
                            "de las promociones", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun cargarInfoRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(this)
      recyclerView.setHasFixedSize(true)

    }

    private fun serializarInformacionPromociones(json: String) {
        val gson = Gson()
        val typeLista = object: TypeToken<ArrayList<Promocion>>(){}.type
        listaPromociones = gson.fromJson(json,typeLista)
    }

    override fun seleccionarItem(posicion: Int, promocion: Promocion) {
        Toast.makeText(this@BusquedaPromocionesActivity, posicion.toString(),
            Toast.LENGTH_LONG).show()
        mostrarInformacionPromocion(promocion)
    }

    private fun mostrarInformacionPromocion(promocion: Promocion) {
        val alert = AlertDialog.Builder(this@BusquedaPromocionesActivity)
        alert.setTitle("Promocion seleccionada")
        alert.setMessage("Promocion" + promocion.nombre_promocion)
        alert.setPositiveButton("Ver detalles", DialogInterface.OnClickListener { dialogInterface,
                                                                                  i -> verDetalles() })
        alert.setNegativeButton("Cerrar", null)
        val dialog = alert.create()
        dialog.show()

    }

    private fun verDetalles() {

        val intent = Intent(this@BusquedaPromocionesActivity, DetallesPromocionActivity::class.java)
        intent.putExtra("id_promocion", id_promocion)
        startActivity(intent)
    }


}