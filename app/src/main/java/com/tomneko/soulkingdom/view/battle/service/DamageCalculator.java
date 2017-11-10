package com.tomneko.soulkingdom.view.battle.service;

import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleAction;
import com.tomneko.soulkingdom.view.battle.model.BattleMember;

/**
 * ダメージの計算
 *
 * Created by toyama on 2017/09/29.
 */
@Service
public class DamageCalculator {

	/**
	 * ダメージ計算
	 *
	 * @param attacker
	 * @param target
	 * @param battleAction
	 * @return
	 */
	public int damage(BattleMember attacker, BattleMember target, BattleAction battleAction) {

		int atk = attacker.getBattleMemberStatus().getAtk();

		return atk;
	}
}
