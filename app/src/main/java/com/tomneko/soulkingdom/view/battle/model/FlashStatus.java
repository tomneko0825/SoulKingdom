package com.tomneko.soulkingdom.view.battle.model;

import com.tomneko.soulkingdom.view.battle.model.enums.FlashStatusType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 揮発性ステータス
 * <p/>
 * Created by toyama on 2017/09/12.
 */
public class FlashStatus {

	private Map<FlashStatusType, FlashStatusCounter> counterMap = new HashMap();

	/**
	 * カウンターを取得
	 *
	 * @param type
	 * @return
	 */
	public FlashStatusCounter getCounter(FlashStatusType type) {
		return counterMap.get(type);
	}

	/**
	 * カウンターを設定
	 *
	 * @param type
	 * @param counter
	 */
	public void setFlashStatus(FlashStatusType type, FlashStatusCounter counter) {
		counterMap.put(type, counter);
	}

	/**
	 * 揮発性ステータスを追加する
	 *
	 * @param type
	 * @param flushCount
	 */
	public void putFlashStatus(FlashStatusType type, int flushCount) {

		FlashStatusCounter counter = counterMap.get(type);

		if (counter == null) {
			counter = new FlashStatusCounter(type, flushCount);
			counterMap.put(type, counter);
		} else {
			counter.update(flushCount);
		}
	}

	/**
	 * カウンターを進める
	 */
	public void countUp() {

		for (FlashStatusType type : counterMap.keySet()) {
			FlashStatusCounter counter = counterMap.get(type);
			counter.countUp();
		}
	}

	/**
	 * 終わりかどうか
	 *
	 * @return
	 */
	public boolean isFinish(FlashStatusType type) {
		FlashStatusCounter counter = counterMap.get(type);
		return counter.isFinish();
	}

	/**
	 * 終わっているステータスを終了させる
	 */
	public void finish() {

		List<FlashStatusType> deleteList = new ArrayList();

		for (FlashStatusType type : counterMap.keySet()) {
			FlashStatusCounter counter = counterMap.get(type);
			if (counter.isFinish()) {
				deleteList.add(type);
			}
		}

		for (FlashStatusType type : deleteList) {
			counterMap.remove(type);
		}
	}

	@Override
	public String toString() {
		String str = "fs";
		for (FlashStatusType type : counterMap.keySet()) {
			FlashStatusCounter counter = counterMap.get(type);
			str += "[" + counter.toString() + "] ";
		}
		return str;
	}
}
