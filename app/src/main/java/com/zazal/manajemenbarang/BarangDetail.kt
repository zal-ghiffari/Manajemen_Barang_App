package com.zazal.manajemenbarang

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import java.text.SimpleDateFormat
import java.util.HashMap

class BarangDetail : AppCompatActivity() {

    internal lateinit var progressDialog: ProgressDialog
    private var jsonParser: JsonParser = JsonParser()
    private var gson: Gson = Gson()
    internal lateinit var queue: RequestQueue
    internal lateinit var bundle:Bundle
    internal lateinit var barang:Barang

    private lateinit var txtnama : TextView
    private lateinit var txtmerek : TextView
    private lateinit var txtwarna : TextView
    private lateinit var txttahun : TextView
    private lateinit var txtharga : TextView
    private lateinit var txtketerangan : TextView
    private lateinit var txtkondisi : TextView
    private lateinit var txt_tanggal_masuk : TextView
    private lateinit var txt_tanggal_update : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barang_detail)

        bundle = intent.extras!!
        queue = Volley.newRequestQueue(this)

        progressDialog = ProgressDialog(this@BarangDetail)
        progressDialog.setMessage("Tunggu...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val btnKembali = findViewById(R.id.btnKembali) as RelativeLayout
        btnKembali.setOnClickListener { onBackPressed() }

        val btnEdit = findViewById(R.id.btnEdit) as RelativeLayout
        btnEdit.setOnClickListener { v ->
            val i = Intent(v.context, TambahBarang::class.java)
            i.putExtra("editor", "editor")
            i.putExtra("nama", barang.nama)
            i.putExtra("merek", barang.merek)
            i.putExtra("warna", barang.warna)
            i.putExtra("tahun", barang.tahun)
            i.putExtra("harga", barang.harga)
            i.putExtra("kondisi", barang.kondisi)
            i.putExtra("keterangan", barang.keterangan)
            i.putExtra("id", barang.id)
            i.putExtra("tanggal_masuk", barang.tanggal_masuk)
            v.context.startActivity(i)
        }

        val btnHapus = findViewById(R.id.btnHapus) as RelativeLayout
        btnHapus.setOnClickListener {
            val list = arrayOfNulls<String>(2)
            list[0] = "Ya"
            list[1] = "Tidak"
            val builder = AlertDialog.Builder(this@BarangDetail)
            builder.setTitle("Anda yakin menghapus barang ini?")
            builder.setItems(list) { _, which ->
                if (which == 0) {
                    val stringRequest = object: StringRequest(Request.Method.POST,
                        getString(R.string.webservice) + "hapusBarang.php", Response.Listener<String> { response ->
                            try {
                                progressDialog.cancel()
                                Toast.makeText(this@BarangDetail, response, Toast.LENGTH_LONG).show()
                                val i = Intent(this@BarangDetail, MainActivity::class.java)
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(i)
                            } catch (e:Exception) {
                                Toast.makeText(this@BarangDetail, "Koneksi ke server bermasalah!", Toast.LENGTH_SHORT).show()
                                e.printStackTrace()
                                progressDialog.cancel()
                            }
                        }, Response.ErrorListener {
                            progressDialog.cancel()
                            Toast.makeText(this@BarangDetail,
                                "Koneksi ke server bermasalah!",
                                Toast.LENGTH_LONG).show()
                        }) {
                        @Throws(AuthFailureError::class)
                        override fun getHeaders():Map<String, String> {
                            val params = HashMap<String, String>()
                            params["PATH"] = "hapusBarang"
                            params["ID"] = bundle.getString("id")!!
                            return params
                        }
                    }
                    queue.add<String>(stringRequest)
                }
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        val stringRequest = object: StringRequest(Request.Method.POST,
            getString(R.string.webservice) + "getBarang.php", Response.Listener<String> { response ->
                try {
                    val mJson = jsonParser.parse(response) as JsonArray
                    barang = gson.fromJson<Barang>(mJson.get(0), Barang::class.java)

                    txtnama.text = barang.nama
                    txtmerek.text = barang.merek
                    txtwarna.text = barang.warna
                    txttahun.text = barang.tahun
                    txtharga.text = barang.harga
                    txtketerangan.text = barang.keterangan

                    if (barang.kondisi==("1")) {
                        txtkondisi.text = "Baru"
                    } else {
                        txtkondisi.text = "Bekas"
                    }

                    val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    val formatterbr = SimpleDateFormat("dd/MM/yyyy 'às' hh:mm")
                    var result = formatter.parse(barang.tanggal_masuk)
                    txt_tanggal_masuk.text = formatterbr.format(result)

                    try {
                        if (barang.tanggal_update.trim() != ("")) {
                            result = formatter.parse(barang.tanggal_update)
                            txt_tanggal_update.text = formatterbr.format(result)
                        }
                    } catch (e:Exception) {
                        e.printStackTrace()
                        txt_tanggal_update.text = formatterbr.format(result)
                    }
                    progressDialog.cancel()
                } catch (e:Exception) {
                    Toast.makeText(this@BarangDetail, "Koneksi ke server bermasalah!", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                    progressDialog.cancel()
                }
            }, Response.ErrorListener {
                progressDialog.cancel()
                Toast.makeText(this@BarangDetail,
                    "Koneksi ke server bermasalah!",
                    Toast.LENGTH_LONG).show()
            }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders():Map<String, String> {

                val params = HashMap<String, String>()
                params["PATH"] = "getDetailBarang"
                params["ID"] = bundle.getString("id")!!

                return params
            }

        }
        queue.add<String>(stringRequest)
    }
}