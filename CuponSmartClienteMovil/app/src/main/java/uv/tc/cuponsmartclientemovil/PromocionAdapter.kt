package uv.tc.cuponsmartclientemovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import uv.tc.cuponsmartclientemovil.interfaces.NotificarClick
import uv.tc.cuponsmartclientemovil.poko.Promocion

class PromocionAdapter(val promociones :ArrayList<Promocion>,
                       val observador :NotificarClick) : RecyclerView
                           .Adapter<PromocionAdapter.ViewHolderPromociones>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPromociones {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_promociones,parent, false)
        return ViewHolderPromociones(itemView)
    }

    override fun getItemCount(): Int {
        return promociones.size
    }

    override fun onBindViewHolder(holder: ViewHolderPromociones, position: Int) {
        val promocion = promociones[position]
        holder.tvNombrePromo.text = ""+promocion.nombre_promocion
        holder.tvEmpresa.text = ""+promocion.nombre_empresa
        holder.tvTipoPromo.text = ""+ promocion.nombre_tipo
        holder.tvFechaTermino.text = "" + promocion.fecha_termino
        holder.tvCuponesDisponibles.text = "" + promocion.usos_disponibles
        holder.cardItem.setOnClickListener {
            observador.seleccionarItem(position,promocion)
        }


    }


    //Nombre de los datos que se van a cargar en la promocion
    class ViewHolderPromociones(itemView : View)  : RecyclerView.ViewHolder(itemView) {
        val tvNombrePromo : TextView = itemView.findViewById(R.id.tv_nombre_promo)
        val tvEmpresa: TextView = itemView.findViewById(R.id.tv_empresa)
        val tvFechaTermino : TextView = itemView.findViewById(R.id.tv_fecha_termino)
        val tvTipoPromo : TextView = itemView.findViewById(R.id.tv_tipo_promo)
        val tvDescuento : TextView = itemView.findViewById(R.id.tv_valor)
        val tvCuponesDisponibles : TextView = itemView.findViewById(R.id.tv_cupones_disponibles)

        val cardItem : CardView = itemView.findViewById(R.id.card_item)
    }
}