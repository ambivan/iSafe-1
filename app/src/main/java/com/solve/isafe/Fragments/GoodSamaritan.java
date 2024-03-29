package com.solve.isafe.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.solve.isafe.R;

public class GoodSamaritan extends Fragment {


    View vg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        vg = inflater.inflate(R.layout.good_samaritan, container, false);

        WebView webView = vg.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("http://gsl.goodsamaritans.in/home/");

        return vg;
    }

}
