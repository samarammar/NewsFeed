package com.newsdomain.interactor.defaultUsecases


import com.newsdomain.diinterfaces.excuter.PostExecutionThread
import com.newsdomain.diinterfaces.excuter.ThreadExecutor
import com.newsdomain.state.BaseVS
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

//import io.reactivex.android.schedulers.AndroidSchedulers



open class ObservableUseCase (private val threadExecutor: ThreadExecutor,
                              private val postExecutionThread: PostExecutionThread
){
    open fun getType():Int{
        return 0
    }
    fun prepareObservable(observable: Observable<BaseVS>):Observable<BaseVS>{
        return observable.onErrorReturn { BaseVS.Error.getWithType(it,getType()) }
                .startWith(BaseVS.Loading.getWithType(getType()))
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
    }
}