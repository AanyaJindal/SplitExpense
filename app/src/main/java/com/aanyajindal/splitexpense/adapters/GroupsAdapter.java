package com.aanyajindal.splitexpense.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aanyajindal.splitexpense.Models.Group;
import com.aanyajindal.splitexpense.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jatin on 1/11/16.
 */
public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupName;
        CircleImageView groupDp;

        public ViewHolder(View itemView) {
            super((itemView));
            groupName = (TextView) itemView.findViewById(R.id.tv_groups_list_item);
            groupDp = (CircleImageView) itemView.findViewById(R.id.iv_groups_list_item);
        }
    }

    private ArrayList<Group> mList;
    private Context mContext;

    public GroupsAdapter(ArrayList<Group> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(mContext);
        View groupView = li.inflate(R.layout.groups_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(groupView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Group group = mList.get(position);
        holder.groupName.setText(group.getName());
        Glide.with(mContext).load(group.getPic()).into(holder.groupDp);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
