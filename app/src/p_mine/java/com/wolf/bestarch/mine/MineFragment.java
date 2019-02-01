package com.wolf.bestarch.mine;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wolf.bestarch.R;
import com.wolf.bestarch.mine.support.holder.MineItemHolder;
import com.wolf.bestarch.mine.viewmodel.MineViewModel;
import com.zzc.mobilearch.uilibs.activity.BaseFragment;
import com.zzc.mobilearch.uilibs.widget.loadpage.XSwipeRefreshLayout;
import com.zzc.mobilearch.uilibs.widget.recyclerview.TalentAdapter;

/**
 * @author Roye
 * @date 2019/1/30
 */
public class MineFragment extends BaseFragment {
    public static final String HOME_HOTEL_PAGE = "home_hotel_page";
    private static final String TAG = "HotelFragment";

    @Override
    public int layoutId() {
        return R.layout.fragment_hotel;
    }

    XSwipeRefreshLayout refreshLayout;
    RecyclerView listRv;
    TalentAdapter adapter;
    MineViewModel mMineViewModel;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
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
        adapter.addHolderType(MineItemHolder.class);
        listRv.setAdapter(adapter);

        addObserver();
    }

    @Override
    public void initData() {
        loadData();
    }

    void addObserver() {
        mMineViewModel = ViewModelProviders.of(getActivity()).get(MineViewModel.class);
        getLifecycle().addObserver(mMineViewModel);
        mMineViewModel.list().observe(this, data -> {
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
            mMineViewModel.refresh();
        }
    }


    void loadData() {
        mMineViewModel.loadData();
    }
}
