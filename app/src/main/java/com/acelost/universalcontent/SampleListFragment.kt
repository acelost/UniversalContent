package com.acelost.universalcontent

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.acelost.universalcontent.lib.content.ContentCreator
import java.util.ArrayList

class SampleListFragment : Fragment() {

    class Creator(
            val content: ArrayList<String>
    ) : ContentCreator {
        override fun createFragment(): Fragment {
            return SampleListFragment.newInstance(content)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = LayoutInflater.from(context!!).inflate(R.layout.fragment_list_sample, container, false)
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_view)
        recycler.layoutManager = LinearLayoutManager(context!!)
        recycler.adapter = Adapter(getContent())
        return root
    }

    private fun getContent(): List<String> {
        return arguments!!.getStringArrayList("list_key")
    }

    companion object {

        @JvmStatic
        fun newInstance(list: List<String>): SampleListFragment {
            val args = Bundle(1)
            args.putStringArrayList("list_key", ArrayList<String>(list))
            return SampleListFragment().apply {
                arguments = args
            }
        }

    }

    private class Adapter(
            val content: List<String>
    ) : RecyclerView.Adapter<ViewHolder>() {

        override fun getItemCount() = content.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

        override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setText(content[position])

    }

    private class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_sample, parent, false)
    ) {

        fun setText(text: String) {
            (itemView as TextView).text = text
        }

    }

}