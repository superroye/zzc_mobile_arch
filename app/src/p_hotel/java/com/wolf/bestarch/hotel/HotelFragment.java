package com.wolf.bestarch.hotel;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supylc.mobilearch.core.util.T;
import com.wolf.bestarch.R;
import com.wolf.bestarch.hotel.support.holder.*;
import com.wolf.bestarch.hotel.viewmodel.HotelViewModel;
import com.supylc.mobilearch.uilibs.activity.BaseFragment;
import com.supylc.mobilearch.uilibs.widget.loadpage.XSwipeRefreshLayout;
import com.supylc.mobilearch.uilibs.widget.recyclerview.TalentAdapter;

/**
 * @author Roye
 * @date 2019/1/11
 */
public class HotelFragment extends BaseFragment {

    public static final String HOME_HOTEL_PAGE = "home_hotel_page";
    private static final String TAG = "HotelFragment";

    @Override
    public int layoutId() {
        return R.layout.fragment_hotel;
    }

    XSwipeRefreshLayout refreshLayout;
    RecyclerView listRv;
    TalentAdapter adapter;
    HotelViewModel mHotelViewModel;

    public static HotelFragment newInstance() {
        HotelFragment fragment = new HotelFragment();
        return fragment;
    }

    public void initView() {
        refreshLayout = findView(R.id.swipe_refresh);
        listRv = findView(R.id.recycler_view);

        listRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            loadData();
        });
        adapter = new TalentAdapter();
        adapter.registerHolder(HotelItemHolder.class);
        listRv.setAdapter(adapter);

        addObserver();
    }

    @Override
    public void initData() {
        loadData();
    }

    void addObserver() {
        mHotelViewModel = ViewModelProviders.of(getActivity()).get(HotelViewModel.class);
        getLifecycle().addObserver(mHotelViewModel);
        mHotelViewModel.list().observe(this, data -> {
            refreshLayout.setRefreshing(false);
            if (isHidden()) {
                shouldReload = true;
                return;
            } else {
                shouldReload = false;
            }
            if (data != null) {
                ((TalentAdapter) listRv.getAdapter()).resetItems(data);
            }
        });
    }

    boolean shouldReload;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (shouldReload && !hidden) {
            mHotelViewModel.refresh();
        }
    }


    void loadData() {
        mHotelViewModel.loadData();
    }

}