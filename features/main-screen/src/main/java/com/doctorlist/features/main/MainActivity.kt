package com.doctorlist.features.main

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.doctorlist.common.base.BaseActivity
import com.doctorlist.common.exhaustive
import com.doctorlist.common.model.Doctor
import com.doctorlist.common.navigation.MainNavigator
import com.doctorlist.common.utils.Keys.DOCTOR_KEY
import com.doctorlist.features.main.Command.*
import com.doctorlist.features.main.ViewState.*
import com.doctorlist.features.main.di.MainComponentProvider

class MainActivity : BaseActivity<MainComponentProvider, ViewState, MainViewModel>(
    R.layout.activity_main_screen
), MainNavigator {
    private val navController: NavController by lazy {
        Navigation.findNavController(
            this, R.id.navMain
        )
    }

    override fun getViewModelClass(): Class<out MainViewModel> = MainViewModel::class.java

    override fun inject() {
        componentProvider.getMainComponent().inject(this)
    }

    override fun renderViewState(state: ViewState) {
        when (state) {
            is Initial -> renderDefaultViewState()
            is DoctorList -> navigateTo(state.destination)
            is DoctorDetails -> navigateTo(
                state.destination, bundleOf(
                    DOCTOR_KEY to state.doctor
                )
            )
            is Pending -> renderDefaultViewState()
        }.exhaustive
    }

    override fun navigateToDoctorList() {
        viewModel.onCommand(OpenDoctorList)
    }

    override fun navigateToDoctorDetails(doctor: Doctor) {
        viewModel.onCommand(OpenDoctorDetails(doctor))
    }

    private fun navigateTo(@IdRes destination: Int, bundle: Bundle? = null) {
        viewModel.onCommand(SetViewState(Pending))
        navController.navigate(destination, bundle)
    }
}