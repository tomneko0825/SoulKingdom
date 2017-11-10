package com.tomneko.soulkingdom.view.battle.service;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_COUNT;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_WIDHT;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.CHARA_BASE_X;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.CHARA_BASE_Y;

/**
 * 戦闘の位置の計算
 *
 * Created by toyama on 2017/09/10.
 */
@Service
public class BattlePositionCalculator {

	@Inject
	private ScaleCalculator sc;

	/**
	 * キャラの基準となる足元の位置x
	 *
	 * @return
	 */
	public float getCharaRootPositionX() {
		float rootX = sc.getViewWidth() / BLOCK_COUNT * CHARA_BASE_X;
		return rootX;
	}

	public float getCharaRootPositionY() {
		float rootY = sc.getY(BLOCK_WIDHT) * (CHARA_BASE_Y - 1);
		return rootY;
	}
}
