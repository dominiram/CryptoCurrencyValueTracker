package app.naum.myapplication.views

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import app.naum.myapplication.R
import app.naum.myapplication.databinding.ActivityMainBinding
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.viewmodels.AllCoinsViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showLoadingIndicator() {
        binding.spinKit.visibility = View.VISIBLE
    }

    fun hideLoadingIndicator() {
        binding.spinKit.visibility = View.GONE
    }

    fun Activity.displayMetrics(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}