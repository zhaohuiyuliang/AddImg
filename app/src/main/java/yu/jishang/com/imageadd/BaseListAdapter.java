package yu.jishang.com.imageadd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangliang on 2016/11/3.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter implements ViewHolder.Callback {
    protected LayoutInflater mInflater;
    protected Callback mCallback;
    private List<T> mDatas;
    private List<T> mPreData;

    public BaseListAdapter(Callback callback) {
        this.mCallback = callback;
        this.mInflater = LayoutInflater.from(callback.getContext());
        this.mDatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= 0 && position < mDatas.size())
            return mDatas.get(position);
        return null;
    }

    protected abstract void convert(ViewHolder vh, T item, int position);

    protected abstract int getLayoutId(int position, T item);

    public List<T> getDatas() {
        return this.mDatas;
    }

    public void updateItem(int location, T item) {
        if (mDatas.isEmpty()) return;
        mDatas.set(location, item);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        checkListNull();
        mDatas.add(item);
        notifyDataSetChanged();
    }


    public void checkListNull() {
        if (mDatas == null) {
            mDatas = new ArrayList<T>();
        }
    }

    public void clear() {
        if (mDatas == null || mDatas.isEmpty()) {
            return;
        }
        mPreData = null;
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void setData(List<T> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void removeItem(int location) {
        if (mDatas == null || mDatas.isEmpty()) {
            return;
        }
        mDatas.remove(location);
        notifyDataSetChanged();
    }

    public void addItem(List<T> items) {
        checkListNull();
        if (items != null) {
            List<T> date = new ArrayList<>();
            if (mPreData != null) {
                for (T d : items) {
                    if (!mPreData.contains(d)) {
                        date.add(d);
                    }
                }
            } else {
                date = items;
            }
            mPreData = items;
            mDatas.addAll(date);
        }
        notifyDataSetChanged();
    }

    public int getCurrentPage() {
        return getCount() % 20;
    }


    @Override
    public LayoutInflater getInflate() {
        return mInflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T time = getItem(position);
        int layoutId = getLayoutId(position, time);
        final ViewHolder vh = ViewHolder.getViewHolder(this, convertView, parent, layoutId, position);
        convert(vh, time, position);
        return vh.getConvertView();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface Callback {
        Context getContext();

        Date getSystemTime();
    }

}
