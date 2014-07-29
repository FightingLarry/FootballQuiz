package com.android4.fragment.installapk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android4.R;
import com.android4.fragment.BaseFragment;

public class InstallApkFragment extends BaseFragment implements OnClickListener {

    private final static String TAG = "InstallApkFragment";

    private Context mContext;

    private Button btnInstall;

    private Button btnInstallWuya;

    private final String mPapaApkAssetsPath = "apk/";

    private String mPapaSdcardpath;

    private final String mPapaApkName = "helios-android.apk";

    private final String mWuyaApkName = "wuya-android.apk";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CopyAssetsToSDCard().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_installapk, null);
        btnInstall = (Button) view.findViewById(R.id.btnInstall);
        btnInstall.setOnClickListener(this);
        btnInstallWuya = (Button) view.findViewById(R.id.btnInstallWuya);
        btnInstallWuya.setOnClickListener(this);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInstall:
                installApk(mPapaSdcardpath + File.separator + mPapaApkName);
                break;
            case R.id.btnInstallWuya:
                installApk(mPapaSdcardpath + File.separator + mWuyaApkName);
                break;

            default:
                break;
        }
    }

    private void installApk(String apk) {
        Uri papa = Uri.fromFile(new File(apk));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(papa, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private class CopyAssetsToSDCard extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(mContext, "开始复制apk到sdk", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean success = true;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                mPapaSdcardpath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/Papa";
                File filePath = new File(mPapaSdcardpath);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                File file = new File(mPapaSdcardpath + File.separator + mPapaApkName);
                if (!file.exists()) {
                    success = copyAssets(mPapaApkName);
                }
                File fileWuya = new File(mPapaSdcardpath + File.separator + mWuyaApkName);
                if (!fileWuya.exists()) {
                    success = copyAssets(mWuyaApkName);
                }
            } else {
                success = false;
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Toast.makeText(mContext, "复制完毕", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "复制出错", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean copyAssets(String apkName) {
        AssetManager assetManager = mContext.getAssets();
        InputStream in = null;
        OutputStream out = null;
        boolean success = true;
        try {
            in = assetManager.open(mPapaApkAssetsPath + apkName);
            File outFile = new File(mPapaSdcardpath, apkName);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (IOException e) {
            success = false;
            Log.e("tag", "Failed to copy asset file: " + apkName, e);
        }

        return success;
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

}
