package com.example.url;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

// step 1 is you need to implement the DownloadFile.Listener interface.
public class pdfAvtivity extends AppCompatActivity implements DownloadFile.Listener {

    private RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter pdfPagerAdapter;
    private String url;
    private  ProgressBar progressBar;
    private LinearLayout pdfLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_avtivity);
        //set the Visibility of the progressbar to visible
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //initialize the pdfLayout
        pdfLayout = findViewById(R.id.pdf_layout);
        //initialize the url variable
        url = getString(R.string.url);
        //Create a RemotePDFViewPager object
        remotePDFViewPager = new RemotePDFViewPager(this, url, this);


    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        // Now back to the onSuccess method where we need to implement our logic if the pdf download went fine.
        // Here we will initialize the pdfPagerAdapter, setting the adapter to the remotePDFViewPager, updating the layout ,and then hiding the progressBar when all is done.
        pdfPagerAdapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(pdfPagerAdapter);
        updateLayout();
        progressBar.setVisibility(View.GONE);


    }

    private void updateLayout() {
        pdfLayout.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    }


    @Override
    public void onFailure(Exception e) {
        // This will be called if download fails

    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        // You will get download progress here
        // Always on UI Thread so feel free to update your views here
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (pdfPagerAdapter != null) {
            pdfPagerAdapter.close();
        }
    }

}
