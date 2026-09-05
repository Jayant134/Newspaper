package com.example.newspaper.clickHandlers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.newspaper.R;

public class newsDetails extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = (getArguments() != null) ? getArguments().getString("url") : null;
        if(url != null){
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }else{
            Toast.makeText(requireContext(), "News article not found!", Toast.LENGTH_SHORT).show();
        }
    }

    //loaded this fragment from NewsItemClickHandler. now write logic to show details in a page or open url
}