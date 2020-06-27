package com.doctorlist.features.details

import android.widget.ImageView
import coil.api.clear
import coil.api.load
import com.doctorlist.common.base.BaseFragment
import com.doctorlist.common.exhaustive
import com.doctorlist.common.model.Doctor
import com.doctorlist.common.utils.Keys
import com.doctorlist.features.details.di.DoctorDetailsComponentProvider
import kotlinx.android.synthetic.main.fragment_doctor_details.*

class DoctorDetailsFragment :
    BaseFragment<DoctorDetailsComponentProvider, ViewState, DoctorDetailsViewModel>(
        R.layout.fragment_doctor_details
    ) {
    override fun getViewModelClass(): Class<out DoctorDetailsViewModel> =
        DoctorDetailsViewModel::class.java

    override fun inject() {
        componentProvider.getDoctorDetailsComponent().inject(this)
    }

    override fun renderViewState(state: ViewState) {
        when (state) {
            ViewState.Initial -> renderInitialView()
        }.exhaustive
    }

    private fun renderInitialView() {
        val doctor: Doctor =
            arguments?.getParcelable(Keys.DOCTOR_KEY) ?: throw IllegalArgumentException(
                "to use this fragment you need to send doctor"
            )

        ivDoctor.showPhoto(doctor.photoId)
        tvName.text = doctor.name
        tvAddress.text = doctor.address
    }

    private fun ImageView.showPhoto(url: String) {
        clear()
        if (url.isEmpty()) {
            setImageResource(R.drawable.ic_account)
        } else {
            this.load(url) {
                placeholder(R.drawable.ic_account)
                error(R.drawable.ic_error)
            }
        }
    }
}