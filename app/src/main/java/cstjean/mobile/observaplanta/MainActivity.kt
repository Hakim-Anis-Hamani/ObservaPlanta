package cstjean.mobile.observaplanta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity qui contient un seul Fragment.
 *
 * @author Hakim-Anis Hamani
 */
class MainActivity : AppCompatActivity() {

    /**
     * Initialisation de l'Activity.
     *
     * @param savedInstanceState Les données conservées au changement d'état.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}