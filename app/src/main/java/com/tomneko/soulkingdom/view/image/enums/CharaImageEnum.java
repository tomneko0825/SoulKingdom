package com.tomneko.soulkingdom.view.image.enums;

import com.tomneko.soulkingdom.R;

/**
 * キャラのイメージ
 *
 * Created by toyama on 2017/09/10.
 */
public enum CharaImageEnum {

	CHARA_001(R.drawable.chara_001),

	CHARA_002(R.drawable.chara_002),

	CHARA_003(R.drawable.chara_003),;

	private int id;

	private CharaImageEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
