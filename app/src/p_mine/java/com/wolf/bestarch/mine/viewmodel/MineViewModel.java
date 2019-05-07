package com.wolf.bestarch.mine.viewmodel;

import android.arch.lifecycle.MediatorLiveData;

import com.wolf.bestarch.mine.repository.MineRepository;
import com.wolf.bestarch.mine.repository.bean.MineItem;
import com.supylc.mobilearch.general.arch.LifeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roye
 * @date 2019/1/30
 */
public class MineViewModel extends LifeViewModel {

    private MineRepository repository;
    private MediatorLiveData<List> list = new MediatorLiveData<>();
    private Object[] results = new Object[6];

    public MineViewModel() {
        repository = new MineRepository();
    }

    public MediatorLiveData<List> list() {
        return list;
    }
    List items;
    public void loadData() {
        items = new ArrayList();
        MineItem item;
        for (int i = 0; i < 20; i++) {
            item = new MineItem();
            item.imgUrl = "https://dpic.tiankong.com/ih/tv/QJ6964436266.jpg?x-oss-process=style/670ws";
            item.text = new java.util.Random().nextLong() + "";
            items.add(item);
        }
        list().postValue(items);
    }

    public void refresh() {
        list().postValue(items);
    }
}
