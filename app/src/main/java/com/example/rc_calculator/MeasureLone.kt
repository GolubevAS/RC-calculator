package com.example.rc_calculator

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean


class MeasureLone<T> : MutableLiveData<T>() {

    private val unsure = AtomicBoolean(false)


    @MainThread
    override fun setValue(t: T?) {
        unsure.set(true)
        super.setValue(t)
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        super.observe(owner, Observer<T> { t ->
            if (unsure.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }


}