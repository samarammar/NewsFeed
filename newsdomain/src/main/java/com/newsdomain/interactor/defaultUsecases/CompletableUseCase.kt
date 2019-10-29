package com.newsdomain.interactor.defaultUsecases

import io.reactivex.Completable

abstract class CompletableUseCase<Params>{
    abstract fun buildUseCase(params: Params):Completable
}