package com.marius.onlineshop

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.marius.onlineshop.adapter.ProductAdapter
import com.marius.onlineshop.helper.opensDetails
import com.marius.onlineshop.model.Product
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), opensDetails {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onItemClicked(id: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("productId", id)
        startActivity(intent)
        changeTitle("New title")
    }

    private fun changeTitle(string: String) {
        findViewById<TextView>(R.id.title).text = string
    }

    private val adapter = ProductAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView()
        adapter.setProducts(
            listOf(
                Product(1, "A", "123", 1.77f),
                Product(2, "B", "123", 7.77f),
                Product(3, "C", "123", 13.77f),
                Product(4, "D", "123", 58.77f)
            )
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                Toast.makeText(this,"Settings option",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.help -> {
                showAlertDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert Dialog")
            .setMessage("Message dialog")
            .setPositiveButton("OK") { dialog, id ->
                Log.d(TAG,"message OK")
            }
            .setNegativeButton("Cancel") { dialog, id ->
                Log.d(TAG,"message Cancel")
            }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("title", findViewById<TextView>(R.id.title).text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        findViewById<TextView>(R.id.title).text = savedInstanceState.getString("title")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "On Resume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "On Restart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "On Pause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "On Destroy")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "On Stop")
    }

    private fun setUpRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}
