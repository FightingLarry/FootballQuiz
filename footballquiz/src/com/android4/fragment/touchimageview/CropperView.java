package com.android4.fragment.touchimageview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android4.R;

public class CropperView extends View {

    private static final String TAG = "TouchImageView";

    private Context mContext;

    private float x_down = 0;

    private float y_down = 0;

    private PointF start = new PointF();

    private PointF mid = new PointF();

    private float oldDist = 1f;

    private float oldRotation = 0;

    /** ��ת�˶��ٽǶ� */
    private float moveRotation;

    /** ����Ҫ��ʾ�ĽǶ� */
    private float targetRotation = 90;

    /** ʵ��ͼƬ��x��ĽǶ� */
    private float imageRotation;

    private Matrix matrix = new Matrix();

    private Matrix matrixTemp = new Matrix();

    private Matrix savedMatrix = new Matrix();

    private static final int NONE = 0;

    private static final int DRAG = 1;

    private static final int ZOOM = 2;

    private int mode = NONE;

    private boolean matrixCheck = false;

    private int widthScreen;

    private int heightScreen;

    private Bitmap gintama;

    private float[] mCropperLinePoints;

    private Paint mPaint;

    private int mDownCount = 1;

    public CropperView(Context context) {
        super(context);
        this.mContext = context;
        gintama = BitmapFactory.decodeResource(getResources(),
                R.drawable.b3c54b9d37b86702714f5bd6b4bd70fa);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;
        getCropperLinePoints();
        matrix = new Matrix();

    }

    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(gintama, matrix, null);
        drawCropperLine(canvas);
        canvas.restore();
    }

    private Paint getPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(mContext.getResources().getColor(android.R.color.background_dark));
            mPaint.setStrokeWidth(2);
        }
        return mPaint;
    }

    private void drawCropperLine(Canvas canvas) {
        canvas.drawLines(mCropperLinePoints, getPaint());
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = DRAG;
                x_down = event.getX();
                y_down = event.getY();
                savedMatrix.set(matrix);
                mDownCount = 1;
                Log.e(TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                oldDist = spacing(event);
                oldRotation = rotation(event);
                savedMatrix.set(matrix);
                midPoint(mid, event);
                mDownCount++;
                Log.e(TAG, "ACTION_POINTER_DOWN");
                Log.d(TAG, "mDownCount=" + mDownCount);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == ZOOM) {
                    matrixTemp.set(savedMatrix);
                    float rotation = rotation(event);
                    moveRotation = rotation - oldRotation;
                    imageRotation = moveRotation + targetRotation;
                    Log.w(TAG, "moveRotation=" + moveRotation + " ,rotation(event)=" + rotation
                            + " ,oldRotation=" + oldRotation);
                    float newDist = spacing(event);
                    float scale = newDist / oldDist;
                    matrixTemp.postScale(scale, scale, mid.x, mid.y);// �s��
                    matrixTemp.postRotate(moveRotation, mid.x, mid.y);// ���D
                    //                    matrixCheck = matrixCheck();
                    //                    if (matrixCheck == false) {
                    matrix.set(matrixTemp);
                    invalidate();
                    //                    }
                } else if (mode == DRAG) {
                    matrixTemp.set(savedMatrix);
                    matrixTemp.postTranslate(event.getX() - x_down, event.getY() - y_down);// ƽ��
                    //                    matrixCheck = matrixCheck();
                    //                    if (matrixCheck == false) {
                    matrix.set(matrixTemp);
                    invalidate();
                    //                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP");
                mode = NONE;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.e(TAG, "ACTION_POINTER_UP");
                mode = NONE;
                if (mDownCount <= 2) {
                    matrix.postRotate(clacAdjustRotation(), mid.x, mid.y);// ���D
                    invalidate();
                }
                mDownCount--;
                Log.d(TAG, "mDownCount=" + mDownCount);
                break;
        }
        return true;
    }

    private float clacAdjustRotation() {
        imageRotation = imageRotation % 360f;
        if (imageRotation < 0) {
            imageRotation += 360;
        }
        if (imageRotation >= 0 && imageRotation < 45) {
            targetRotation = 0;
        } else if (imageRotation >= 45 && imageRotation < 135) {
            targetRotation = 90;
        } else if (imageRotation >= 135 && imageRotation < 225) {
            targetRotation = 180;
        } else if (imageRotation >= 225 && imageRotation < 315) {
            targetRotation = 270;
        } else if (imageRotation >= 315 && imageRotation < 360) {
            targetRotation = 360;
        }
        Log.i(TAG, "ʵ�ʼн�=" + targetRotation + " ,��תǰ=" + imageRotation + " ,������="
                + (targetRotation - imageRotation));
        return targetRotation - imageRotation;

    }

    private boolean matrixCheck() {
        float[] f = new float[9];
        matrixTemp.getValues(f);
        // ͼƬ4�����������
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
        float x2 = f[0] * gintama.getWidth() + f[1] * 0 + f[2];
        float y2 = f[3] * gintama.getWidth() + f[4] * 0 + f[5];
        float x3 = f[0] * 0 + f[1] * gintama.getHeight() + f[2];
        float y3 = f[3] * 0 + f[4] * gintama.getHeight() + f[5];
        float x4 = f[0] * gintama.getWidth() + f[1] * gintama.getHeight() + f[2];
        float y4 = f[3] * gintama.getWidth() + f[4] * gintama.getHeight() + f[5];
        // ͼƬ�ֿ��
        double width = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        // ���ű����ж�
        if (width < widthScreen / 3 || width > widthScreen * 3) {
            return true;
        }
        // �����ж�
        if ((x1 < widthScreen / 3 && x2 < widthScreen / 3 && x3 < widthScreen / 3 && x4 < widthScreen / 3)
                || (x1 > widthScreen * 2 / 3 && x2 > widthScreen * 2 / 3
                        && x3 > widthScreen * 2 / 3 && x4 > widthScreen * 2 / 3)
                || (y1 < heightScreen / 3 && y2 < heightScreen / 3 && y3 < heightScreen / 3 && y4 < heightScreen / 3)
                || (y1 > heightScreen * 2 / 3 && y2 > heightScreen * 2 / 3
                        && y3 > heightScreen * 2 / 3 && y4 > heightScreen * 2 / 3)) {
            return true;
        }
        return false;
    }

    // ������������
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    // ȡ�������ĵ�
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    // ȡ��ת�Ƕȣ��ִ������������x��ļн�
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    // ���ƶ��������Լ���ת���ͼ�㱣��Ϊ��ͼƬ
    // �����Л]���õ�ԓ��������Ҫ����DƬ�Ŀ��ԅ���
    public Bitmap creatNewPhoto() {
        Bitmap bitmap = Bitmap.createBitmap(widthScreen, widthScreen, Config.ARGB_8888); // ����ͼƬ
        Canvas canvas = new Canvas(bitmap); // �½�����
        canvas.drawBitmap(gintama, matrix, null); // ��ͼƬ
        canvas.save(Canvas.ALL_SAVE_FLAG); // ���滭��
        canvas.restore();
        return bitmap;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setImageViewHeight(getHeight());
    }

    public void setImageViewHeight(int mImageViewHeight) {
        this.heightScreen = mImageViewHeight;
        getCropperLinePoints();
        invalidate();
    }

    private void getCropperLinePoints() {
        int mCropperLineFirstY = (heightScreen - widthScreen) / 2;
        mCropperLinePoints = new float[] { 0, mCropperLineFirstY, widthScreen, mCropperLineFirstY,
                0, mCropperLineFirstY + widthScreen, widthScreen, mCropperLineFirstY + widthScreen };
    }
}
