package com.tomneko.soulkingdom.view.battle.tactics.factory;

import com.tomneko.soulkingdom.view.battle.tactics.Tactics;

/**
 * 戦術のファクトリ
 * <p/>
 * Created by toyama on 2017/10/19.
 */
public interface TacticsFactory {

	/**
	 * 戦術を作成
	 *
	 * @return
	 */
	Tactics create(int level);
}
