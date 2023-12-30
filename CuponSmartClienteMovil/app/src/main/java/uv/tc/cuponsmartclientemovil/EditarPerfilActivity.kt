package uv.tc.cuponsmartclientemovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uv.tc.cuponsmartclientemovil.databinding.ActivityEditarPerfilBinding

class EditarPerfilActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditarPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
    }
}