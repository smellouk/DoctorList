package com.doctorlist.features.list

import com.doctorlist.common.base.BaseCommand
import com.doctorlist.features.list.model.ListItem

sealed class Command : BaseCommand {
    object GetDoctors : Command()
    class NavigateToDetails(val item: ListItem.DoctorItem) : Command()
    class SetViewState(val viewState: ViewState) : Command()
}