package com.tomneko.soulkingdom.view.battle.service;

import com.tomneko.soulkingdom.framework.Service;

/**
 * 戦闘用定数
 * <p/>
 * Created by toyama on 2017/10/04.
 */
@Service
public class BattleConstantsHolder {

	private int baseStep = 8;

	public int getStep() {
		return baseStep;
	}

	public int getStep(int divisor) {
		return baseStep / divisor;
	}

	public int getStepMulti(int multiplier) {
		return baseStep * multiplier;
	}
}
