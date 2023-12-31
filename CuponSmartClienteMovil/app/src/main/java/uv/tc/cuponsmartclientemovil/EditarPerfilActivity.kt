package uv.tc.cuponsmartclientemovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import uv.tc.cuponsmartclientemovil.databinding.ActivityEditarPerfilBinding
import uv.tc.cuponsmartclientemovil.poko.Cliente

class EditarPerfilActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditarPerfilBinding
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val jsonCliente = intent.getStringExtra("cliente")
        if(jsonCliente != null){
            serializarCliente(jsonCliente)

        }
    }

    fun serializarCliente(json : String){
        val gson  = Gson()
        cliente = gson.fromJson(json, Cliente::class.java)
    }
}