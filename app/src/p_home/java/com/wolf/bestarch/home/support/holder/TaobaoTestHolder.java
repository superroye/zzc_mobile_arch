package com.wolf.bestarch.home.support.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolf.bestarch.R;
import com.wolf.bestarch.home.repository.bean.TaobaoTest;
import com.supylc.mobilearch.uilibs.widget.recyclerview.HolderRes;
import com.supylc.mobilearch.uilibs.widget.recyclerview.TalentHolder;

/**
 * @author Roye
 * @date 2019/2/1
 */
@HolderRes(R.layout.item_home_1)
public class TaobaoTestHolder extends TalentHolder<TaobaoTest.TaobaoItem> {

    public TaobaoTestHolder(View itemView) {
        super(itemView);
    }

    private ImageView ivHotel;
    private View vDivider;
    private TextView titleTv;
    private TextView tvTitleEn;
    private TextView tvRate;
    private TextView tvLevel;
    private TextView tvPosition;
    private View vDivider1;
    private TextView tvPositionDesc;
    private RecyclerView rvLabel;
    private TextView tvLastestBook;
    private TextView tvRestHotel;
    private TextView tvPriceReal;
    private TextView tvPriceDiscount;
    private TextView tvPrice;

    @Override
    public void initView() {
        ivHotel = (ImageView) findV(R.id.iv_hotel);
        vDivider = findV(R.id.v_divider);
        titleTv = (TextView) findV(R.id.titleTv);
        tvTitleEn = (TextView) findV(R.id.tv_title_en);
        tvRate = (TextView) findV(R.id.tv_rate);
        tvLevel = (TextView) findV(R.id.tv_level);
        tvPosition = (TextView) findV(R.id.tv_position);
        vDivider1 = findV(R.id.v_divider1);
        tvPositionDesc = (TextView) findV(R.id.tv_position_desc);
        rvLabel = (RecyclerView) findV(R.id.rv_label);
        tvLastestBook = (TextView) findV(R.id.tv_lastest_book);
        tvRestHotel = (TextView) findV(R.id.tv_rest_hotel);
        tvPriceReal = (TextView) findV(R.id.tv_price_real);
        tvPriceDiscount = (TextView) findV(R.id.tv_price_discount);
        tvPrice = (TextView) findV(R.id.tv_price);
    }

    @Override
    public void toView() {
       // Glide.with(itemView).load(itemValue.get(0)).into(ivHotel);
        titleTv.setText(itemValue.get(0));
    }
}
