package com.doctorlist.features.main

import com.doctorlist.common.base.BaseCommand
import com.doctorlist.common.model.Doctor

sealed class Command : BaseCommand {
    object OpenDoctorList : Command()
    class OpenDoctorDetails(val doctor: Doctor) : Command()
    class SetViewState(val state: ViewState) : Command()
}