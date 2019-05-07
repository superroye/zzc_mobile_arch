package com.supylc.mobilearch.general.arch;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import com.supylc.mobilearch.core.lifearch.LifeUIBehavior;
import com.supylc.mobilearch.core.lifearch.UIViewModel;
import com.supylc.mobilearch.core.util.T;
import com.supylc.mobilearch.rxjava2.IRxObserveDisposer;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Roye
 * @date 2018/8/22
 */
public class LifeViewModel extends ViewModel implements LifecycleObserver, IRxObserveDisposer {

    //创建subscription的时候，不小心以某种方式持有了context的引用，容易发生泄漏
    // 所以使用CompositeSubscription来持有所有的Subscriptions在activity销毁时取消订阅
    //其他页面采用addSubscription来注册Subscription
    private CompositeDisposable mCompositeDisposable;

    public LifeViewModel() {
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposable);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    private LifeUIBehavior mUIBehavior;

    public LifeUIBehavior getUIBehavior() {
        return mUIBehavior;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(LifecycleOwner lifecycleOwner) {
        if (mUIBehavior == null) {
            FragmentActivity activity = T.Context.getActivity(lifecycleOwner);
            if (activity == null) {
                throw new RuntimeException("lifecycleOwner is null, or this lifecycleOwner is not FragmentActivity type.");
            }
            UIViewModel viewModel = ViewModelProviders.of(activity).get(UIViewModel.class);
            mUIBehavior = viewModel.getLifeBehavior();
        }
    }

}
