package com.tomneko.soulkingdom.view.battle.model;

import com.tomneko.soulkingdom.view.battle.model.enums.FlashStatusType;

/**
 * 揮発性ステータスのカウンタ
 * <p/>
 * Created by toyama on 2017/09/12.
 */
public class FlashStatusCounter {

	// タイプ
	private FlashStatusType flashStatusType;

	// 揮発させるカウント
	private int flashCount;

	// 現在のカウント
	private int currentCount;

	/**
	 * 残りのカウント
	 *
	 * @return
	 */
	public int getRestCount() {
		return flashCount - currentCount;
	}

	public FlashStatusType getFlashStatusType() {
		return flashStatusType;
	}

	public FlashStatusCounter(FlashStatusType flashStatusType, int flushCount) {
		this.flashStatusType = flashStatusType;
		this.flashCount = flushCount;
	}

	/**
	 * 新しいカウンタの方が多ければ更新する
	 *
	 * @param newFlashCount
	 */
	public void update(int newFlashCount) {

		if (flashCount < newFlashCount) {
			flashCount = newFlashCount;
		}
	}

	/**
	 * カウンタを進める。
	 *
	 * @return
	 */
	public void countUp() {
		currentCount++;
	}

	/**
	 * 終わりかどうか
	 *
	 * @return
	 */
	public boolean isFinish() {
		if (flashCount < currentCount) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return flashStatusType + " " + currentCount + "/" + flashCount;
	}
}
