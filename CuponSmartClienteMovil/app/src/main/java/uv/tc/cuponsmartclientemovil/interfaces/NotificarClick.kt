package uv.tc.cuponsmartclientemovil.interfaces

import uv.tc.cuponsmartclientemovil.poko.Promocion

interface NotificarClick {
    fun seleccionarItem(posicion: Int, promocion: Promocion)
}