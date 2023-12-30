package uv.tc.cuponsmartclientemovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import uv.tc.cuponsmartclientemovil.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fun validacionCampos(email: String, password: String): Boolean {


            var isValid = true
            if (email.isEmpty()) {
                isValid = false
                binding.etCorreo.error = "Correo electrónico requerido"
            }
            if (password.isEmpty()) {
                isValid = false
                binding.etPassword.error = "Contraseña requerida"
            }
            return isValid

        }

        binding.btnIniciarSesion.setOnClickListener {
            val email: String = binding.etCorreo.text.toString()
            val password: String = binding.etPassword.text.toString()
            if (validacionCampos(email, password)) {
                irPantallaPrincipal()
                Toast.makeText(
                    this@LoginActivity,
                    "Campos llenados correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.btnRegistrarUsuario.setOnClickListener {
            val intent = Intent(this, RegistrarClienteActivity::class.java)
            startActivity(intent)
        }


    }

    private fun irPantallaPrincipal() {
        val intent = Intent(this@LoginActivity, InicioActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }
}