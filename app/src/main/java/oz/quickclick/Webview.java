package oz.quickclick;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Webview extends android.support.v4.app.Fragment
{
    private WebView webview;
    private String URL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.webview, container, false);

        URL = getArguments().getString("URL");

        webview = (WebView) view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), getContext().getResources().getString(R.string.status), getContext().getResources().getString(R.string.page),true );

        webview.setWebViewClient(new WebViewClient()
        {
            // Burada kullanıcı uyarılıyor
            public void onReceivedError(WebView webview, int errorCode, String description, String failingUrl)
            {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.load), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageFinished(WebView webview, String url)
            {
                super.onPageFinished(webview, url);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

        try
        {
            if(URL.substring(0,3).equals("htt"))
                webview.loadUrl(URL);
            else
                webview.loadUrl("http://"+URL);
        }
        catch (Exception e)
        {}

        return view;
    }
}