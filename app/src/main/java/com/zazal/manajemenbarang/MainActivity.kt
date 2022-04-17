package com.zazal.manajemenbarang

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import java.util.ArrayList
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    internal lateinit var progressDialog: ProgressDialog
    internal var barangList: ArrayList<Barang> = ArrayList()
    internal lateinit var queue: RequestQueue
    internal lateinit var adapter: BarangListAdapter

    private var jsonParser: JsonParser? = null
    private var gson: Gson? = null

    private lateinit var btnTambahBarang : FloatingActionButton
    private lateinit var swipeRefresh : SwipeRefreshLayout
    private lateinit var errormessage : TextView
    private lateinit var barangListView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jsonParser = JsonParser()
        gson = Gson()

        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener { carregarLista() }

        btnTambahBarang = findViewById(R.id.btnTambahBarang)
        btnTambahBarang.setOnClickListener { v ->
            val i = Intent(v.context, TambahBarang::class.java)
            v.context.startActivity(i)
        }

        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Tunggu...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        queue = Volley.newRequestQueue(this)

        carregarLista()

    }


    fun carregarLista() {
        barangList.clear()
        val stringRequest = object : StringRequest(Request.Method.POST,
            getString(R.string.webservice) + "getSemuaBarang.php", Response.Listener { response ->
                try {

                    val mJson = jsonParser!!.parse(response) as JsonArray
                    barangList = ArrayList()

                    (0 until mJson.size())
                        .map {
                            gson!!.fromJson(mJson.get(it),
                                Barang::class.java)
                        }
                        .forEach { barangList.add(it) }

                    adapter = BarangListAdapter(this, barangList)

                    barangListView = findViewById(R.id.barangListView)

                    barangListView?.adapter = adapter
                    adapter.notifyDataSetChanged()

                    errormessage.text = "Tidak dapat menemukan barang!."
                    barangListView.emptyView = errormessage

                    barangListView.setOnItemClickListener { _, view, position, _ ->
                        Toast.makeText(this@MainActivity, "Keteken Kok!", Toast.LENGTH_SHORT).show()
                        val i = Intent(view.context, BarangDetail::class.java)
                        i.putExtra("id", barangList[position].id)
                        view.context.startActivity(i)
                    }

                    progressDialog.cancel()
                    swipeRefresh.isRefreshing = false

                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Koneksi ke server bermasalah!", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                    progressDialog.cancel()
                    swipeRefresh.isRefreshing = false
                }
            }, Response.ErrorListener {
                progressDialog.cancel()
                swipeRefresh.isRefreshing = false
                Toast.makeText(this@MainActivity,
                    "Koneksi ke server bermasalah! : Listener",
                    Toast.LENGTH_LONG).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["PATH"] = "getBarang"

                return params
            }
        }
        queue.add(stringRequest)
    }

}