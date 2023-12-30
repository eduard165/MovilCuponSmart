package uv.tc.cuponsmartclientemovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uv.tc.cuponsmartclientemovil.databinding.ActivityRegistrarClienteBinding

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
    }

    private fun irPantallaPrincipal() {
        val intent = Intent(this@RegistrarClienteActivity,LoginActivity::class.java)
        startActivity(intent)
    }


}