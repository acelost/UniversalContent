package com.acelost.universalcontent.views

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acelost.universalcontent.R
import com.acelost.universalcontent.contentbased.core.Content
import com.acelost.universalcontent.contentbased.core.ContentContainer
import com.acelost.universalcontent.contentbased.impl.ProfileContent

class MyBottomSheetFragment : BottomSheetDialogFragment(), ContentContainer, ContentContainer.Appearing {

    private lateinit var mBehavior: BottomSheetBehavior<View>
    private lateinit var mContent: Content

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context?.let { context ->
            mContent = ProfileContent()
            return mContent.createView(context, null)
        } ?: return inflater.inflate(R.layout.content_profile, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.setOnShowListener { dialog ->
            mContent.attachToContainer(this)
            if (dialog is BottomSheetDialog) {
                val sheet = dialog.findViewById<View>(android.support.design.R.id.design_bottom_sheet)
                if (sheet != null) {
                    mBehavior = BottomSheetBehavior.from(sheet)
                    mBehavior.peekHeight = 0
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mContent.detachFromContainer()
        mContent.destroyView()
    }

    override fun requestAppearance(requesting: Content) {
        show()
    }

    private fun show() {
        mBehavior.apply {
            if (state != BottomSheetBehavior.STATE_EXPANDED) {
                state = BottomSheetBehavior.STATE_EXPANDED
            }
            skipCollapsed = true
        }
    }

}