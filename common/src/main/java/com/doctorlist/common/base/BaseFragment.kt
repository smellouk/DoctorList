package com.doctorlist.common.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doctorlist.common.utils.ViewModelFactory
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<
        ComponentProvider : BaseComponentProvider,
        State : BaseViewState,
        ViewModel : BaseViewModel<State>
        >(layout: Int) :
    Fragment(layout) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var componentProvider: ComponentProvider

    lateinit var viewModel: ViewModel

    override fun onStart() {
        super.onStart()
        componentProvider =
            activity?.application as? ComponentProvider ?: throw IllegalArgumentException(
                "Application did not implement component provider!"
            )
        inject()

        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
        viewModel = viewModelProvider[getViewModelClass()]
        startObserving()
        retainInstance = true
    }

    abstract fun getViewModelClass(): Class<out ViewModel>

    override fun onStop() {
        stopObserving()
        super.onStop()
    }

    /**
     * Use this method if you have a state without view rendering
     */
    open fun renderDefaultViewState() {
        // NO OP
    }

    abstract fun inject()

    abstract fun renderViewState(state: State)

    private fun startObserving() {
        viewModel.liveData.observe(
            this,
            Observer { renderViewState(it) }
        )
    }

    private fun stopObserving() {
        viewModel.liveData.removeObservers(this)
    }
}