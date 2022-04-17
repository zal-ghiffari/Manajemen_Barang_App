package com.zazal.manajemenbarang

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.HashMap

class TambahBarang : AppCompatActivity() {
    internal var kondisi: String = "-1"

    internal lateinit var progressDialog: ProgressDialog
    internal lateinit var queue: RequestQueue
    internal var bundle: Bundle? = null

    internal lateinit var tanggal_masuk: String
    internal lateinit var id: String
    internal var editor = false

    private lateinit var etnama : EditText
    private lateinit var etmerek : EditText
    private lateinit var etharga : EditText
    private lateinit var etwarna : EditText
    private lateinit var rttahun : EditText
    private lateinit var etketerangan : EditText
    private lateinit var txtkondisitambah : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_barang)

        queue = Volley.newRequestQueue(this)

        etnama = findViewById(R.id.etnama)
        etmerek = findViewById(R.id.etmerek)
        etharga = findViewById(R.id.etharga)
        etwarna = findViewById(R.id.etwarna)
        rttahun = findViewById(R.id.rttahun)
        etketerangan = findViewById(R.id.etketerangan)
        txtkondisitambah = findViewById(R.id.txtkondisitambah)

        val rlKeterangan = findViewById(R.id.rlKondisi) as RelativeLayout
        rlKeterangan.setOnClickListener {
            val list = arrayOfNulls<String>(2)
            list[0] = "Baru"
            list[1] = "Bekas"
            val builder = AlertDialog.Builder(this@TambahBarang)
            builder.setTitle("Apakah barang baru atau bekas?")
            builder.setItems(list) { dialogInterface, which ->
                if (which == 0) {
                    txtkondisitambah.text = "Baru"
                    kondisi = "1"
                } else {
                    txtkondisitambah.text = "Bekas"
                    kondisi = "0"
                }
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        val btnInput = findViewById(R.id.btnInput) as RelativeLayout
        btnInput.setOnClickListener {
            if (testForm()) {

                progressDialog = ProgressDialog(this@TambahBarang)
                progressDialog.setMessage("Tunggu...")
                progressDialog.setCancelable(false)
                progressDialog.show()

                var url = getString(R.string.webservice) + "tambahBarang.php"
                if (editor) {
                    url = getString(R.string.webservice) + "editBarang.php"
                }

                val stringRequest = object : StringRequest(Request.Method.POST,
                    url, Response.Listener { response ->

                        try {
                            progressDialog.cancel()
                            Toast.makeText(this@TambahBarang, response, Toast.LENGTH_LONG).show()
                            val i = Intent(this@TambahBarang, MainActivity::class.java)
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(i)

                        } catch (e: Exception) {
                            Toast.makeText(this@TambahBarang, "Koneksi ke server bermasalah!", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                            progressDialog.cancel()
                        }
                    }, Response.ErrorListener {
                        progressDialog.cancel()

                        Toast.makeText(this@TambahBarang,
                            "Koneksi ke server bermasalah!",
                            Toast.LENGTH_LONG).show()
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val params = HashMap<String, String>()
                        if (editor) {
                            params["PATH"] = "editBarang"
                        } else {
                            params["PATH"] = "tambahBarang"
                        }
                        params["NAMA"] = etnama.text.toString().trim { it <= ' ' }
                        params["MEREK"] = etmerek.text.toString().trim { it <= ' ' }
                        params["HARGA"] = etharga.text.toString().trim { it <= ' ' }
                        params["WARNA"] = etwarna.text.toString().trim { it <= ' ' }
                        params["TAHUN"] = rttahun.text.toString().trim { it <= ' ' }
                        params["KETERANGAN"] = etketerangan.text.toString().trim { it <= ' ' }
                        params["KONDISI"] = kondisi
                        if (editor) {
                            params["ID"] = id
                            //params.put("DT_CADASTRO", dt_cadastro);
                        }

                        return params
                    }
                }

                queue.add(stringRequest)
            }
        }

        try {
            bundle = intent.extras
            if (bundle!!.getString("editor")!!.equals("editor", ignoreCase = true)) {

                editor = true

                etnama.setText(bundle!!.getString("nama"), TextView.BufferType.EDITABLE)
                etmerek.setText(bundle!!.getString("merek"), TextView.BufferType.EDITABLE)
                etwarna.setText(bundle!!.getString("warna"), TextView.BufferType.EDITABLE)
                rttahun.setText(bundle!!.getString("tahun"), TextView.BufferType.EDITABLE)
                etharga.setText(bundle!!.getString("harga"), TextView.BufferType.EDITABLE)
                etketerangan.setText(bundle!!.getString("keterangan"), TextView.BufferType.EDITABLE)

                id = bundle!!.getString("id").toString()
                tanggal_masuk = bundle!!.getString("tanggal_masuk").toString()

                kondisi = bundle!!.getString("kondisi").toString()
                if (kondisi === "1") {
                    txtkondisitambah.text = "Baru"

                } else {
                    txtkondisitambah.text = "Bekas"
                }
            }

        } catch (e: Exception) {
        }
    }

    private fun testForm(): Boolean {
        if (etnama.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)
            || etmerek.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)
            || etwarna.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)
            || rttahun.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)
            || etharga.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)
            || etketerangan.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)
            || kondisi === "-1") {
            Toast.makeText(this, "Isi semua field.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}