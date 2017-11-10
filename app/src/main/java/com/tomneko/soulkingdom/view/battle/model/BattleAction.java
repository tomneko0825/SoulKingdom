package com.tomneko.soulkingdom.view.battle.model;

import com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleActionType;

/**
 * 戦闘のアクション
 *
 * Created by toyama on 2017/09/09.
 */
public class BattleAction {

	// 起動者
	private String invorkerId;

	// 戦闘アクションのタイプ
	private BattleActionType type;

	// 戦闘アクションの呼び出し元
	private BattleActionTargetType from;

	// 戦闘アクションの対象
	private BattleActionTargetType to;

	public String getInvorkerId() {
		return invorkerId;
	}

	// TODO おためし
	private String message;

	public BattleAction(BattleActionType type, String invorkerId) {
		this.type = type;
		this.invorkerId = invorkerId;
	}

	public BattleActionType getType() {
		return type;
	}

	public BattleActionTargetType getFrom() {
		return from;
	}

	public void setFrom(BattleActionTargetType from) {
		this.from = from;
	}

	public BattleActionTargetType getTo() {
		return to;
	}

	public void setTo(BattleActionTargetType to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
