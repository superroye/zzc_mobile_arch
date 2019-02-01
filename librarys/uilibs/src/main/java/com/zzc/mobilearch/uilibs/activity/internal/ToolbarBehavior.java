package com.zzc.mobilearch.uilibs.activity.internal;

import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * @author Roye
 * @date 2019/1/31
 */
public interface ToolbarBehavior extends UIBehavior {

    public void setTitle(String title);
    public void hideNavigationIcon();
    public void setNavigationOnClickListener(View.OnClickListener listener);
    public void inflateMenu(int menu);
    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener listener);
}
