package uv.tc.cuponsmartclientemovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import uv.tc.cuponsmartclientemovil.databinding.ActivityRegistrarClienteBinding
import uv.tc.cuponsmartclientemovil.poko.Cliente
import uv.tc.cuponsmartclientemovil.poko.Mensaje
import uv.tc.cuponsmartclientemovil.util.Constantes

class RegistrarClienteActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrarClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.btnCancelarRegistro.setOnClickListener {
            irPantallaPrincipal()
            finish()

        }
        binding.btnRegistrocliente.setOnClickListener {
            val clienteRegistro = Cliente()

            clienteRegistro.nombre = binding.etNombre.text.toString()
            clienteRegistro.apellido_paterno =  binding.etApellidopat.text.toString()
            clienteRegistro.apellido_materno= binding.etApellidomat.text.toString()
            clienteRegistro.correo_electronico = binding.etEmail.text.toString()
            clienteRegistro.calle = binding.etDireccion.text.toString()
            clienteRegistro.numero_calle = (binding.etNumero.text.toString()).toInt()
            clienteRegistro.password= binding.etContrasena.text.toString()
             clienteRegistro.telefono = binding.etTelefono.text.toString()
            clienteRegistro.fecha_nacimiento = binding.etFechanacimiento.text.toString()

            validarCamposLlenos()
            realizarPeticionRegistro(clienteRegistro)
        }
    }






   fun serializarRespuestaRegistro(json:String){
        val gson = Gson()
       val mensaje = gson.fromJson(json,Mensaje::class.java)

    }

    private fun realizarPeticionRegistro(clienteNuevo: Cliente) {
        val gson = Gson()
        val jsonCliente = gson.toJson(clienteNuevo)

        Ion.getDefault(this@RegistrarClienteActivity).conscryptMiddleware.enable(false)
        Ion.with(this@RegistrarClienteActivity)
            .load("POST",Constantes.URL_WS + "clientes/agregar")
            .setHeader("Content-Type", "application/json")
            .setStringBody(jsonCliente)
            .asString()
            .setCallback { e, result ->
                if (e== null && result !=null){
                  Toast.makeText(this@RegistrarClienteActivity,"¡Registro realizado con éxito!",  Toast.LENGTH_LONG).show()
                    irPantallaPrincipal()

                }else{
                    Toast.makeText(this@RegistrarClienteActivity, "Error al registrar",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    fun validarCamposLlenos(): Boolean {
        var isValid = true
        val nombreCliente = binding.etNombre.text.toString()
        val apellidoPaterno = binding.etApellidopat.text.toString()
        val apellidoMaterno = binding.etApellidomat.text.toString()
        val fechaNacimiento = binding.etFechanacimiento.text.toString()
        val telefonoCliente = binding.etTelefono.text.toString()

        val email = binding.etEmail.text.toString()
        val password  = binding.etContrasena.text.toString()

        if (nombreCliente.isEmpty()){
            isValid = false
            binding.etNombre.error = "Nombre requerido"
        }
        if(apellidoPaterno.isEmpty()){
            isValid = false
            binding.etApellidopat.error="Apellido paterno requerido"

        }
        if(apellidoMaterno.isEmpty()){
            isValid = false
            binding.etApellidomat.error = "Apellido materno requerido"
        }
        if (email.isEmpty()) {
            isValid = false
            binding.etEmail.error = "Correo electrónico requerido"
        }
        if (password.isEmpty()) {
            isValid = false
            binding.etContrasena.error = "Contraseña requerida"
        }
        if (telefonoCliente.isEmpty()) {
            isValid = false
            binding.etTelefono.error = "Contraseña requerida"
        }
        if (fechaNacimiento.isEmpty()) {
            isValid = false
            binding.etFechanacimiento.error = "Contraseña requerida"
        }


        return isValid
    }


    private fun irPantallaPrincipal() {
        val intent = Intent(this@RegistrarClienteActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}