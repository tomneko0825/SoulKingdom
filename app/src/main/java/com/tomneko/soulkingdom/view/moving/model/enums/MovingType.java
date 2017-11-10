package com.tomneko.soulkingdom.view.moving.model.enums;

/**
 * 動きが何を意味するか
 * <p/>
 * Created by toyama on 2017/09/11.
 */
public enum MovingType {

	// アクション
	ACTION_MOVE,

	// アクション(SAなし)
	ACTION_NOSA_MOVE,

	// ガード
	GUARD_MOVE,

	// ガード後
	GUARD_AFTER_MOVE,

	// 回避前
	DODGE_BEFORE_MOVE,

	// 回避中
	DODGE_MOVE,

	// 回避後
	DODGE_AFTER_MOVE,

	// チェンジ出陣
	CHANGE_GO_MOVE,

	// チェンジ戻る
	CHANGE_BACK_MOVE,

	// シェイク
	SHAKE_MOVE,

	// エフェクト
	EFFECT_MOVE,

	// 文字
	TEXT_MOVE,

	// 出現、行く
	APPEAR_IN_MOVE,

	// 出現、移動
	// APPEAR_MOVE,

	// 戦闘不能の点滅
	BLINK_DEFEATED_MOVE,

}
