package com.wolf.bestarch.home;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wolf.bestarch.R;
import com.wolf.bestarch.home.support.holder.HomeItemHolder;
import com.wolf.bestarch.home.support.holder.TaobaoTestHolder;
import com.wolf.bestarch.home.viewmodel.HomeViewModel;
import com.zzc.mobilearch.uilibs.activity.BaseFragment;
import com.zzc.mobilearch.uilibs.widget.loadpage.XSwipeRefreshLayout;
import com.zzc.mobilearch.uilibs.widget.recyclerview.TalentAdapter;

/**
 * @author Roye
 * @date 2019/1/29
 */
public class HomeFragment extends BaseFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_home;
    }

    public static final String HOME_HOTEL_PAGE = "home_hotel_page";
    private static final String TAG = "HotelFragment";

    XSwipeRefreshLayout refreshLayout;
    RecyclerView listRv;
    TalentAdapter adapter;
    HomeViewModel mHomeViewModel;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        adapter.addHolderType(HomeItemHolder.class);
        adapter.addHolderType(TaobaoTestHolder.class);
        listRv.setAdapter(adapter);

        addObserver();
    }

    @Override
    public void initData() {
        loadData();
    }

    void addObserver() {
        mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        getLifecycle().addObserver(mHomeViewModel);
        mHomeViewModel.list().observe(this, data -> {
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
        }
    }


    void loadData() {
        mHomeViewModel.loadData();
    }

}
