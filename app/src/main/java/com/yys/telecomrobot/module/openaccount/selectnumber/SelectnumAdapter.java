package com.yys.telecomrobot.module.openaccount.selectnumber;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yys.telecomrobot.R;
import com.yys.telecomrobot.base.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.yys.telecomrobot.model.SelectnumInfo;
import com.yys.telecomrobot.utils.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yltang3 on 2017/11/20.
 */

public class SelectnumAdapter extends RecyclerView.Adapter<SelectnumAdapter.SelectnumHolder> implements View.OnClickListener {

    //是否为Adapter设置条目点击事件
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    /** 获取当前条目的数据 */
    public SelectnumInfo getItemData(int position) {
        return mData.get(position);
    }

    /**
     * 替换RecyclerView数据
     */
    public void replaceList(List<SelectnumInfo> list) {
        if (list != null){
            this.mData = list;
        } else {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    List<SelectnumInfo> mData;  // 适配器数据
    Context mContext;

    public SelectnumAdapter(Context context, List<SelectnumInfo> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public SelectnumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_openacc_selectnum, parent, false);
        view.setOnClickListener(this);
        return new SelectnumHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectnumHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    public class SelectnumHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_phone) TextView mPhoneTv;

        public SelectnumHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /** 设置当前数据 */
        public void setData(int position) {
            SelectnumInfo temp = mData.get(position);
            if(temp.isChoose()) {
                mPhoneTv.setTextColor(UiUtils.getColor(R.color.phone_check));
                mPhoneTv.setBackgroundResource(R.drawable.shape_phonebg_check);
            } else {
                mPhoneTv.setTextColor(UiUtils.getColor(R.color.phone_uncheck));
                mPhoneTv.setBackgroundResource(R.drawable.shape_phonebg_uncheck);
            }
            mPhoneTv.setText(temp.getPhone());
        }
    }
}
