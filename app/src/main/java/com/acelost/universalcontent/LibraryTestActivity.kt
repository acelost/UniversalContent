package com.acelost.universalcontent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.acelost.universalcontent.lib.container.bottomsheet.ContainerBottomSheet

class LibraryTestActivity : AppCompatActivity() {

    private val mocks = arrayListOf(
            "Library", "Test", "Activity"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_test)
        findViewById<Button>(R.id.show_sample).setOnClickListener {
            ContainerBottomSheet()
                    .setContentCreator(SampleListFragment.Creator(mocks))
                    .show(supportFragmentManager, "123")
        }
    }

}