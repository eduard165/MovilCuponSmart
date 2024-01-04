package uv.tc.cuponsmartclientemovil

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import uv.tc.cuponsmartclientemovil.databinding.ActivityInicioBinding
import uv.tc.cuponsmartclientemovil.poko.Promocion

class InicioActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityInicioBinding

    lateinit var drawerLayoutMenu: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbarMenu: Toolbar
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var card_alimentos: CardView
    lateinit var card_entretenimiento: CardView
    lateinit var card_viajes:CardView
    lateinit var card_electronicos: CardView
    lateinit var card_ropamoda: CardView
    lateinit var card_hogar: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        drawerLayoutMenu = binding.drawerLayout
        toolbarMenu = binding.toolbar
        navigationView = binding.navView
        navigationView.bringToFront()
        setSupportActionBar(toolbarMenu)



        toggle = ActionBarDrawerToggle(
            this,
            drawerLayoutMenu,
            toolbarMenu,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )
        drawerLayoutMenu.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)

        //Eventos de promociones con cardviews

        card_alimentos = binding.cvAlimentos

        card_entretenimiento = binding.cvEntretenimiento
        card_viajes = binding.cvViajes
        card_hogar = binding.cvHogar
        card_ropamoda = binding.cvRopaModa
        card_electronicos = binding.cvElectronica

        card_alimentos.setOnClickListener {
           val idCategoria = 1
            val intent = Intent(this@InicioActivity, PromocionesActivity::class.java)
            intent.putExtra("idCategoria", idCategoria)
            startActivity(intent)
        }
        card_entretenimiento.setOnClickListener {
            val idCategoria = 2
            val intent = Intent(this@InicioActivity, PromocionesActivity::class.java)
            intent.putExtra("idCategoria", idCategoria)
            startActivity(intent)
        }
        card_viajes.setOnClickListener {
            val idCategoria = 3
            val intent = Intent(this@InicioActivity, PromocionesActivity::class.java)
            intent.putExtra("idCategoria", idCategoria)
            startActivity(intent)
        }
        card_ropamoda.setOnClickListener {
            val idCategoria = 4
            val intent = Intent(this@InicioActivity, PromocionesActivity::class.java)
            intent.putExtra("idCategoria", idCategoria)
            startActivity(intent)
        }
        card_hogar.setOnClickListener {
            val idCategoria = 5
            val intent = Intent(this@InicioActivity, PromocionesActivity::class.java)
            intent.putExtra("idCategoria", idCategoria)
            startActivity(intent)
        }
        card_electronicos.setOnClickListener {
            val idCategoria = 6
            val intent = Intent(this@InicioActivity, PromocionesActivity::class.java)
            intent.putExtra("idCategoria", idCategoria)
            startActivity(intent)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_promos -> irMenuPrincipal()
            R.id.nav_perfil -> irEditarPerfil()
            R.id.nav_logout -> cerrarSesion()
            R.id.nav_empresas -> irBuscarPorEmpresa()
        }

        drawerLayoutMenu.closeDrawer(GravityCompat.START)
        return true
    }

    private fun cerrarSesion() {
        val intent = Intent(this@InicioActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irMenuPrincipal() {
        val intent = Intent(this@InicioActivity, InicioActivity::class.java)
        startActivity(intent)
    }

    fun irEditarPerfil() {
        val cadenaJson = intent.getStringExtra("cliente")
        val intent = Intent(this@InicioActivity, EditarPerfilActivity::class.java)
        intent.putExtra("cliente", cadenaJson)
        startActivity(intent)

    }

    fun irBuscarPorEmpresa() {
        val intent = Intent(this@InicioActivity, BusquedaPromocionesActivity::class.java)
        startActivity(intent)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}