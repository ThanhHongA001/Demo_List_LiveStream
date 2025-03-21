package com.viettel.mocha.rmlivestream.player.info_live.reaction.reactionamination;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.ref.WeakReference;


public class OverTheTopLayer {

    public static class OverTheTopLayerException extends RuntimeException {
        public OverTheTopLayerException(String msg) {
            super(msg);
        }
    }

    private WeakReference<Activity> mWeakActivity;
    private WeakReference<ViewGroup> mWeakRootView;
    private FrameLayout mCreatedOttLayer;
    private float mScalingFactor = 1.0f;
    private int[] mDrawLocation = {0, 0};
    private Bitmap mBitmap;


    public OverTheTopLayer() {
    }

    /**
     * To create a layer on the top of activity

     */
    public OverTheTopLayer with(Activity weakReferenceActivity) {
        mWeakActivity = new WeakReference<Activity>(weakReferenceActivity);
        return this;
    }

    /**
     * Draws the image as per the drawable resource id on the given location pixels.

     */
    public OverTheTopLayer generateBitmap(Resources resources, int drawableResId, float mScalingFactor, int[] location) {

        if (location == null) {
            location = new int[]{0, 0};
        } else if (location.length != 2) {
            throw new OverTheTopLayerException("Requires location as an array of length 2 - [x,y]");
        }

        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableResId);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * mScalingFactor), (int) (bitmap.getHeight() * mScalingFactor), false);

        this.mBitmap = scaledBitmap;

        this.mDrawLocation = location;
        return this;
    }

    public OverTheTopLayer setBitmap(Bitmap bitmap, int[] location) {

        if (location == null) {
            location = new int[]{0, 0};
        } else if (location.length != 2) {
            throw new OverTheTopLayerException("Requires location as an array of length 2 - [x,y]");
        }

        this.mBitmap = bitmap;
        this.mDrawLocation = location;
        return this;
    }


    /**
     * Holds the scaling factor for the image.
     *
     * @param scale
     * @return
     */
    public OverTheTopLayer scale(float scale) {

        if (scale <= 0) {
            throw new OverTheTopLayerException("Scaling should be > 0");

        }
        this.mScalingFactor = scale;


        return this;
    }

    /**
     * Attach the OTT layer as the child of the given root view.
     * @return
     */
    public OverTheTopLayer attachTo(ViewGroup rootView) {
        this.mWeakRootView = new WeakReference<ViewGroup>(rootView);
        return this;
    }

    /**
     * Creates an OTT.
     * @return
     */
    public FrameLayout create() {


        if(mCreatedOttLayer != null) {
            destroy();
        }

        if (mWeakActivity == null) {
            throw new OverTheTopLayerException("Could not create the layer as not activity reference was provided.");
        }

        Activity activity = mWeakActivity.get();

        if (activity != null) {
            ViewGroup attachingView = null;


            if (mWeakRootView != null && mWeakRootView.get() != null) {
                attachingView = mWeakRootView.get();
            } else {
                attachingView = (ViewGroup) activity.findViewById(android.R.id.content);
            }


            ImageView imageView = new ImageView(activity);

            imageView.setImageBitmap(mBitmap);


            int minWidth = mBitmap.getWidth();
            int minHeight = mBitmap.getHeight();

            imageView.measure(View.MeasureSpec.makeMeasureSpec(minWidth, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(minHeight, View.MeasureSpec.AT_MOST));

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageView.getLayoutParams();

            if (params == null) {
                params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
                imageView.setLayoutParams(params);
            }

            int xPosition = mDrawLocation[0];
            int yPosition = mDrawLocation[1];

            params.width = minWidth;
            params.height = minHeight;

            params.leftMargin = xPosition;
            params.topMargin = yPosition;

            imageView.setLayoutParams(params);

            FrameLayout ottLayer = new FrameLayout(activity);
            FrameLayout.LayoutParams topLayerParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.TOP);

            ottLayer.setLayoutParams(topLayerParam);

            ottLayer.addView(imageView);

            attachingView.addView(ottLayer);

            mCreatedOttLayer = ottLayer;


        } else {
            Log.e(OverTheTopLayer.class.getSimpleName(), "Could not create the layer. Reference to the activity was lost");
        }

        return mCreatedOttLayer;

    }

    /**
     * Kills the OTT
     */
    public void destroy() {


        if (mWeakActivity == null) {
            throw new OverTheTopLayerException("Could not create the layer as not activity reference was provided.");
        }

        Activity activity = mWeakActivity.get();

        if (activity != null) {
            ViewGroup attachingView = null;


            if (mWeakRootView != null && mWeakRootView.get() != null) {
                attachingView = mWeakRootView.get();
            } else {
                attachingView = (ViewGroup) activity.findViewById(android.R.id.content);
            }

            if (mCreatedOttLayer != null) {

                attachingView.removeView(mCreatedOttLayer);
                mCreatedOttLayer = null;
            }


        } else {

            Log.e(OverTheTopLayer.class.getSimpleName(), "Could not destroy the layer as the layer was never created.");

        }

    }

    /**
     * Applies the animation to the image view present in OTT.
     * @param animation
     */
    public void applyAnimation(Animation animation) {

        if(mCreatedOttLayer != null) {
            ImageView drawnImageView = (ImageView) mCreatedOttLayer.getChildAt(0);
            drawnImageView.startAnimation(animation);
            new Handler().postDelayed(() -> {
                drawnImageView.animate().alpha(0f).setDuration(1500);
                drawnImageView.animate().scaleX(0f).setDuration(1500);
                drawnImageView.animate().scaleY(0f).setDuration(1500);
            }, 1500);
        }
    }

    public void applyContinueAnimation(Animation animation) {

        if(mCreatedOttLayer != null) {
            ImageView drawnImageView = (ImageView) mCreatedOttLayer.getChildAt(0);
            drawnImageView.startAnimation(animation);
            new Handler().postDelayed(() -> {
                drawnImageView.animate().alpha(0f).setDuration(1000);
                drawnImageView.animate().scaleX(0f).setDuration(1000);
                drawnImageView.animate().scaleY(0f).setDuration(1000);
            }, 1000);
        }
    }

    public void applyPopAnimation(Animation animation) {
        if(mCreatedOttLayer != null) {
            ImageView drawnImageView = (ImageView) mCreatedOttLayer.getChildAt(0);
            drawnImageView.startAnimation(animation);
            new Handler().postDelayed(() -> {
                drawnImageView.animate().alpha(0f).setDuration(200);
                drawnImageView.animate().scaleX(1.5f).setDuration(200);
                drawnImageView.animate().scaleY(1.5f).setDuration(200);
            }, 800);
        }
    }
}