package com.doctorlist.features.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.doctorlist.common.base.BaseFragment
import com.doctorlist.common.exhaustive
import com.doctorlist.common.hide
import com.doctorlist.common.navigation.MainNavigator
import com.doctorlist.common.show
import com.doctorlist.common.utils.EndlessRecyclerViewScrollListener
import com.doctorlist.features.list.Command.*
import com.doctorlist.features.list.ViewState.*
import com.doctorlist.features.list.di.DoctorListComponentProvider
import com.doctorlist.features.list.model.Item
import com.doctorlist.features.list.model.Item.DoctorItem
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

    private val endlessScroller: EndlessRecyclerViewScrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(
            rvDoctors.layoutManager as StaggeredGridLayoutManager
        ) {
            override fun onLoadMore(page: String?, totalItemsCount: Int, view: RecyclerView?) {
                loadMoreDoctors(page, doctorListAdapter.currentList)
            }
        }
    }

    private val onItemClick: (DoctorItem) -> Unit = { doctor ->
        viewModel.onCommand(NavigateToDetails(doctor))
    }

    private val doctorListAdapter = DoctorListAdapter(onItemClick)

    override fun getViewModelClass(): Class<out DoctorListViewModel> =
        DoctorListViewModel::class.java

    override fun inject() {
        componentProvider.getDoctorListComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvDoctors.apply {
            adapter = doctorListAdapter
            addOnScrollListener(endlessScroller)
        }
        swipeToRefresh.setOnRefreshListener {
            endlessScroller.resetState()
            getDoctors()
        }
    }

    override fun renderViewState(state: ViewState) {
        when (state) {
            is Initial -> getDoctors()
            is Pending -> renderDefaultViewState()
            is DoctorListReady -> renderDoctorList(state)
            is Error -> renderError(state)
            is DoctorDetails -> renderDoctorDetails(state)
            is RefreshListReady -> renderRefreshList(state)
            is StopLoading -> renderStopLoading()
        }.exhaustive
    }

    private fun renderStopLoading() {
        swipeToRefresh.isRefreshing = false
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    private fun getDoctors() {
        swipeToRefresh.isRefreshing = true
        viewModel.onCommand(GetDoctors)
    }

    private fun loadMoreDoctors(
        page: String?,
        currentList: List<Item>
    ) {
        swipeToRefresh.isRefreshing = true
        viewModel.onCommand(
            LoadMoreDoctors(page, currentList)
        )
    }

    private fun refreshList() {
        viewModel.onCommand(
            RefreshList(doctorListAdapter.currentList)
        )
    }

    private fun renderDoctorList(state: DoctorListReady) {
        tvError.hide()
        rvDoctors.show()
        renderStopLoading()
        rvDoctors.tag = state.nextPage
        with(doctorListAdapter) {
            submitList(state.list)
        }
    }

    private fun renderRefreshList(state: RefreshListReady) {
        doctorListAdapter.submitList(state.list)
    }

    private fun renderError(state: Error) {
        renderStopLoading()
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