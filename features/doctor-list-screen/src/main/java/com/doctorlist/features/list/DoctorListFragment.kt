package com.doctorlist.features.list

import android.os.Bundle
import android.view.View
import com.doctorlist.common.base.BaseFragment
import com.doctorlist.common.exhaustive
import com.doctorlist.common.hide
import com.doctorlist.common.navigation.MainNavigator
import com.doctorlist.common.show
import com.doctorlist.features.list.Command.*
import com.doctorlist.features.list.ViewState.*
import com.doctorlist.features.list.di.DoctorListComponentProvider
import com.doctorlist.features.list.model.ListItem.DoctorItem
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.fragment_doctor_list.*

class DoctorListFragment : BaseFragment<
        DoctorListComponentProvider,
        ViewState,
        DoctorListViewModel>(
    R.layout.fragment_doctor_list
) {
    private val mainNavigator: MainNavigator by lazy {
        activity as? MainNavigator ?: throw IllegalArgumentException(
            "Activity[${activity?.javaClass?.name}] is not implementing ${MainNavigator::class.java.name}"
        )
    }

    private val onItemClick: (DoctorItem) -> Unit = { doctor ->
        viewModel.onCommand(NavigateToDetails(doctor))
    }

    private val doctorListAdapter =
        DoctorListAdapter(onItemClick)

    override fun getViewModelClass(): Class<out DoctorListViewModel> =
        DoctorListViewModel::class.java

    override fun inject() {
        componentProvider.getDoctorListComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvDoctors.adapter = doctorListAdapter
        swipeToRefresh.setOnRefreshListener {
            getDoctors()
        }
    }

    override fun renderViewState(state: ViewState) {
        when (state) {
            is Initial -> getDoctors()
            is Pending -> renderDefaultViewState()
            is Loading -> renderLoading()
            is StopLoading -> renderStopLoading()
            is DoctorListReady -> renderDoctorList(state)
            is Error -> renderError(state)
            is DoctorDetails -> renderDoctorDetails(state)
        }.exhaustive
    }

    private fun getDoctors() {
        viewModel.onCommand(GetDoctors)
    }

    private fun renderLoading() {
        swipeToRefresh.isRefreshing = true
    }

    private fun renderStopLoading() {
        swipeToRefresh.isRefreshing = false
    }

    private fun renderDoctorList(state: DoctorListReady) {
        tvError.hide()
        rvDoctors.show()
        doctorListAdapter.clear()
        doctorListAdapter.submitList(state.list)
    }

    private fun renderError(state: Error) {
        swipeToRefresh.isRefreshing = false
        tvError.show()
        rvDoctors.hide()

        val message = state.message?.let {
            "$it\n\n${getString(R.string.swipe_down_hint)}"
        } ?: getString(R.string.empty_list_error)
        tvError.text = message
    }

    private fun renderDoctorDetails(state: DoctorDetails) {
        viewModel.onCommand(SetViewState(Pending))
        mainNavigator.navigateToDoctorDetails(state.doctor)
    }
}