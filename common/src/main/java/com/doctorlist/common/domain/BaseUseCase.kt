package com.doctorlist.common.domain

import io.reactivex.Completable
import io.reactivex.Observable

interface BaseUseCase<DataState : BaseDataState> {
    fun buildObservable(): Observable<DataState>
}

interface BaseUseCaseWithParams<DataState : BaseDataState, Params : BaseParams> {
    fun buildObservable(params: Params): Observable<DataState>
}

interface BaseCompletableUseCase<Params : BaseParams> {
    fun buildObservable(params: Params): Completable
}