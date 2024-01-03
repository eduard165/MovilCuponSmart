package uv.tc.cuponsmartclientemovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import uv.tc.cuponsmartclientemovil.databinding.ActivityLoginBinding
import uv.tc.cuponsmartclientemovil.poko.Cliente
import uv.tc.cuponsmartclientemovil.poko.RespuestaLoginCliente
import uv.tc.cuponsmartclientemovil.util.Constantes

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnIniciarSesion.setOnClickListener {
            val correo_electronico: String = binding.etCorreo.text.toString()
            val password: String = binding.etPassword.text.toString()
            if (validacionCampos(correo_electronico, password)) {
                peticionLogin(correo_electronico, password)
            }
        }
            binding.btnRegistrarUsuario.setOnClickListener {
                val intent = Intent(this, RegistrarClienteActivity::class.java)
                startActivity(intent)
            }

        }
    fun validacionCampos(correo_electronico: String, password: String): Boolean {
        var isValid = true
        if (correo_electronico.isEmpty()) {
            isValid = false
            binding.etCorreo.error = "Correo electrónico requerido"
        }
        if (password.isEmpty()) {
            isValid = false
            binding.etPassword.error = "Contraseña requerida"
        }
        return isValid
    }



    fun irPantallaPrincipal(cliente: Cliente) {
        val intent = Intent(this@LoginActivity, InicioActivity::class.java)
        val gson = Gson()
        val cadenaJson: String = gson.toJson(cliente)
        intent.putExtra("cliente", cadenaJson)
        startActivity(intent)
        finish()
    }



    fun serializarRespuestaLogin(json:String) {
        val gson = Gson()
        val respuesta = gson.fromJson(json,RespuestaLoginCliente::class.java)
        Toast.makeText(this@LoginActivity, respuesta.contenido, Toast.LENGTH_LONG).show()
        if (!respuesta.error) {
            irPantallaPrincipal(respuesta.cliente)
        }

    }

    fun peticionLogin(correo_electronico: String, password: String) {
        Ion.getDefault(this@LoginActivity).conscryptMiddleware.enable(false)
        Ion.with(this@LoginActivity)
            .load("POST", Constantes.URL_WS + "login/validacion/cliente")
            .setHeader("Content-Type", "application/x-www-form-urlencoded")
            .setBodyParameter("correo_electronico",correo_electronico)
            .setBodyParameter("password",password)
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    println(result.toString())
                    serializarRespuestaLogin(result)
                    Toast.makeText(this@LoginActivity,result,Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this@LoginActivity, "Error en la petición",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

}

