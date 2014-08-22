package com.android4.fragment.push;

import roboguice.inject.InjectView;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android4.R;
import com.android4.activity.MainActivity;
import com.android4.fragment.BaseFragment;
import com.google.inject.Inject;

public class PushFragment extends BaseFragment {

    public static final String EXTRA_INTENT_PUSH_INFO = "push_info";

    @Inject
    private NotificationManager mNotificationManager;

    @InjectView(R.id.pushContent)
    private EditText mPushText;

    @InjectView(R.id.pushType)
    private EditText mPushType;

    @InjectView(R.id.pushButton)
    private Button mPushButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPushButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String mPushContent = mPushText.getText().toString();
                Notification notification = buildNotification(getString(R.string.app_name),
                        mPushContent, mPushContent);
                String codeString = mPushType.getText().toString();
                int requestCode = Integer.parseInt(codeString);
                notification.contentIntent = resultPendingIntent("pushinfo", requestCode);
                try {
                    mNotificationManager.notify(requestCode, notification);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.push;
    }

    public static Fragment newInstance() {
        return new PushFragment();
    }

    private Notification buildNotification(String title, String tickerText, String content) {
        Bitmap largeBitmap = BitmapFactory.decodeResource(mActivity.getResources(),
                R.drawable.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mActivity)
                .setContentTitle(title).setContentText(content).setSmallIcon(R.drawable.push_icon)
                .setLargeIcon(largeBitmap).setAutoCancel(true).setTicker(tickerText)
                .setLights(Color.RED, Color.GREEN, Color.YELLOW).setNumber(12);
        Notification notification = builder.build();
        return notification;
    }

    private PendingIntent resultPendingIntent(String pushInfo, int requestcode) {
        Intent intent = null;
        intent = new Intent(mActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction("push");
        intent.putExtra(EXTRA_INTENT_PUSH_INFO, pushInfo);
        return PendingIntent.getActivity(mActivity, requestcode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
