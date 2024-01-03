package uv.tc.cuponsmartclientemovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import uv.tc.cuponsmartclientemovil.databinding.ActivityEditarPerfilBinding
import uv.tc.cuponsmartclientemovil.poko.Cliente
import uv.tc.cuponsmartclientemovil.poko.Mensaje
import uv.tc.cuponsmartclientemovil.util.Constantes

class EditarPerfilActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditarPerfilBinding
    private lateinit var cliente: Cliente
    private var isEditable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        obtenerDatosCliente()

        if (cliente != null) {
            cargarDatosCliente()
        }

        binding.btnEdicion.setOnClickListener {
            isEditable = !isEditable
            habilitarEdicion(isEditable)
        }
        binding.etEmail.isEnabled = false



    }
    override fun onResume(){
        super.onResume()
        binding.btnGuardarEdicion.setOnClickListener {
            val clienteEditado = Cliente()
            clienteEditado.nombre = binding.etNombre.text.toString()
            clienteEditado.apellido_paterno = binding.etApellidopat.text.toString()
            clienteEditado.apellido_materno = binding.etApellidomat.text.toString()
            clienteEditado.telefono = binding.etTelefono.text.toString()
            clienteEditado.password = binding.etContrasena.text.toString()
            clienteEditado.correo_electronico = cliente.correo_electronico
            clienteEditado.calle = binding.etDireccion.text.toString()
            clienteEditado.fecha_nacimiento = binding.etFechanacimiento.text.toString()
            clienteEditado.numero_calle = (binding.etNumeroCalle.text.toString()).toInt()
            clienteEditado.id_cliente = cliente.id_cliente
            realizarPeticionEditar(clienteEditado)

        }

    }

    private fun habilitarEdicion(isEditable: Boolean) {
        binding.etNombre.isEnabled = isEditable
        binding.etApellidopat.isEnabled = isEditable
        binding.etApellidomat.isEnabled = isEditable
        binding.etTelefono.isEnabled = isEditable
        binding.etDireccion.isEnabled = isEditable
        binding.etNumeroCalle.isEnabled = isEditable
        binding.etContrasena.isEnabled = isEditable
        binding.etFechanacimiento.isEnabled = isEditable
        if (isEditable)
            binding.btnEdicion.visibility = View.VISIBLE
        else {
            binding.btnEdicion.visibility = View.INVISIBLE
        }


    }

    fun obtenerDatosCliente() {
        val jsonCliente = intent.getStringExtra("cliente")
        val gson = Gson()
        cliente = gson.fromJson(jsonCliente, Cliente::class.java)
    }

    fun cargarDatosCliente() {
        binding.etEmail.isEnabled = false
        binding.etNombre.setText(cliente.nombre)
        binding.etApellidopat.setText(cliente.apellido_paterno)
        binding.etApellidomat.setText(cliente.apellido_materno)
        binding.etTelefono.setText(cliente.telefono)
        binding.etFechanacimiento.setText(cliente.fecha_nacimiento)
        binding.etContrasena.setText(cliente.password)
        binding.etEmail.setText(cliente.correo_electronico)
        binding.etDireccion.setText(cliente.calle)
        binding.etNumeroCalle.setText(cliente.numero_calle.toString())
    }


    fun realizarPeticionEditar(clienteEditado: Cliente) {
        val gson = Gson()
        val jsonCliente = gson.toJson(clienteEditado)
        Ion.with(this@EditarPerfilActivity)
            .load("PUT", Constantes.URL_WS + "clientes/editar")
            .setHeader("Content-Type", "application/json")
            .setStringBody(jsonCliente)
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    verificarEdicion(result)
                    println(result.toString())

                } else {
                    Toast.makeText(
                        this@EditarPerfilActivity,
                        "Error en petición para actualizar la información.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    fun verificarEdicion(resultado: String) {
        val gson = Gson()
        var mensaje = gson.fromJson(resultado, Mensaje::class.java)
        Toast.makeText(this@EditarPerfilActivity, mensaje.contenido, Toast.LENGTH_LONG).show()
        if (!mensaje.error) {
            isEditable = !isEditable
            habilitarEdicion(isEditable)
        }
    }
}