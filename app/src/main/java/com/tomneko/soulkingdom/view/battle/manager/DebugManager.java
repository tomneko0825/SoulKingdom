package com.tomneko.soulkingdom.view.battle.manager;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.listener.DebugButtonListenerCreator;
import com.tomneko.soulkingdom.view.battle.manager.enums.BattleDebugType;
import com.tomneko.soulkingdom.view.battle.model.BattleChara;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemy;
import com.tomneko.soulkingdom.view.battle.model.BattleMember;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.button.enums.ButtonObjectType;
import com.tomneko.soulkingdom.view.drawer.ButtonDrawer;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.tomneko.soulkingdom.view.battle.manager.enums.BattleDebugType.MESSAGE;
import static com.tomneko.soulkingdom.view.battle.manager.enums.BattleDebugType.STATUS;

/**
 * デバッグ関連各種
 * <p/>
 * Created by toyama on 2017/09/03.
 */
@Service
public class DebugManager {

	@Inject
	private ScaleCalculator sc;

	@Inject
	private ButtonDrawer buttonDrawer;

	@Inject
	private PartyManager partyManager;

	@Inject
	private EnemyManager enemyManager;

	@Inject
	private EffectManager effectManager;

	@Inject
	private DebugButtonListenerCreator debugListenerCreator;

	@Inject
	private BattleStageManager battleStageManager;

	private List<ButtonObject> buttonObjectList = new ArrayList();

	private List<String> messageList = new LinkedList();

	// デバッグの種類
	private BattleDebugType battleDebugType = STATUS;

	// デバッグメッセージの出力行数
	public static final int DEBUG_LINE_COUNT = 24;

	public List<ButtonObject> getButtonObjectList() {
		return buttonObjectList;
	}

	public void addMessage(String message) {
		this.messageList.add(message);
	}

	public BattleDebugType getBattleDebugType() {
		return battleDebugType;
	}

	public void setBattleDebugType(BattleDebugType battleDebugType) {
		this.battleDebugType = battleDebugType;
	}

	/**
	 * デバッグのメッセージリストを取得
	 *
	 * @return
	 */
	public List<String> getDebugMessageList() {

		if (battleDebugType == MESSAGE) {
			return createMessageList();
		} else if (battleDebugType == STATUS) {
			return createStatusList();
		}
		return new ArrayList();
	}

	/**
	 * ステータスを作成
	 *
	 * @return
	 */
	private List<String> createStatusList() {

		List<String> list = new ArrayList();

		// ステージ
		list.add(battleStageManager.getBattleStageStateType().name());

		// キャラ
		for (BattleChara chara : partyManager.getBattleParty().getBattleCharaList()) {
			list.addAll(createBattleMemberStatus(chara));
		}

		// 敵
		List<BattleEnemy> enemyList = enemyManager.getCurrentBattleEnemyList();
		for (BattleEnemy enemy : enemyList) {
			list.addAll(createBattleMemberStatus(enemy));
		}

		// エフェクト
		List<MovingEffect> effectList = effectManager.getMovingEffectList();
		for (MovingEffect effect : effectList) {
			MovingObject effectMo = effect.getMovingObject();
			list.add("effect [" + effect.getEffectImageEnum().name() + "]");
			list.add("  " + effectMo.toString());
			list.add("");
		}

		return list;
	}

	/**
	 * 戦闘員のステータスを作成
	 *
	 * @param battleMember
	 * @return
	 */
	private List<String> createBattleMemberStatus(BattleMember battleMember) {

		List<String> retList = new ArrayList();
		MovingObject mo = battleMember.getMovingObject();
		retList.add(mo.getInvokerId() + " state[" + battleMember.getBattleMovingType() + "]");
		retList.add("  " + mo.toString());
		// retList.add("  " + battleMember.getFlashStatus().toString());
		retList.add("");

		return retList;
	}


	/**
	 * 表示するメッセージのリストを作成
	 *
	 * @return
	 */
	private List<String> createMessageList() {
		// デバッグ対象のメッセージ
		List<String> retList = new ArrayList();
		for (int i = messageList.size() - 1; 0 <= i; i--) {
			String message = messageList.get(i);
			retList.add(message);

			// 24行だけ表示
			if (DEBUG_LINE_COUNT <= messageList.size() - i) {
				break;
			}
		}
		return retList;
	}

	/**
	 * デバッグ用のボタン群を作成
	 */
	public void createDebugButtons() {

		Map<String, ButtonObject> buttonObjectMap = createButtonObjectMap();

		debugListenerCreator.createListener(buttonObjectMap);
	}

	/**
	 * ボタンオブジェクトのマップを作成
	 *
	 * @return
	 */
	private Map<String, ButtonObject> createButtonObjectMap() {

		Map<String, ButtonObject> buttonObjectMap = new HashMap();

		float viewWidth = sc.getViewWidth();
		float areaWidth = viewWidth / 16 * 10;

		// 一枠の大きさから作成
		float margin = areaWidth / 3 / 30;

		float x = viewWidth / 16 * 3;
		float width = (areaWidth - (margin * 11)) / 10;
		float y = PartyManager.STATUS_HEIGHT + sc.getY(30);
		float height = sc.getY(80) - (margin * 2);

		for (int i = 0; i < 10; i++) {

			// マージン
			x += margin;

			ButtonObject bo = new ButtonObject();
			bo.setButtonObjectType(ButtonObjectType.ALPHA_LABEL);
			bo.setX(x);
			bo.setY(y);
			bo.setWidth(width);
			bo.setHeight(height);

			bo.setId("debug_" + i);

			buttonObjectMap.put(bo.getId(), bo);
			buttonObjectList.add(bo);

			x += width;
		}
		return buttonObjectMap;
	}


}
