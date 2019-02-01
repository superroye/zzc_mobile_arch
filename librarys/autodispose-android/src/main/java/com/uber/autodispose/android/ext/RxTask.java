package com.uber.autodispose.android.ext;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Roye
 * @date 2018/12/13
 */
public class RxTask {

    public static RxTaskBuilder runOnMain() {
        return new RxTaskBuilder(AndroidSchedulers.mainThread());
    }

    public static RxTaskBuilder runOnBg() {
        return new RxTaskBuilder(Schedulers.io());
    }

    public static RxTaskBuilder runOn(Scheduler scheduler) {
        return new RxTaskBuilder(scheduler);
    }

    public static final class RxTaskBuilder {
        private TaskLifecycleScopeProvider mScopeProvider;
        private Scheduler mScheduler;

        public RxTaskBuilder(Scheduler scheduler) {
            this.mScheduler = scheduler;
        }

        public RxTaskBuilder scopeProvider(TaskLifecycleScopeProvider scopeProvider) {
            this.mScopeProvider = scopeProvider;
            return this;
        }

        public void subscribe(final Consumer<Context> consumer) {
            Observable.empty().subscribeOn(mScheduler).subscribe(new DefaultObserver<Object>() {
                @Override
                public void onNext(Object s) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    if (mScopeProvider == null || mScopeProvider.getContext() == null) {
                        realCall();
                    } else if (mScopeProvider.shouldDoByLifecycle()) {
                        realCall();
                    }
                }

                //粗暴try-catch，防止崩溃
                void realCall() {
                    try {
                        consumer.accept(mScopeProvider.getContext());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
