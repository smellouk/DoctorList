package com.doctorlist.common.domain

import io.reactivex.Completable
import io.reactivex.Observable

interface BaseUseCase<DataState : BaseDataState> {
    fun buildObservable(): Observable<DataState>
}

interface BaseCompletableUseCase<Params : BaseParams> {
    fun buildObservable(params: Params): Completable
}