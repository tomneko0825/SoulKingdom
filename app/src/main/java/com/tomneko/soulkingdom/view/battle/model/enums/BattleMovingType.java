package com.tomneko.soulkingdom.view.battle.model.enums;

/**
 * 戦闘時のメンバーの動作状態
 * <p/>
 * Created by toyama on 2017/09/11.
 */
public enum BattleMovingType {

	// 控え
	ON_RESERVE,

	// 交代中、行く
	ON_CHANGE_GO,

	// 交代中、戻る
	ON_CHANGE_BACK,

	// 待機中
	ON_WAIT,

	// アクション中
	ON_ACTION,

	// SAなしアクション中
	ON_ACTION_NOSA,

	// ガード中
	ON_GUARD,

	// ガード後
	ON_AFTER_GUARD,

	// 回避前
	ON_BEFORE_DODGE,

	// 回避中
	ON_DODGE,

	// 回避後
	ON_AFTER_DODGE,

	// シェイク中
	ON_SHAKE,

	// 出現中
	ON_APPEAR_IN,

	// 出現することによる移動
	// ON_APPEAR_MOVE,

	// 戦闘不能点滅中
	ON_BLINK_DEFEATED,

	// 戦闘不能
	ON_DEFEATED

	;
}
