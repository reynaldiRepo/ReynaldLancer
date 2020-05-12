package com.reynaldlancer.reynaldlancer;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public  class FaqItem {
    LinearLayout container;
    TextView content;

    public FaqItem(LinearLayout container, TextView content, ImageView icon) {
        this.container = container;
        this.content = content;
        final TextView con = this.content;
        final ImageView iconFaq = icon;
        this.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (con.getVisibility() == View.GONE ){
                    con.setVisibility(View.VISIBLE);
                    iconFaq.setImageResource(R.drawable.expand_less);
                }else{
                    con.setVisibility(View.GONE);
                    iconFaq.setImageResource(R.drawable.expand_more);

                }
            }
        });
    }
}
