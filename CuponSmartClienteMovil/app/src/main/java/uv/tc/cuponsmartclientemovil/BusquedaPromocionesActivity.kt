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
import java.util.Locale

class BusquedaPromocionesActivity : AppCompatActivity(), NotificarClick {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var id_estatus:Int = 0
    private var id_promocion:Int =0
    private var listaPromociones = ArrayList<Promocion>()
    private lateinit var adaptador:PromocionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busqueda_empresa)

        recyclerView = findViewById(R.id.rv_promos)
        searchView = findViewById(R.id.buscar)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        consultarPromocionesDisponibles(id_estatus)
        recyclerView.adapter = adaptador

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

    }



   

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Promocion>()
            for (i in listaPromociones) {
                if (i.nombre_empresa.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adaptador.setFilteredList(filteredList)
            }
        }
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