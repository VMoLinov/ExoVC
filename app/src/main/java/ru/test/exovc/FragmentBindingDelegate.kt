package ru.test.exovc

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentBindingDelegate<T>(
    private val fragment: Fragment,
    private val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    var binding: T? = null

    init {
        fragment.lifecycle.addObserver(lifecycleOnCreateEventObserver())
    }

    private fun lifecycleOnCreateEventObserver() =
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { lifecycleOwner ->
                    lifecycleOwner.lifecycle.addObserver(lifecycleOnDestroyEventObserver())
                }
            }
        }

    private fun lifecycleOnDestroyEventObserver() =
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) binding = null
        }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) return binding
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not initialize binding when views are destroyed")
        }
        return viewBindingFactory.invoke(thisRef.requireView()).also {
            this@FragmentBindingDelegate.binding = it
        }
    }
}
