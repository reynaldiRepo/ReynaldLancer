package com.reynaldlancer.reynaldlancer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaqSaldoFragment extends Fragment {


    public FaqSaldoFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_faq_saldo, container, false);
        LinearLayout headFaq[] = {v.findViewById(R.id.faq1), v.findViewById(R.id.faq2), v.findViewById(R.id.faq3), v.findViewById(R.id.faq4), v.findViewById(R.id.faq5), v.findViewById(R.id.faq6)};
        TextView contentFaq[] = {v.findViewById(R.id.faq_content_1), v.findViewById(R.id.faq_content_2), v.findViewById(R.id.faq_content_3), v.findViewById(R.id.faq_content_4), v.findViewById(R.id.faq_content_5), v.findViewById(R.id.faq_content_6)};
        ImageView iconFaq[] = {v.findViewById(R.id.faq_icon_1), v.findViewById(R.id.faq_icon_2), v.findViewById(R.id.faq_icon_3), v.findViewById(R.id.faq_icon_4), v.findViewById(R.id.faq_icon_5), v.findViewById(R.id.faq_icon_6)};
        FaqItem faqItemArr[] = new FaqItem[6];
        for (int i = 0; i < headFaq.length ; i++){
            FaqItem tempfaqitem = new FaqItem(headFaq[i], contentFaq[i], iconFaq[i]);
            faqItemArr[i] = tempfaqitem;
        }
        return v;
    }

}
