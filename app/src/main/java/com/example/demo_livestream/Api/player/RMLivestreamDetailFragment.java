package com.viettel.mocha.rmlivestream.player;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer114.Player;
import com.viettel.mocha.activity.BaseSlidingFragmentActivity;
import com.viettel.mocha.app.R;
import com.viettel.mocha.app.databinding.RmFragmentLivestreamDetailBinding;
import com.viettel.mocha.common.utils.ScreenManager;
import com.viettel.mocha.common.utils.image.ImageManager;
import com.viettel.mocha.common.utils.player.MochaPlayer;
import com.viettel.mocha.common.utils.player.MochaPlayerUtil;
import com.viettel.mocha.model.tab_video.Resolution;
import com.viettel.mocha.model.tab_video.Video;
import com.viettel.mocha.rmlivestream.RMConstants;
import com.viettel.mocha.rmlivestream.RMLivestreamActivity;
import com.viettel.mocha.rmlivestream.channel.event_bus.FollowInChannel;
import com.viettel.mocha.rmlivestream.model.LiveStreamViewNumber;
import com.viettel.mocha.rmlivestream.model.LivestreamLiveNotification;
import com.viettel.mocha.rmlivestream.model.RMLivestream;
import com.viettel.mocha.rmlivestream.player.info_live.RMInfoLiveFragment;
import com.viettel.mocha.rmlivestream.player.info_upcoming.RMInfoUpcomingFragment;
import com.viettel.mocha.rmlivestream.player.info_vod.RMInfoVODFragment;
import com.viettel.mocha.ui.tabvideo.playVideo.dialog.FullPlayerDialog;
import com.viettel.mocha.ui.tabvideo.playVideo.dialog.QualityVideoDialog;
import com.viettel.mocha.ui.view.tab_video.VideoPlaybackControlView;
import com.viettel.mocha.util.Log;
import com.viettel.mocha.util.Utilities;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RMLivestreamDetailFragment extends Fragment {
    public static RMLivestreamDetailFragment newInstance(String idVideo, Boolean fromVTM) {
        RMLivestreamDetailFragment fragment = new RMLivestreamDetailFragment();
        fragment.fromVTM=fromVTM;
        fragment.setIdVideo(idVideo);
        return fragment;
    }

    private Boolean fromVTM=false;
    private RmFragmentLivestreamDetailBinding binding;
    private RMLivestreamDetailViewModel viewModel;
    private MochaPlayer mPlayer;
    private String idVideo;
    private RMLivestream currentVideo;
    private BaseSlidingFragmentActivity mActivity;
    protected boolean isFullScreen = false;
    private Player.EventListener eventListener = new Player.DefaultEventListener() {
        private int currentState;
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (!isFullScreen && currentState != Player.STATE_ENDED && playbackState == Player.STATE_ENDED) {
//                handlerEnd();
            }
//            hanlderKeepScreenOn();
            currentState = playbackState;
        }
    };
    private VideoPlaybackControlView.CallBackListener callBackListener = new VideoPlaybackControlView.DefaultCallbackListener() {

        @Override
        public void onFullScreen() {
            handlerFullScreen();
        }

        @Override
        public void onSmallScreen() {
            super.onSmallScreen();
            requireActivity().onBackPressed();
        }

        @Override
        public void onPlayPause(boolean state) {
            hanlderKeepScreenOn();
        }

        @Override
        public void onHaveSeek(boolean flag) {
            super.onHaveSeek(flag);
            if (mPlayer != null)
                mPlayer.onHaveSeek(flag);
        }

        @Override
        public void onMoreClick() {
            super.onMoreClick();
            handlerMore();
        }

        @Override
        public void onQuality() {
            super.onQuality();
            handlerQuality();
        }

        @Override
        public void autoSwitchPlayer() {

        }

        @Override
        public void onClickViewFrame() {

        }

        @Override
        public void onShowAd() {

        }

        @Override
        public void onHideAd() {

        }

        @Override
        public void clickSubTitle() {

        }
    };

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RmFragmentLivestreamDetailBinding.inflate(getLayoutInflater(), container, false);
        mActivity = (BaseSlidingFragmentActivity) getActivity();
        viewModel = new ViewModelProvider(requireActivity()).get(RMLivestreamDetailViewModel.class);
        EventBus.getDefault().register(this);
        getData();
        initEvent();
        return binding.getRoot();
    }

    public void getData(){
        viewModel.getLiveDetail(idVideo);

    }

    private void initEvent() {
        viewModel.getResponseLive().observe(requireActivity(), it -> {
            if (it != null) {
                currentVideo = it;
                initData();
            } else {
                requireActivity().onBackPressed();
            }
        });
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromVTM){
                    requireActivity().finish();
                }else {
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
    }

    private void initData() {
        if (currentVideo == null) return;
        if (getActivity() instanceof RMLivestreamActivity) {
            ((RMLivestreamActivity) getActivity()).currentVideo=currentVideo;
        }
        mPlayer = MochaPlayerUtil.getInstance().providePlayerBy("livestream");
        mPlayer.addListener(eventListener);
        mPlayer.addControllerListener(callBackListener);
        mPlayer.getPlayerView().setUseController(true);
        mPlayer.getPlayerView().setEnabled(true);
        mPlayer.getPlayerView().enableFast(false);
        mPlayer.getPlayerView().getController().setVisibility(View.VISIBLE);
        mPlayer.updatePlaybackState();
        mPlayer.getControlView().getViewMore().setVisibility(View.GONE);
        mPlayer.addPlayerViewTo(binding.frVideo);
        mPlayer.getPlayerView().setRefreshMode((float) 16 / 9);
//        mPlayer.prepare("http://apicdn.tls.tl/routing-service/hls/kakoakvideo/57024428/67075560829/bf9a40b28b90c1fca2a1c83ed895003e/1740190400039/livestream_vod/2025/02/21/3b884eaa9d7f46c59de71847ef42268c/playlist.m3u8");
        Utilities.setSizeFrameVideo(requireActivity(), binding.frController, (double) 16 / 9);
        if (currentVideo.getStatus() == RMLivestream.TYPE_LIVE) {
//            mActivity.executeFragmentTransactionAllowLoss(RMInfoLiveFragment.Companion.newInstance(), binding.frameInfo.getId(), false, false, "LIVE");
            binding.layoutLive.setVisibility(View.VISIBLE);
            binding.layoutComingSoon.setVisibility(View.GONE);
            mPlayer.prepare(Video.convertLivestreamToVideo(currentVideo));
            binding.frVideo.setVisibility(View.VISIBLE);
            mPlayer.setPlayWhenReady(true);
            childFragment(RMInfoLiveFragment.Companion.newInstance(), binding.frameInfo.getId());

        } else if (currentVideo.getStatus() == RMLivestream.TYPE_UPCOMING) {
            childFragment(RMInfoUpcomingFragment.Companion.newInstance(), binding.frameInfo.getId());
//            mActivity.executeFragmentTransactionAllowLoss(RMInfoUpcomingFragment.Companion.newInstance(), binding.frameInfo.getId(), false, false, "UPCOMING");
            binding.layoutLive.setVisibility(View.GONE);
            binding.layoutComingSoon.setVisibility(View.VISIBLE);
            ImageManager.showImage(currentVideo.getLinkAvatar(), binding.ivVideo);
            binding.frVideo.setVisibility(View.GONE);
            mPlayer.setPlayWhenReady(false);
        } else {
            childFragment(RMInfoVODFragment.Companion.newInstance(), binding.frameInfo.getId());
//            mActivity.executeFragmentTransactionAllowLoss(RMInfoVODFragment.Companion.newInstance(), binding.frameInfo.getId(), false, false, "VOD");
            binding.layoutLive.setVisibility(View.GONE);
            binding.layoutComingSoon.setVisibility(View.GONE);
            mPlayer.prepare(Video.convertLivestreamToVideo(currentVideo));
            binding.frVideo.setVisibility(View.VISIBLE);
            mPlayer.getControlView().getViewMore().setVisibility(View.GONE);
            mPlayer.setPlayWhenReady(true);
        }


    }


    @Override
    public void onDestroyView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getChildFragmentManager().getFragments().forEach(fragment -> {
                getChildFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            });
        }
        requireActivity().getViewModelStore().clear();
        viewModel.onCleared();
        viewModel = null;
        MochaPlayerUtil.removePlayer("livestream");
        EventBus.getDefault().unregister(this);
        if (mPlayer != null && callBackListener != null)
            mPlayer.removeControllerListener(callBackListener);
        if (mPlayer != null && eventListener != null)
            mPlayer.removeListener(eventListener);
        super.onDestroyView();
    }

    public void hanlderKeepScreenOn() {
        if (mPlayer != null && mPlayer.getPlayerView() != null) {
            if (!mPlayer.getPlayWhenReady()) {
                if (mPlayer.getAdsManager() != null && mPlayer.isAdDisplayed()) {
                    mPlayer.getPlayerView().setKeepScreenOn(true);
                } else {
                    mPlayer.getPlayerView().setKeepScreenOn(mPlayer.getPlayWhenReady());
                }
            } else {
                mPlayer.getPlayerView().setKeepScreenOn(mPlayer.getPlayWhenReady());
            }
        }
    }

    protected void handlerFullScreen() {
//        if (activity == null || activity.isFinishing())
//            return;
//        if (currentVideo.getAspectRatio() > 1)
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        isFullScreen = true;
        mPlayer.setFullscreen(true);
        binding.ivBack.setVisibility(View.GONE);
        binding.frameInfo.setVisibility(View.GONE);
        setSizeFullFrameVideo(requireActivity(), binding.frController);
        setSizeFullFrameVideo(requireActivity(), binding.frVideo);
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isFullScreen){
                    setEnabled(false);
                    handlerCloseFullScreen();
                } else {
                    requireActivity().onBackPressed();
                }
            }
        });
//        mPlayer.getPlayerView().setRefreshMode((float) 21 / 9);
    }


    public static void setSizeFullFrameVideo(Activity activity, FrameLayout frController) {
        if (activity == null || frController == null) return;
        try {
            ViewGroup.LayoutParams layoutParams = frController.getLayoutParams();
            int width = ScreenManager.getWidth(activity);
            int height = ScreenManager.getHeight(activity);
            layoutParams.width = height;
            layoutParams.height = (int) (width * 0.94);
            frController.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlerCloseFullScreen() {
        isFullScreen = false;
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mPlayer.setFullscreen(false);
        binding.ivBack.setVisibility(View.VISIBLE);
        binding.frameInfo.setVisibility(View.VISIBLE);
        Utilities.setSizeFrameVideo(requireActivity(), binding.frController, (double) 16 / 9);
        Utilities.setSizeFrameVideo(requireActivity(), binding.frVideo, (double) 16 / 9);
//        mPlayer.getPlayerView().setRefreshMode((float) 16 / 9);
    }

    public void handlerQuality() {
//        if (activity == null || activity.isFinishing())
//            return;
//        QualityVideoDialog mSpeedVideoDialog = new QualityVideoDialog(activity);
//        mSpeedVideoDialog.setCurrentVideo(currentVideo);
//        mSpeedVideoDialog.setOnQualityVideoListener(new QualityVideoDialog.OnQualityVideoListener() {
//            @Override
//            public void onQualityVideo(int idx, Video video, Resolution resolution) {
//                if (currentVideo != null && video != null
//                        && !TextUtils.isEmpty(currentVideo.getId()) && !TextUtils.isEmpty(video.getId())
//                        && currentVideo.getId().equals(video.getId())) {
//                    if (currentVideo.getIndexQuality() == idx)
//                        return;
//                    currentVideo.setIndexQuality(idx);
//                    long position;
//                    long duration;
//                    if (mPlayer != null) {
//                        position = mPlayer.getCurrentPosition();
//                        duration = mPlayer.getDuration();
//                        mPlayer.prepare(resolution.getVideoPath());
//                        mPlayer.seekTo(Math.min(position, duration));
//                    }
//
//                }
//            }
//        });
//        mSpeedVideoDialog.show();
    }


    @Override
    public void onPause() {
        super.onPause();
        if(mPlayer!=null){
            mPlayer.setPlayWhenReady(false);
        }
    }

    public void handlerMore() {

    }

    public void childFragment(Fragment fragment, int fragmentParentId) {
        getChildFragmentManager().beginTransaction()
                .add(fragmentParentId, fragment)
                .commit();
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveViewNumber(LiveStreamViewNumber number) {
        binding.tvViewer.setText(String.valueOf(number.getNumber()));
        EventBus.getDefault().removeStickyEvent(number);
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void stopLivestream(LivestreamLiveNotification event) {
        if(event.getContentType().equals(LivestreamLiveNotification.typeStop)){
            mPlayer.setPlayWhenReady(false);
            ImageManager.showImage(currentVideo.getLinkAvatar(), binding.ivVideo);
            binding.frVideo.setVisibility(View.GONE);
            binding.tvEndLive.setVisibility(View.VISIBLE);

        } else if(event.getContentType().equals(RMConstants.WebSocket.typeStream)){
//            viewModel.getLiveDetail(currentVideo.getId());
        }
        EventBus.getDefault().removeStickyEvent(event);
    }

}