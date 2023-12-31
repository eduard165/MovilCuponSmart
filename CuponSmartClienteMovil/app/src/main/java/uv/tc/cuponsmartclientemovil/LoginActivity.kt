package uv.tc.cuponsmartclientemovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.koushikdutta.ion.Ion
import org.json.JSONObject
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

        binding.btnRegistrarUsuario.setOnClickListener {
            val intent = Intent(this, RegistrarClienteActivity::class.java)
            startActivity(intent)
        }

        fun irPantallaPrincipal(cliente: Cliente) {
            val intent = Intent(this@LoginActivity, InicioActivity::class.java)
            val gson = Gson()
            val cadenaJson: String = gson.toJson(cliente)
            intent.putExtra("cliente", cadenaJson)
            startActivity(intent)
            finish()
        }
//Aquí marcaba error en la serializacion de la respuestaLogin
        fun serializarRespuestaLogin(json: String) {
            val gson = Gson()
            try {
                var respuesta: RespuestaLoginCliente =
                    gson.fromJson(json, RespuestaLoginCliente::class.java)
                Toast.makeText(this@LoginActivity, respuesta.contenido, Toast.LENGTH_LONG).show()
                if (!respuesta.error) {
                    irPantallaPrincipal(respuesta.cliente)
                }
            } catch (e: JsonSyntaxException) {
               e.printStackTrace()
            }
        }

        //Esta es la peticion al ws
        fun peticionLogin(correo_electronico: String, password: String) {
            Ion.getDefault(this@LoginActivity).conscryptMiddleware.enable(false)
            Ion.with(this@LoginActivity)
                .load("POST", Constantes.URL_WS + "autenticacion/validacion/cliente")
                .setHeader("Content-Type", "application/json")
                //Json
                .asString()
                .setCallback { e, result ->

                    if (e == null && result != null) {
                        serializarRespuestaLogin(result)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Error en la petición",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
        binding.btnIniciarSesion.setOnClickListener {
            val correo_electronico: String = binding.etCorreo.text.toString()
            val password: String = binding.etPassword.text.toString()
            if (validacionCampos(correo_electronico, password)) {
                peticionLogin(correo_electronico, password)
            }

        }

    }


}