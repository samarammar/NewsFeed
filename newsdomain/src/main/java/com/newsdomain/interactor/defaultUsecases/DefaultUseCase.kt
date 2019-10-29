package com.newsdomain.interactor.defaultUsecases

abstract class DefaultUseCase<ReturnType,Params>{
    abstract fun buildUseCase(params: Params):ReturnType
}