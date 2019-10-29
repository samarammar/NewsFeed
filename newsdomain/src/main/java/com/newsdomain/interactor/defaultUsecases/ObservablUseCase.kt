package com.newsdomain.interactor.defaultUsecases

import com.newsdomain.state.BaseVS
import io.reactivex.Observable


abstract class ObservablUseCase<Params>  {
    abstract fun buildUseCase(params: Params): Observable<BaseVS>

}