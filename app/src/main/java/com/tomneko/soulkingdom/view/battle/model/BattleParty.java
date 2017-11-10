package com.tomneko.soulkingdom.view.battle.model;

import java.util.ArrayList;
import java.util.List;

/**
 * パーティの戦場データ
 * <p/>
 * Created by toyama on 2017/09/08.
 */
public class BattleParty {

	private List<BattleChara> battleCharaList = new ArrayList();

	public List<BattleChara> getBattleCharaList() {
		return battleCharaList;
	}

	public void setBattleCharaList(List<BattleChara> battleCharaList) {
		this.battleCharaList = battleCharaList;
	}


	/**
	 * 指定されたIDのみをアクティブにする
	 *
	 * @param id
	 */
//	public void setActive(String id) {
//		for (BattleChara bc  : battleCharaList) {
//
//			if (bc.getId().equals(id)) {
//				bc.setActive(true);
//			} else {
//				bc.setActive(false);
//			}
//		}
//	}

	/**
	 * idから取得
	 *
	 * @param id
	 * @return
	 */
	public BattleChara getById(String id) {

		for (BattleChara chara : battleCharaList) {
			if (id.equals(chara.getId())) {
				return chara;
			}
		}
		return null;
	}

	/**
	 * 現在戦闘中のキャラを取得
	 *
	 * @return
	 */
	public BattleChara getCurrentBattleChara() {

		// とりあえず戦闘を返す
		for (BattleChara battleChara : battleCharaList) {
			if (battleChara.isOnBattle()) {
				return battleChara;
			}
		}

		// 戦闘不能のタイミングで取れない可能性がある
		return null;
	}
}
