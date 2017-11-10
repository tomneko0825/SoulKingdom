package com.tomneko.soulkingdom.view.image.enums;

import com.tomneko.soulkingdom.R;

/**
 * 戦闘背景のイメージ
 *
 * Created by toyama on 2017/09/10.
 */
public enum BackBattleImageEnum {

	// 森
	FOREST(R.drawable.back_battle_001);

	private int id;

	private BackBattleImageEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
