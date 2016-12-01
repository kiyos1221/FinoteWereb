package com.a360ground.finotewereb.ViewHolders;

import android.view.View;
import android.widget.TextView;
import com.a360ground.finotewereb.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;


/**
 * Created by Kiyos Solomon on 11/26/2016.
 */

public class BealViewHolder extends GroupViewHolder{
    TextView bealName;
    TextView bealDate;
    TextView count;
    public BealViewHolder(View itemView) {
        super(itemView);
        bealName = (TextView) itemView.findViewById(R.id.bealName);
        bealDate = (TextView) itemView.findViewById(R.id.bealDate);
        count = (TextView) itemView.findViewById(R.id.count);
    }
    public void setBealName(ExpandableGroup beal){
        bealName.setText(beal.getTitle());
    }

    public void setBealDate(String bealDateStr) {
        bealDate.setText(bealDateStr);
    }

    public void setCount(String countStr) {
        count.setText(countStr);
    }
}
