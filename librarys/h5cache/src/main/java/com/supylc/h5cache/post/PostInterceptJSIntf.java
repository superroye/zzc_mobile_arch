package com.supylc.h5cache.post;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostInterceptJSIntf {

    public static final String TAG = "PostIntercept";

    private static String mInterceptHeader = null;
    private PostInterceptJSIntf.FormRequestContents mNextFormRequestContents = null;
    private PostInterceptJSIntf.AjaxRequestContents mNextAjaxRequestContents = null;

    public PostInterceptJSIntf() {
    }

    public static String enableIntercept(Context context, byte[] data) throws IOException {
        if (mInterceptHeader == null) {
            mInterceptHeader = new String(IOUtils.readFully(context.getAssets().open(
                    "www/interceptheader.html")));
        }

        org.jsoup.nodes.Document doc = Jsoup.parse(new String(data));
        doc.outputSettings().prettyPrint(true);

        // Prefix every script to capture submits
        // Make sure our interception is the first element in the
        // header
        org.jsoup.select.Elements el = doc.getElementsByTag("head");
        if (el.size() > 0) {
            el.get(0).prepend(mInterceptHeader);
        }

        String pageContents = doc.toString();
        return pageContents;
    }

    public class FormRequestContents {
        public String method = null;
        public String json = null;
        public String enctype = null;

        public FormRequestContents(String method, String json, String enctype) {
            this.method = method;
            this.json = json;
            this.enctype = enctype;
        }
    }

    public class AjaxRequestContents {
        public String method = null;
        public String body = null;

        public AjaxRequestContents(String method, String body) {
            this.method = method;
            this.body = body;
        }
    }

    @JavascriptInterface
    public void customAjax(final String method, final String body) {
        Log.i(TAG, "Submit data: " + method + " " + body);
        mNextAjaxRequestContents = new AjaxRequestContents(method, body);
    }

    @JavascriptInterface
    public void customSubmit(String json, String method, String enctype) {
        Log.i(TAG, "Submit data: " + json + "\t" + method + "\t" + enctype);
        mNextFormRequestContents = new FormRequestContents(method, json, enctype);
    }

    public AjaxRequestContents getAjaxRequestContents() {
        return mNextAjaxRequestContents;
    }

    public FormRequestContents getFormRequestContents() {
        return mNextFormRequestContents;
    }

    public String getContentFeature() {
        if (true) {
            return "";
        }
        String feature = "";
        String divider = "&__";
        if (mNextAjaxRequestContents != null && mNextAjaxRequestContents.body != null) {
            feature = divider + mNextAjaxRequestContents.body;
        } else if (mNextFormRequestContents != null && mNextFormRequestContents.json != null) {
            feature = divider + mNextFormRequestContents.json;
        }
        return feature;
    }

    public void setPost(Request.Builder requestBuilder) {
        RequestBody formBody = null;
        if (mNextAjaxRequestContents != null) {
            if (mNextAjaxRequestContents.body != null) {
                formBody = FormBody.create(MediaType.parse("application/x-www-form-urlencoded"), mNextAjaxRequestContents.body);
            }
        } else if (mNextFormRequestContents != null) {
            try {
                JSONArray jsonPars = new JSONArray(mNextFormRequestContents.json);
                FormBody.Builder m = new FormBody.Builder();
                for (int i = 0; i < jsonPars.length(); i++) {
                    JSONObject jsonPar = jsonPars.getJSONObject(i);
                    m.add(jsonPar.getString("name"), jsonPar.getString("value"));
                }
                formBody = m.build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //默认
        if (formBody == null) {
            formBody = FormBody.create(MediaType.parse("application/x-www-form-urlencoded"), "");
        }
        requestBuilder.post(formBody);
    }

    public void resetRequestContents() {
        mNextAjaxRequestContents = null;
        mNextFormRequestContents = null;
    }

    public boolean isPost() {
        return mNextAjaxRequestContents != null || mNextFormRequestContents != null;
    }
}
