package com.acelost.universalcontent

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.acelost.universalcontent.fragmentbased.container.*
import com.acelost.universalcontent.fragmentbased.content.ProfileContentFragment
import com.acelost.universalcontent.fragmentbased.content.impl.ProfileActivity

class FragmentBasedApproachActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.show_alert).setOnClickListener {
            AlertDialogContainer.withContent(ProfileContentFragment())
                    .show(supportFragmentManager, "ALERT_DIALOG:PROFILE")
        }
        findViewById<Button>(R.id.show_bottom_sheet).setOnClickListener {
            BottomSheetContainer.withContent(ProfileContentFragment())
                    .instant(false)
                    .show(supportFragmentManager, "BOTTOM_SHEET:PROFILE")
        }
        findViewById<Button>(R.id.show_popup_menu).setOnClickListener {
            PopupContainer.withContent(ProfileContentFragment())
                    .setAnchor(it)
                    .show(supportFragmentManager, "POPUP:PROFILE")
        }
        findViewById<Button>(R.id.show_activity).setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.show_fragment).setOnClickListener {
            val fragment = FragmentContainer.withContent(ProfileContentFragment())
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
        }
    }

}