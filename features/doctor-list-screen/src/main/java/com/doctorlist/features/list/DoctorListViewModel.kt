package com.doctorlist.features.list

import com.doctorlist.common.base.BaseViewModel
import com.doctorlist.common.base.Commandable
import com.doctorlist.common.exhaustive
import com.doctorlist.features.list.Command.*
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.di.PAGED_DOCTOR_LIST_COMPOSITE_DISPOSABLE
import com.doctorlist.features.list.domain.pageddoctorlist.PagedDoctorListUseCase
import com.doctorlist.features.list.domain.recentdoctors.AddDoctorToRecentParams
import com.doctorlist.features.list.domain.recentdoctors.AddDoctorToRecentUseCase
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

@DoctorListScope
class DoctorListViewModel @Inject constructor(
    @Named(PAGED_DOCTOR_LIST_COMPOSITE_DISPOSABLE)
    private val pagedDoctorListCompositeDisposable: CompositeDisposable,
    private val pagedDoctorListUseCase: PagedDoctorListUseCase,
    private val addDoctorToRecentUseCase: AddDoctorToRecentUseCase,
    private val viewStateMapper: ViewStateMapper
) : BaseViewModel<ViewState>(), Commandable<Command> {
    override fun getInitialState(): ViewState = ViewState.Initial

    override fun onCommand(cmd: Command) {
        liveData.value = when (cmd) {
            is GetDoctors -> onGetDoctors()
            is NavigateToDetails -> onNavigateToDoctorDetails(cmd)
            is SetViewState -> onSetViewState(cmd)
        }.exhaustive
    }

    override fun onCleared() {
        pagedDoctorListCompositeDisposable.clear()
        super.onCleared()
    }

    private fun onGetDoctors(): ViewState.Pending {
        addObservable(
            source = pagedDoctorListUseCase.buildListObservable()
                .distinctUntilChanged()
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

        addObservable(
            source = pagedDoctorListUseCase.buildRequestObservable()
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
}