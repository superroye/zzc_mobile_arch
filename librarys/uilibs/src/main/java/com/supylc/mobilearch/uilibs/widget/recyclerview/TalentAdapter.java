package com.supylc.mobilearch.uilibs.widget.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supylc.mobilearch.uilibs.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roye on 2016/12/10.
 */

public class TalentAdapter extends RecyclerView.Adapter {

    String TAG = TalentAdapter.class.getSimpleName();

    protected List mItems = new ArrayList<>();

    private SparseArray<Class> holderClassesMap = new SparseArray<>();
    private final int MIN_MULTI_TYPE = 1 << 10; //1024

    private List<Class> dataClasses = new ArrayList<>();
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public TalentAdapter() {
        this(null);
    }

    public TalentAdapter(List items) {
        if (items != null) {
            mItems = items;
        }
    }

    public List getItems() {
        return mItems;
    }

    public void resetItems(List items) {
        resetItems(items, true);
    }

    public void resetItems(List items, boolean notify) {
        if (items != null) {
            mItems = items;
            if (notify) {
                notifyDataSetChanged();
            }
        }
    }

    public void removeItem(int position) {
        if (mItems != null && mItems.size() > position) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        Class holderClass = holderClassesMap.get(viewType);
        if (holderClass == null) {
            throw new RuntimeException("the viewType not found.");
        }

        Object holder = null;
        try {
            HolderRes holderRes = (HolderRes) holderClass.getAnnotation(HolderRes.class);
            int holderResId = holderRes.value();
            if(!TextUtils.isEmpty(holderRes.resName())){
                holderResId = Utils.getApp().getResources().getIdentifier(holderRes.resName(), "layout", Utils.getApp().getPackageName());
            }
            View root = inflater.inflate(holderResId, parent, false);

            holder = holderClass.getConstructor(View.class).newInstance(root);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (holder == null) {
            throw new RuntimeException("holder not found.");
        }
        return (TalentHolder) holder;
    }

    @Override
    public int getItemViewType(int position) {
        Object dataItem = getItem(position);
        if (dataItem == null) {
            throw new RuntimeException("this item data is null.");
        }
        int itemType;
        Class dataClass = dataItem.getClass();
        if (TalentItemType.class.isAssignableFrom(dataClass)) {
            int originalType = ((TalentItemType) dataItem).getItemType();
            if (originalType < 0) {
                throw new RuntimeException("itemType is not allow less than zero.");
            }
            itemType = getRealItemType(originalType);
        } else {
            itemType = dataClasses.indexOf(dataItem.getClass());
            if (itemType < 0) {
                throw new RuntimeException("this dataItem has no designated itemType.");
            }
        }
        return itemType;
    }

    private int getRealItemType(int originalType) {
        return originalType + MIN_MULTI_TYPE;
    }

    private Class getDataTypeClass(Class holderClass) {
        Class cls = null;
        try {
            ParameterizedType pt = ((ParameterizedType) holderClass.getGenericSuperclass());
            Type type = pt.getActualTypeArguments()[0];
            if (type instanceof ParameterizedType) {
                cls = (Class) ((ParameterizedType) type).getRawType();
            } else {
                cls = (Class) (type);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return cls;
    }

    public void registerHolder(Class<? extends TalentHolder> holderClass) {
        Class dataType = getDataTypeClass(holderClass);
        if (dataType == null) {
            throw new RuntimeException("holder DataType class not found.");
        }

        if (TalentItemType.class.isAssignableFrom(dataType)) {
            throw new RuntimeException("the TalentItemType Item should use method registerHolder(Class,int).");
        }

        int hasIndex = dataClasses.indexOf(dataType);
        if (hasIndex < 0) {
            dataClasses.add(dataType);
            holderClassesMap.put(dataClasses.size() - 1, holderClass);
        } else {
            if (hasIndex >= MIN_MULTI_TYPE) {
                throw new RuntimeException("registerHolder exceeding the " + MIN_MULTI_TYPE + " limit");
            }
            holderClassesMap.put(hasIndex, holderClass);
        }
    }

    /**
     * @param holderClass
     * @param originalType 此模式指定数据类型值绑定对应的holder，注意：需要数据item实现TalentItemType返回相应的类型值.
     */
    public void registerHolder(Class<? extends TalentHolder> holderClass, int originalType) {
        Class dataType = getDataTypeClass(holderClass);
        if (dataType == null) {
            throw new RuntimeException("holder DataType class not found.");
        }
        if (!TalentItemType.class.isAssignableFrom(dataType)) {
            throw new RuntimeException("the TalentItemType Item should use method registerHolder(Class).");
        }

        holderClassesMap.put(getRealItemType(originalType), holderClass);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TalentHolder) holder).bind(getItem(position));
        ((TalentHolder) holder).setOnItemClick(onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        if (payloads.isEmpty()) {
            ((TalentHolder) holder).bind(getItem(position));
            ((TalentHolder) holder).setOnItemClick(onItemClickListener);
        } else {
            ((TalentHolder) holder).onPayload(payloads.get(0));
        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof TalentHolder) {
            ((TalentHolder) holder).recycle();
        }
    }
}
