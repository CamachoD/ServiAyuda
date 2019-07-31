package com.serviayuda.pc.serviayuda.Actividades

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.serviayuda.pc.serviayuda.Activitys.InterfazActivity
import com.serviayuda.pc.serviayuda.DatabaseHelper
import com.serviayuda.pc.serviayuda.ManejadorPreferencias
import com.serviayuda.pc.serviayuda.R
import com.serviayuda.pc.serviayuda.Usuario
import kotlinx.android.synthetic.main.ajustes.*

class SettingsActivity : AppCompatActivity() {

    lateinit var estadoInterfaz : String
    lateinit var sSesion : String
    lateinit var mp : ManejadorPreferencias
    lateinit var databaseHelper: DatabaseHelper
    lateinit var usuario : Usuario
    val activity = this

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.ajustes)
        intialViews()
        initialListeners()
    }

    private fun intialViews() {

        switchInterfaz.isChecked
        val preferences = this.getSharedPreferences("SESION", Activity.MODE_PRIVATE)
        mp = ManejadorPreferencias(preferences)
        estadoInterfaz = mp.cargarPreferencias("KEY_INTERFAZ")
        databaseHelper = DatabaseHelper(activity)
        usuario = databaseHelper.getUsuario(mp.cargarPreferencias("KEY_EMAIL"));

        if (usuario.tipoPerfil.compareTo("Solicitante") == 0) {
            switchInterfaz.visibility = View.VISIBLE
            SNInterfaz.visibility = View.VISIBLE
            textInterfaz.visibility = View.VISIBLE

            if (estadoInterfaz.compareTo("No") == 0 ||
                    estadoInterfaz.compareTo("No existe la información") == 0) {
                switchInterfaz.isChecked = false
                SNInterfaz.text = "No"

            } else {
                switchInterfaz.isChecked = true
                SNInterfaz.text = "Si"
            }
        } else {
            switchInterfaz.visibility = View.GONE
            SNInterfaz.visibility = View.GONE
            textInterfaz.visibility = View.GONE
        }

        sSesion = mp.cargarPreferencias("KEY_SESION")

        if (sSesion.compareTo("No") == 0 ||
                sSesion.compareTo("No existe la información") == 0) {
            SNSesion.text = "No"
            switchSesion.isChecked = false

        } else {
            SNSesion.text = "Si"
            switchSesion.isChecked = true
        }
    }

    private fun initialListeners() {
        switchInterfaz.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                SNInterfaz.text = "Si"
                mp.guardarPreferencias("KEY_INTERFAZ", "Si")
                InterfazActivity(this, activity, mp.cargarPreferencias("KEY_EMAIL"), "Si").execute()

            } else {
                SNInterfaz.text = "No"
                mp.guardarPreferencias("KEY_INTERFAZ", "No")
                InterfazActivity(this, activity, mp.cargarPreferencias("KEY_EMAIL"), "No").execute()
            }
        }

        switchSesion.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                SNSesion.text = "Si"
                mp.guardarPreferencias("KEY_SESION", "Si")

            } else {
                SNSesion.setText("No")
                mp.guardarPreferencias("KEY_SESION", "No")
            }
        }

        cerrarSesion.setOnClickListener {
            SNSesion.text = "No"
            mp.guardarPreferencias("KEY_SESION", "No")
            val i = Intent(this, Inicio::class.java)
            finishAffinity()
            startActivity(i)
        }
    }

    override fun getCallingActivity(): ComponentName? {
        return super.getCallingActivity()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i: Intent
        if (switchInterfaz.isChecked) {
            i = Intent(this, MenuInterfazAdaptada::class.java)
            finishAffinity()

        } else {
            i = Intent(this, ActivitySetViewPagerSolicitante::class.java)
            finishAffinity()
        }
        startActivity(i)
    }
}