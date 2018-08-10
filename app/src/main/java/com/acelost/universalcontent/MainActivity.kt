package com.acelost.universalcontent

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.acelost.universalcontent.contentbased.container.AlertDialogContainer
import com.acelost.universalcontent.contentbased.container.BottomSheetContainer
import com.acelost.universalcontent.contentbased.container.FragmentContainer
import com.acelost.universalcontent.contentbased.content.impl.ProfileContent
import com.acelost.universalcontent.contentbased.content.impl.ProfileActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.show_alert).setOnClickListener {
            AlertDialogContainer.withContent(ProfileContent())
                    .show(supportFragmentManager, "ALERT_DIALOG:PROFILE")
        }
        findViewById<Button>(R.id.show_bottom_sheet).setOnClickListener {
            BottomSheetContainer.withContent(ProfileContent())
                    .instant(false)
                    .show(supportFragmentManager, "BOTTOM_SHEET:PROFILE")
        }
        findViewById<Button>(R.id.show_activity).setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.show_fragment).setOnClickListener {
            val fragment = FragmentContainer.withContent(ProfileContent())
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
        }
    }

}
