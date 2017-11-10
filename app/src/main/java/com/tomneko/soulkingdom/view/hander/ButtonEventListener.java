package com.tomneko.soulkingdom.view.hander;

import android.view.MotionEvent;

/**
 * ボタンのイベントのリスナ
 * <p/>
 * Created by toyama on 2017/09/13.
 */
public interface ButtonEventListener {

	/**
	 * 押下
	 *
	 * @param event
	 */
	void onDown(MotionEvent event);

	/**
	 * リリース
	 *
	 * @param event
	 */
	void release(MotionEvent event);

}
