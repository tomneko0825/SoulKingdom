package com.tomneko.soulkingdom.view.service;

import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.BattleStageConstatns;

import java.util.concurrent.TimeUnit;

/**
 * FPSの表示・調整するクラス
 */
@Service
public class FpsManager {

	// 1秒（ナノ秒単位）
	private static long ONE_SEC_TO_NANO = TimeUnit.SECONDS.toNanos(1L);

	// 1ミリ秒（ナノ秒単位）
	private static long ONE_MILLI_TO_NANO = TimeUnit.MILLISECONDS.toNanos(1L);

	private int maxFps;
	private int[] fpsBuffer;
	private int fpsCnt;
	private long startTime;
	private long elapsedTime;
	private long sleepTime;
	private long oneCycle;

	/**
	 * コンストラクタ
	 */
	public FpsManager() {
		maxFps = BattleStageConstatns.BASE_FPS;
		fpsBuffer = new int[maxFps];
		fpsCnt = 0;
		startTime = System.nanoTime();
		oneCycle = (long) (Math.floor((double) ONE_SEC_TO_NANO / maxFps));
	}

	/**
	 * 状態更新
	 *
	 * @return sleepする時間（ナノ秒）
	 */
	public long state() {
		fpsCnt++;
		if (maxFps <= fpsCnt) {
			fpsCnt = 0;
		}

		elapsedTime = System.nanoTime() - startTime;
		sleepTime = oneCycle - elapsedTime;

		// 余裕が無い場合でも1ミリ秒はsleepさせる
		if (sleepTime < ONE_MILLI_TO_NANO) {
			sleepTime = ONE_MILLI_TO_NANO;
		}

		int fps = (int) (ONE_SEC_TO_NANO / (elapsedTime + sleepTime));
		fpsBuffer[fpsCnt] = fps;

		startTime = System.nanoTime() + sleepTime;

		return sleepTime;
	}

	/**
	 * FPSの取得
	 *
	 * @return 現在のFPS
	 */
	public int getFps() {
		int allFps = 0;
		for (int i = 0; i < maxFps; i++) {
			allFps += fpsBuffer[i];
		}

		return allFps / maxFps;
	}
}
