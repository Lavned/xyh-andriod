package com.zejor.mvp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.zejor.R;
import com.zejor.base.BaseActivity;
import com.zejor.component.ApplicationComponent;
import com.zejor.contants.ApiService;
import com.zejor.retrofit.RetrofitFactory;
import com.zejor.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PdfActivity extends BaseActivity {


    private static final String TAG = PdfActivity.class.getSimpleName();
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pdfViews)
    PDFView pdfViews;


    private String title = "";
    private String webViewURL = "";


    private void downFile() {
        RetrofitFactory
                .getInstance()
                .getRetrofit()
                .create(ApiService.class)
                .downFile(webViewURL)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        int code = response.code();
                        if (200 == code) {
                            writeResponseBodyToDisk(response.body());
                        } else {
                            ToastUtils.showToast(PdfActivity.this, getResources().getString(R.string.netErrText));
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        ToastUtils.showToast(PdfActivity.this, getResources().getString(R.string.netErrText));
                    }
                });
    }


    /**
     * 最垃圾的下载文件方式
     *
     * @param body 响应体，从中获取下载到的问加你
     * @return 是否下载成功
     */
    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();
                downFileSuccess(futureStudioIconFile);
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


    private void downFileSuccess(File file) {
        if (null != pdfViews) {
            pdfViews.fromFile(file)
                    .pages(0, 1, 2, 3, 4)
                    .defaultPage(1)
//                    .showMinimap(false)
                    .enableSwipe(true)
                    .load();
        }

    }

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_pdf;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        title = getIntent().getExtras().getString("title");
        webViewURL = getIntent().getExtras().getString("url");

        tvTitle.setText(title);
        if (webViewURL.endsWith(".pdf")) {
//            HttpUtils.getInstance()
//                    .downFile(webViewURL,"xy.pdf",this);

            //下载文件
            downFile();
        }
    }

    @Override
    public void initData() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
