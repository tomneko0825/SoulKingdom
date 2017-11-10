package com.tomneko.soulkingdom.view.image.enums;

import com.tomneko.soulkingdom.R;

/**
 * 敵のイメージ
 *
 * Created by toyama on 2017/09/10.
 */
public enum EnemyImageEnum {

	// 狼
	WILD_WOLF_IMG(R.drawable.enemy_001);

	private int id;

	private EnemyImageEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
