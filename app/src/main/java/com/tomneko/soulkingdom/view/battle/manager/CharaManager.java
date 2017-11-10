package com.tomneko.soulkingdom.view.battle.manager;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleChara;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;


/**
 * 操作キャラの管理
 * <p>
 * Created by toyama on 2017/09/03.
 */
@Service
public class CharaManager {

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattleMemberManager battleMemberManager;

	@Inject
	private PartyManager partyManager;

	/**
	 * キャラを進める
	 */
	public void proceedChara() {

		for (BattleChara battleChara : partyManager.getBattleParty().getBattleCharaList()) {
			battleMemberManager.proceedBattleMember(battleChara);
		}
	}
}
