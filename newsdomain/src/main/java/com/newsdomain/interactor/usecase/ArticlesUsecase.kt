package com.newsdomain.interactor.usecase

import com.newsdomain.diinterfaces.excuter.PostExecutionThread
import com.newsdomain.diinterfaces.excuter.ThreadExecutor
import com.newsdomain.interactor.defaultUsecases.ObservableUseCase
import com.newsdomain.repository.INewsRepository
import com.newsdomain.state.BaseVS
import com.newsdomain.statesresult.ArticlesResult
import io.reactivex.Observable
import javax.inject.Inject

class ArticlesUsecase @Inject constructor(val iRepository: INewsRepository,
                                          threadExecutor: ThreadExecutor,
                                          postExecutionThread: PostExecutionThread
)
    : ObservableUseCase(threadExecutor,postExecutionThread) {

    fun getArticles (): Observable<BaseVS> {
        return prepareObservable(
                iRepository.getArticles()
                .map { ArticlesResult(it) as BaseVS })
    }

}