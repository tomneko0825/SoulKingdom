package com.tomneko.soulkingdom.view.moving.model.enums;

/**
 * エフェクトの表示先
 *
 * Created by toyama on 2017/09/14.
 */
public enum MovingEffectTargetType {

	// 設定されている通り
	ET_ASIS,

	// idに従う
	ET_BY_ID,

	// キャラ
	ET_CHARA,

	// ターゲットとなっている敵
	ET_ENEMY_TARGET,

	// すべての敵
	ET_ENEMY_ALL;


}
