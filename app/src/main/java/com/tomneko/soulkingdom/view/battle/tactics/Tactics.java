package com.tomneko.soulkingdom.view.battle.tactics;

import com.tomneko.soulkingdom.view.moving.model.MovingCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 戦術
 * <p/>
 * Created by toyama on 2017/10/04.
 */
public class Tactics {

	private List<TacticsPattern> tacticsPatternList = new ArrayList();

	// 前回実行からのインターバル
	private int interval;

	// ここまでは実行しない
	private int intervalMin = 16;

	// intervalMin時に実行される確率
	private int intervalMinPer = 10;

	// これ以上は必ず実行
	private int intervalMax = 48;

	// 実行間隔のカウンタ
	private Map<TacticsPattern, Integer> counterMap = new HashMap();

	// 予約のリスト
	private LinkedList<MovingCombination> reserveMovingCombinationList = new LinkedList();

	public List<TacticsPattern> getTacticsPatternList() {
		return tacticsPatternList;
	}

	public void addTacticsPattern(TacticsPattern tacticsPattern) {
		this.tacticsPatternList.add(tacticsPattern);
		this.counterMap.put(tacticsPattern, 0);
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getIntervalMin() {
		return intervalMin;
	}

	public void setIntervalMin(int intervalMin) {
		this.intervalMin = intervalMin;
	}

	public int getIntervalMax() {
		return intervalMax;
	}

	public void setIntervalMax(int intervalMax) {
		this.intervalMax = intervalMax;
	}

	public int getCounter(TacticsPattern tacticsPattern) {
		return counterMap.get(tacticsPattern);
	}

	public LinkedList<MovingCombination> getReserveMovingCombinationList() {
		return reserveMovingCombinationList;
	}

	public void addReserveMovingCombination(MovingCombination mc) {
		this.reserveMovingCombinationList.add(mc);
	}

	/**
	 * インターバルを進める
	 *
	 * @param tacticsPattern
	 */
	public void proceedInterval(TacticsPattern tacticsPattern) {
		for (TacticsPattern tp : counterMap.keySet()) {
			if (tp == tacticsPattern) {
				counterMap.put(tp, 0);
			} else {
				int counter = counterMap.get(tp);
				counter++;
				counterMap.put(tp, counter);
			}
		}
	}

	public int getIntervalMinPer() {
		return intervalMinPer;
	}

	public void setIntervalMinPer(int intervalMinPer) {
		this.intervalMinPer = intervalMinPer;
	}
}
