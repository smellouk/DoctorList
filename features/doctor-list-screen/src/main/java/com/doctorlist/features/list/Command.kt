package com.doctorlist.features.list

import com.doctorlist.common.base.BaseCommand
import com.doctorlist.features.list.model.Item

sealed class Command : BaseCommand {
    object GetDoctors : Command()
    class LoadMoreDoctors(val page: String?, val currentList: List<Item>) : Command()
    class NavigateToDetails(val item: Item.DoctorItem) : Command()
    class RefreshList(val items: List<Item>) : Command()
    class SetViewState(val viewState: ViewState) : Command()
}