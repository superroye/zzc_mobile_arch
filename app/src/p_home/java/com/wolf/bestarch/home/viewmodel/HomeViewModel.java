package com.wolf.bestarch.home.viewmodel;

import android.arch.lifecycle.MediatorLiveData;

import com.wolf.bestarch.base.bean.TaobaoTest;
import com.wolf.bestarch.base.network.CoolAPi;
import com.wolf.bestarch.base.network.support.ResponseDataObserver;
import com.wolf.bestarch.home.repository.HomeRepository;
import com.wolf.bestarch.home.repository.bean.HomeItem;
import com.zzc.mobilearch.core.util.T;
import com.zzc.mobilearch.general.arch.LifeViewModel;
import com.zzc.network.ApiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roye
 * @date 2019/1/30
 */
public class HomeViewModel extends LifeViewModel {
    private HomeRepository repository;
    private MediatorLiveData<List> list = new MediatorLiveData<>();
    private Object[] results = new Object[6];

    public HomeViewModel() {
        repository = new HomeRepository();
    }


    public MediatorLiveData<List> list() {
        return list;
    }

    public void loadData() {
        List items = new ArrayList();
        HomeItem item;
        for (int i = 0; i < 20; i++) {
            item = new HomeItem();
            item.imgUrl = "https://dpic.tiankong.com/ih/tv/QJ6964436266.jpg?x-oss-process=style/670ws";
            item.text = new java.util.Random().nextLong() + "";
            items.add(item);
        }


        ResponseDataObserver observer = new ResponseDataObserver<TaobaoTest>(this){

            @Override
            public void onResponse(TaobaoTest result) {
                T.Log.d("====="+result.toString());
                list().postValue(result);
            }
        };
        observer.setLoading(getUIBehavior());

        ApiManager.api(CoolAPi.class).testSearchNetwork("市").subscribe(observer);

    }


}
