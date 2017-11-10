package com.tomneko.soulkingdom.view.service;

import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.MainView;

/**
 * スケールの計算
 *
 * Created by toyama on 2017/09/01.
 */
@Service
public class ScaleCalculator {

	private final static float BASE_WIDTH = 1280;
	private final static float BASE_HEIGHT = 720;

	private float viewWidth = 0;

	private float viewHeight = 0;

	private float scale = 0;

	/**
	 * 初期化処理
	 *
	 * @param mainView
	 */
	public void initialize(MainView mainView) {
		this.viewWidth = mainView.getWidth();
		this.viewHeight = mainView.getHeight();

		float scaleX = mainView.getWidth() / BASE_WIDTH;

		// ひとまず横のサイズに合わせる
		// float scaleY = getHeight() / BASE_HEIGHT;
		// scale = scaleX > scaleY ? scaleY : scaleX;
		scale = scaleX;
	}

	public float getX(float x) {
		return x * scale;
	}

	public int getIntX(float x) {
		return (int)(x * scale);
	}

	// スケール計算された画面の幅
	public float getScaledWidth() {
		return BASE_WIDTH * scale;
	}

	public float getY(float y) {
		return y * scale;
	}

	public int getIntY(float y) {
		return (int)(y* scale);
	}

	// スケール計算された画面の高さ
	public float getScaledHeight() {
		return BASE_HEIGHT * scale;
	}

	public float getViewWidth() {
		return viewWidth;
	}

	public float getViewHeight() {
		return viewHeight;
	}

}
