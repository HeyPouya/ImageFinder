package ir.heydarii.imagefinder.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ir.heydarii.imagefinder.R

/**
 * All activities are child of this class
 */
open class BaseActivity : AppCompatActivity() {

    protected fun showTryAgain(view: View, message: String, action: () -> Unit) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.try_again)) {
                run {
                    action
                }
            }
            .show()
    }
}
