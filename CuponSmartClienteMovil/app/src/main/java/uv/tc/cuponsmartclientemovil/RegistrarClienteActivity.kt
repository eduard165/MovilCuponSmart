package uv.tc.cuponsmartclientemovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import uv.tc.cuponsmartclientemovil.databinding.ActivityRegistrarClienteBinding
import uv.tc.cuponsmartclientemovil.poko.Cliente
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

        }
        binding.btnRegistrocliente.setOnClickListener {
            val nombre:String = binding.etNombre.text.toString()
            val apellido_paterno:String =  binding.etApellidopat.text.toString()
            val apellido_materno:String = binding.etApellidomat.text.toString()

            val correo_electronico: String = binding.etEmail.text.toString()
            val password: String = binding.etContrasena.text.toString()
            val telefono:String = binding.etTelefono.text.toString()
            val fecha_nacimiento:String = binding.etFechanacimiento.text.toString()
            validarCamposLlenos(nombre, apellido_paterno, apellido_materno,correo_electronico,password,telefono, fecha_nacimiento)
           // realizarPeticionRegistro(cliente)
        }
    }






   fun serializarRespuestaRegistro(json:String){
        val gson = Gson()
       val respuesta = gson.fromJson(json,Cliente::class.java)
    }
    private fun realizarPeticionRegistro(cliente: Cliente) {

        val gson = Gson() // Asegúrate de tener la dependencia de Gson en tu proyecto
        val clienteJson = gson.toJson(cliente)

        Ion.getDefault(this@RegistrarClienteActivity).conscryptMiddleware.enable(false)
        Ion.with(this@RegistrarClienteActivity)
            .load("POST",Constantes.URL_WS + "clientes/agregar")
            .setHeader("Content-Type", "application/json")
            .setStringBody(clienteJson)
            .asString()
            .setCallback { e, result ->
                if (e== null && result !=null){
                    serializarRespuestaRegistro(result)

                }else{
                    Toast.makeText(this@RegistrarClienteActivity, "Error al registrar",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    fun validarCamposLlenos(nombre: String, apellido_paterno:String,apellido_materno:String,
                            correo_electronico: String, password: String, telefono:String,
                            fecha_nacimiento:String): Boolean {
        var isValid = true

        if (nombre.isEmpty()){
            isValid = false
            binding.etNombre.error = "Nombre requerido"
        }
        if(apellido_paterno.isEmpty()){
            isValid = false
            binding.etApellidopat.error="Apellido paterno requerido"

        }
        if(apellido_materno.isEmpty()){
            isValid = false
            binding.etApellidomat.error = "Apellido materno requerido"
        }
        if (correo_electronico.isEmpty()) {
            isValid = false
            binding.etEmail.error = "Correo electrónico requerido"
        }
        if (password.isEmpty()) {
            isValid = false
            binding.etContrasena.error = "Contraseña requerida"
        }
        return isValid
    }


    private fun irPantallaPrincipal() {
        val intent = Intent(this@RegistrarClienteActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}