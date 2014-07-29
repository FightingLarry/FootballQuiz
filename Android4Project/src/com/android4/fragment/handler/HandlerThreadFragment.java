package com.android4.fragment.handler;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android4.R;
import com.android4.fragment.BaseFragment;

public class HandlerThreadFragment extends BaseFragment {

    private static final String TAG = "HandlerThreadFragment";

    private Handler mMainHandler, mChildHandler;

    @InjectView(R.id.tv)
    private TextView info;

    @InjectView(R.id.btn)
    private Button msgBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainview = inflater.inflate(R.layout.fragment_handler_thread, null);
        super.onCreateView(inflater, container, savedInstanceState);

        return mainview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMainHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Log.i(TAG, "Got an incoming message from the child thread - " + (String) msg.obj);
                // 接收子线程的消息
                info.setText((String) msg.obj);
            }

        };

        new ChildThread().start();

        msgBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mChildHandler != null) {

                    //发送消息给子线程
                    Message childMsg = mChildHandler.obtainMessage();
                    childMsg.obj = mMainHandler.getLooper().getThread().getName() + " says Hello";
                    mChildHandler.sendMessage(childMsg);

                    Log.i(TAG, "Send a message to the child thread - " + (String) childMsg.obj);

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "Stop looping the child thread's message queue");
        mChildHandler.getLooper().quit();
    }

    class ChildThread extends Thread {

        private static final String CHILD_TAG = "ChildThread";

        public void run() {
            this.setName("ChildThread");

            //初始化消息循环队列，需要在Handler创建之前
            Looper.prepare();

            mChildHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    Log.i(CHILD_TAG, "Got an incoming message from the main thread - "
                            + (String) msg.obj);

                    try {

                        //在子线程中可以做一些耗时的工作
                        sleep(100);

                        Message toMain = mMainHandler.obtainMessage();
                        toMain.obj = "This is " + this.getLooper().getThread().getName()
                                + ".  Did you send me \"" + (String) msg.obj + "\"?";

                        mMainHandler.sendMessage(toMain);

                        Log.i(CHILD_TAG, "Send a message to the main thread - "
                                + (String) toMain.obj);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            };

            Log.i(CHILD_TAG, "Child handler is bound to - "
                    + mChildHandler.getLooper().getThread().getName());

            //启动子线程消息循环队列
            Looper.loop();
        }
    }

    public static HandlerThreadFragment newInstance() {
        return new HandlerThreadFragment();
    }
}
