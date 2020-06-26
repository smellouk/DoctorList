package com.doctorlist.features.list

import com.doctorlist.common.base.BaseViewModel
import com.doctorlist.common.base.Commandable
import com.doctorlist.common.exhaustive
import com.doctorlist.features.list.Command.*
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.domain.GetDoctorsUseCase
import com.doctorlist.features.list.domain.LoadNextDoctorListPageParams
import com.doctorlist.features.list.domain.LoadNextDoctorListPageUseCase
import com.doctorlist.features.list.domain.recentdoctors.AddDoctorToRecentParams
import com.doctorlist.features.list.domain.recentdoctors.AddDoctorToRecentUseCase
import com.doctorlist.features.list.domain.refreshlist.RefreshListParams
import com.doctorlist.features.list.domain.refreshlist.RefreshListWhenItemClickUseCase
import javax.inject.Inject

@DoctorListScope
class DoctorListViewModel @Inject constructor(
    private val getDoctorsUseCase: GetDoctorsUseCase,
    private val loadNextDoctorListPageUseCase: LoadNextDoctorListPageUseCase,
    private val addDoctorToRecentUseCase: AddDoctorToRecentUseCase,
    private val refreshListWhenItemClickUseCase: RefreshListWhenItemClickUseCase,
    private val viewStateMapper: ViewStateMapper
) : BaseViewModel<ViewState>(), Commandable<Command> {
    override fun getInitialState(): ViewState = ViewState.Initial

    override fun onCommand(cmd: Command) {
        liveData.value = when (cmd) {
            is GetDoctors -> onGetDoctors()
            is NavigateToDetails -> onNavigateToDoctorDetails(cmd)
            is LoadMoreDoctors -> onLoadMoreDoctors(cmd)
            is SetViewState -> onSetViewState(cmd)
            is RefreshList -> onRefreshList(cmd)
        }.exhaustive
    }

    private fun onGetDoctors(): ViewState.Pending {
        addObservable(
            source = getDoctorsUseCase.buildObservable()
                .map { dataState ->
                    viewStateMapper.map(dataState)
                },
            onNext = { viewState ->
                liveData.value = viewState
            },
            onError = { throwable ->
                liveData.value = viewStateMapper.map(throwable)
            }
        )
        return ViewState.Pending
    }

    private fun onLoadMoreDoctors(cmd: LoadMoreDoctors): ViewState = cmd.page?.let {
        addObservable(
            source = loadNextDoctorListPageUseCase.buildObservable(
                LoadNextDoctorListPageParams(
                    cmd.page,
                    cmd.currentList
                )
            ).map { dataState ->
                viewStateMapper.map(dataState)
            },
            onNext = { viewState ->
                liveData.value = viewState
            },
            onError = { throwable ->
                liveData.value = viewStateMapper.map(throwable)
            }
        )
        ViewState.Pending
    } ?: ViewState.StopLoading

    private fun onNavigateToDoctorDetails(cmd: NavigateToDetails): ViewState.DoctorDetails {
        addCompletable(
            source = addDoctorToRecentUseCase.buildObservable(
                AddDoctorToRecentParams(
                    cmd.item.doctor
                )
            ),
            onError = { throwable ->
                liveData.value = viewStateMapper.map(throwable)
            }
        )
        return ViewState.DoctorDetails(cmd.item.doctor)
    }

    private fun onSetViewState(cmd: SetViewState): ViewState = cmd.viewState

    private fun onRefreshList(cmd: RefreshList): ViewState.Pending {
        if (cmd.items.isEmpty()) {
            return ViewState.Pending
        }

        addObservable(
            source = refreshListWhenItemClickUseCase.buildObservable(RefreshListParams(cmd.items))
                .map { dataState ->
                    viewStateMapper.map(dataState)
                },
            onNext = { viewState ->
                liveData.value = viewState
            },
            onError = { throwable ->
                liveData.value = viewStateMapper.map(throwable)
            }
        )
        return ViewState.Pending
    }
}