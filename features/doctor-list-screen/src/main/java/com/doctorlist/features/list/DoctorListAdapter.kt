package com.doctorlist.features.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.clear
import coil.api.load
import coil.transform.CircleCropTransformation
import com.doctorlist.features.list.model.ListItem
import com.doctorlist.features.list.model.ListItem.*
import kotlinx.android.synthetic.main.doctor_list_item.view.*

class DoctorListAdapter(
    private val onItemClick: (DoctorItem) -> Unit
) : PagedListAdapter<ListItem, DoctorViewHolder>(
    INSPECTION_DIFF_CALLBACK
) {
    fun clear() {
        submitList(null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DoctorViewHolder(
            createView(parent, viewType)
        )

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        getItem(position)?.let { item ->
            if (item is DoctorItem) {
                with(holder.itemView) {
                    tag = item
                    ivPhoto.showPhoto(item.doctor.photoId)
                    tvName.text = item.doctor.name
                    tvAddress.text = item.doctor.address
                    tvPhone.text = item.doctor.phoneNumber
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is RecentDoctorView -> ListItem.RECENT_DOCTOR_VIEW
        is VivyDoctorView -> ListItem.VIVY_DOCTOR_VIEW
        is DoctorItem -> ListItem.DOCTOR_ITEM
        else -> throw IllegalArgumentException("View type not handled here!")
    }

    private fun createView(parent: ViewGroup, viewType: Int) = when (viewType) {
        ListItem.RECENT_DOCTOR_VIEW -> createRecentDoctorView(parent)
        ListItem.VIVY_DOCTOR_VIEW -> createVivyDoctor(parent)
        ListItem.DOCTOR_ITEM -> createDoctorView(parent)
        else -> throw IllegalArgumentException("View type not handled here!")
    }

    private fun createRecentDoctorView(parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(
            R.layout.recent_doctor_view, parent, false
        )

    private fun createVivyDoctor(parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(
            R.layout.vivy_doctor_view, parent, false
        )

    private fun createDoctorView(parent: ViewGroup) = LayoutInflater.from(parent.context).inflate(
        R.layout.doctor_list_item, parent, false
    ).apply {
        setOnClickListener {
            onItemClick(tag as DoctorItem)
        }
    }
}

class DoctorViewHolder(view: View) : RecyclerView.ViewHolder(view)

private val INSPECTION_DIFF_CALLBACK =
    object : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
            if (oldItem is DoctorItem && newItem is DoctorItem) {
                oldItem.doctor == newItem.doctor
            } else {
                oldItem == newItem
            }
    }

private fun ImageView.showPhoto(url: String) {
    clear()
    if (url.isEmpty()) {
        setImageResource(R.drawable.ic_account)
    } else {
        this.load(url) {
            error(R.drawable.ic_error)
            transformations(CircleCropTransformation())
        }
    }
}