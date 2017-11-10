package com.tomneko.soulkingdom.view.battle.model;

import com.tomneko.soulkingdom.view.battle.model.enums.BattleMemberType;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;

/**
 * 戦闘員
 * <p/>
 * Created by toyama on 2017/09/11.
 */
public abstract class BattleMember {

	private String id;

	private String name;

	private BattleMemberType battleMemberType;

	// ステータス
	private BattleMemberStatus battleMemberStatus = new BattleMemberStatus();

	// 動きの状態
	private BattleMovingType battleMovingType = BattleMovingType.ON_RESERVE;

	// 揮発性ステータス
	private FlashStatus flashStatus = new FlashStatus();

	private MovingObject movingObject;

	// 戦闘中かどうか
	// private boolean active = false;

	public BattleMemberStatus getBattleMemberStatus() {
		return battleMemberStatus;
	}

	public void setBattleMemberStatus(BattleMemberStatus battleMemberStatus) {
		this.battleMemberStatus = battleMemberStatus;
	}

	/**
	 * 表示対象かどうか
	 *
	 * @return
	 */
	public abstract boolean isVisible();

	/**
	 * 戦闘中かどうか
	 *
	 * @return
	 */
	public abstract boolean isOnBattle();

//	public void setActive(boolean active) {
//		this.active = active;
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MovingObject getMovingObject() {
		return movingObject;
	}

	public void setMovingObject(MovingObject movingObject) {
		this.movingObject = movingObject;
	}

	public BattleMovingType getBattleMovingType() {
		return battleMovingType;
	}

	public void setBattleMovingType(BattleMovingType battleMovingType) {
		this.battleMovingType = battleMovingType;
	}

	public FlashStatus getFlashStatus() {
		return flashStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
