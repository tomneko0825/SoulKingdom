package com.tomneko.soulkingdom.view.moving.model;

import com.tomneko.soulkingdom.view.moving.model.enums.MovingType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 動きの組み合わせ
 * <p/>
 * Created by toyama on 2017/09/05.
 */
public class MovingCombination {

	private LinkedList<MovingTarget> movingTargetList = new LinkedList();

	private String tag;

	private String id;

	private static Map<String, Long> tagCountMap = new HashMap();

	public MovingCombination(String tag) {
		this.tag = tag;

		synchronized (tagCountMap) {
			Long count = tagCountMap.get(tag);
			if (count == null) {
				count = new Long(0);
				tagCountMap.put(tag, count);
			} else {
				count++;
				tagCountMap.put(tag, count);
			}

			id = tag + "_" + count;
		}
	}

	/**
	 * 残りのステップ数
	 *
	 * @return
	 */
	public int getRestStepCount() {

		int rest = 0;

		for (MovingTarget mt : movingTargetList) {
			int tmp = mt.getSteps() - mt.getCurrentSteps();
			rest += tmp;
		}
		return rest;
	}

	/**
	 * 今のMovingTypeを取得
	 *
	 * @return
	 */
	public MovingType getCurrentMovingType() {

		// MovingTargetの最初
		return movingTargetList.getFirst().getMovingType();
	}

	public LinkedList<MovingTarget> getMovingTargetList() {
		return movingTargetList;
	}

	public void addMovingTarget(MovingTarget mt) {
		this.movingTargetList.add(mt);
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "{type=" + getCurrentMovingType() +
				", id='" + id + '\'' +
				", rest='" + getRestStepCount() + '\'' +
				'}';
	}
}
