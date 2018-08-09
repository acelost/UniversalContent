package com.acelost.universalcontent

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.acelost.universalcontent.contentbased.container.AlertDialogContainer
import com.acelost.universalcontent.contentbased.impl.ProfileContent
import com.acelost.universalcontent.views.MyActivity
import com.acelost.universalcontent.views.MyBottomSheetFragment
import com.acelost.universalcontent.views.MyFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.show_alert).setOnClickListener {
            AlertDialogContainer.withContent(ProfileContent())
                    .show(supportFragmentManager, "ALERT_DIALOG:PROFILE")
        }
        findViewById<Button>(R.id.show_bottom_sheet).setOnClickListener {
            val fragment = MyBottomSheetFragment()
            fragment.show(supportFragmentManager, "BOTTOM_SHEET:PROFILE")
        }
        findViewById<Button>(R.id.show_activity).setOnClickListener {
            val intent = Intent(this, MyActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.show_fragment).setOnClickListener {
            val fragment = MyFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
        }
    }

}
