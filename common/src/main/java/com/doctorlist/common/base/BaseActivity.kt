package com.doctorlist.common.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doctorlist.common.utils.ViewModelFactory
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<
        ComponentProvider : BaseComponentProvider,
        State : BaseViewState,
        ViewModel : BaseViewModel<State>
        >(layout: Int) :
    AppCompatActivity(layout) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var componentProvider: ComponentProvider

    lateinit var viewModel: ViewModel

    override fun onStart() {
        super.onStart()
        componentProvider = application as? ComponentProvider ?: throw IllegalArgumentException(
            "Application did not implement component provider!"
        )
        inject()

        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
        viewModel = viewModelProvider[getViewModelClass()]

        startObserving()
    }

    abstract fun getViewModelClass(): Class<out ViewModel>

    override fun onStop() {
        super.onStop()
        stopObserving()
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