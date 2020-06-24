package com.doctorlist.features.main

import com.doctorlist.common.base.BaseViewModel
import com.doctorlist.common.base.Commandable
import com.doctorlist.features.main.ViewState.Initial
import com.doctorlist.features.main.di.MainScope
import javax.inject.Inject

@MainScope
class MainViewModel @Inject constructor(
    private val viewStateMapper: ViewStateMapper
) : BaseViewModel<ViewState>(), Commandable<Command> {
    override fun getInitialState() = Initial

    override fun onCommand(cmd: Command) {
        liveData.value = viewStateMapper.map(cmd)
    }
}