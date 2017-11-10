package com.tomneko.soulkingdom.view.moving.model;

import com.tomneko.soulkingdom.view.battle.model.BattleAction;
import com.tomneko.soulkingdom.view.image.enums.EffectImageEnum;
import com.tomneko.soulkingdom.view.moving.model.enums.MovingEffectTargetType;

import static com.tomneko.soulkingdom.view.moving.model.enums.MovingEffectTargetType.ET_ASIS;

/**
 * エフェクト
 * <p/>
 * Created by toyama on 2017/09/09.
 */
public class MovingEffect {

	private BattleAction battleAction;

	private MovingObject movingObject;

	private EffectImageEnum effectImageEnum;

	private MovingEffectTargetType movingEffectTargetType = ET_ASIS;

	// MovingEffectTargetTypeがET_BY_IDの場合のみ
	private String movingEffectTargetId;

	// ターゲットが見つからないなど
	private boolean invalid = false;

	public MovingEffect(EffectImageEnum effectImageEnum) {
		this.effectImageEnum = effectImageEnum;
	}

	public EffectImageEnum getEffectImageEnum() {
		return effectImageEnum;
	}

	public MovingObject getMovingObject() {
		return movingObject;
	}

	public void setMovingObject(MovingObject movingObject) {
		this.movingObject = movingObject;
	}

	public BattleAction getBattleAction() {
		return battleAction;
	}

	public void setBattleAction(BattleAction battleAction) {
		this.battleAction = battleAction;
	}

	public MovingEffectTargetType getMovingEffectTargetType() {
		return movingEffectTargetType;
	}

	public void setMovingEffectTargetType(MovingEffectTargetType movingEffectTargetType) {
		this.movingEffectTargetType = movingEffectTargetType;
	}

	public String getMovingEffectTargetId() {
		return movingEffectTargetId;
	}

	public void setMovingEffectTargetId(String movingEffectTargetId) {
		this.movingEffectTargetId = movingEffectTargetId;
	}

	public boolean isInvalid() {
		return invalid;
	}

	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}

	/**
	 * アクションを消す
	 */
	public void clearBattleAction() {
		this.battleAction = null;
	}
}
