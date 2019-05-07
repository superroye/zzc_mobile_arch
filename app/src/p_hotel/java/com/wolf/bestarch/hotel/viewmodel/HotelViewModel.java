package com.wolf.bestarch.hotel.viewmodel;

import android.arch.lifecycle.MediatorLiveData;

import com.wolf.bestarch.hotel.repository.HotelRepository;
import com.wolf.bestarch.hotel.repository.bean.HotelItem;
import com.supylc.mobilearch.general.arch.LifeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roye
 * @date 2019/1/15
 */
public class HotelViewModel extends LifeViewModel {

    private HotelRepository repository;
    private MediatorLiveData<List> list = new MediatorLiveData<>();
    private Object[] results = new Object[6];

    public HotelViewModel() {
        repository = new HotelRepository();
    }

    public MediatorLiveData<List> list() {
        return list;
    }

    List items;

    public void loadData() {
        items = new ArrayList();
        HotelItem item;
        for (int i = 0; i < 20; i++) {
            item = new HotelItem();
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
