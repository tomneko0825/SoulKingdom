package com.tomneko.soulkingdom.view.battle.model;

import com.tomneko.soulkingdom.view.button.ButtonObject;

/**
 * 戦闘員描画のためのひとまとまり
 *
 * Created by toyama on 2017/09/19.
 */
public class BattleMemberDrawingObject {

	private BattleMember battleMember;

	private ButtonObject buttonObject;

	public BattleMember getBattleMember() {
		return battleMember;
	}

	public void setBattleMember(BattleMember battleMember) {
		this.battleMember = battleMember;
	}

	public ButtonObject getButtonObject() {
		return buttonObject;
	}

	public void setButtonObject(ButtonObject buttonObject) {
		this.buttonObject = buttonObject;
	}
}
