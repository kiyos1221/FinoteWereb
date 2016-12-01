package com.a360ground.finotewereb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.a360ground.finotewereb.Date.EthiopianCalendar;
import com.a360ground.finotewereb.ViewHolders.BealViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Kiyos Solomon on 11/26/2016.
 */

public class BealRecyclerAdapter extends ExpandableRecyclerViewAdapter<BealViewHolder,WerebViewHolder>{
    private Context mContext;
    private String[] bealDates = {"ጥቅምት 14 ይከበራል","ኅዳር 12 ይከበራል","ኅዳር 21 ይከበራል","ጥር 11 ይከበራል","የካቲት 16 ይከበራል","የ ዘንድሮ ግንቦት 25 ይከበራል","ሰኔ 12 ይከበራል","ነሐሴ 16 ይከበራል"};
    EthiopianCalendar ethiopianCalendar = new EthiopianCalendar();
    BealRecyclerAdapter(List<? extends ExpandableGroup> groups, Context mContext) {
        super(groups);
        this.mContext = mContext;
        ethiopianCalendar.toEthiopicDate(Calendar.getInstance());
        bealDates[5] =  "የዘንድሮ "+ethiopianCalendar.getMonthFormat(ethiopianCalendar.getPeraklitos(ethiopianCalendar.toEthiopicDate(Calendar.getInstance())[1]),ethiopianCalendar.toEthiopicDate(Calendar.getInstance())[1])+" ይከበራል";
    }

    @Override
    public BealViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_group_row,parent,false);
        return new BealViewHolder(view);
    }

    @Override
    public WerebViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_child_row,parent,false);
        return new WerebViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(WerebViewHolder holder, final int flatPosition, ExpandableGroup group, final int childIndex) {
        final Wereb wereb = (Wereb) group.getItems().get(childIndex);
        holder.setWerebName(wereb.getWerebName());
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PlayerActivity.class);
                intent.putExtra("TITLE",wereb.getWerebGeez());
                intent.putExtra("TIRGUM",wereb.getWerebAmharic());
               mContext.startActivity(intent);
            }
        });
    }
    @Override
    public void onBindGroupViewHolder(BealViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setBealName(group);
        holder.setBealDate(bealDates[flatPosition]);
        holder.setCount(group.getItems().size()+" ወረብ");
    }
}
class WerebViewHolder extends ChildViewHolder {
    TextView werebName;
    ImageButton play;
    public WerebViewHolder(View itemView) {
        super(itemView);
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "font/ebrima.ttf");
        werebName = (TextView) itemView.findViewById(R.id.werebName);
        werebName.setTypeface(typeface);
        play = (ImageButton) itemView.findViewById(R.id.playBtn);
    }
    public void setWerebName(String name){
        werebName.setText(name);
    }
}
