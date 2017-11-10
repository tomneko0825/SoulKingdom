package com.tomneko.soulkingdom.view.battle.listener;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.BattleStageController;
import com.tomneko.soulkingdom.view.battle.manager.DebugManager;
import com.tomneko.soulkingdom.view.battle.manager.EnemyManager;
import com.tomneko.soulkingdom.view.battle.manager.enums.BattleDebugType;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemy;
import com.tomneko.soulkingdom.view.battle.service.BattleConstantsHolder;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.hander.ButtonEventListener;
import com.tomneko.soulkingdom.view.hander.TouchEventHandler;
import com.tomneko.soulkingdom.view.moving.factory.enemy.AppearFactory;
import com.tomneko.soulkingdom.view.moving.factory.enemy.SimpleAssultFactory;
import com.tomneko.soulkingdom.view.moving.factory.enemy.StraightAssultFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;

import java.util.Map;

import static com.tomneko.soulkingdom.view.battle.manager.enums.BattleDebugType.MESSAGE;
import static com.tomneko.soulkingdom.view.battle.manager.enums.BattleDebugType.NONE;
import static com.tomneko.soulkingdom.view.battle.manager.enums.BattleDebugType.STATUS;

/**
 * デバッグ用リスナのクリエーター
 * <p/>
 * Created by toyama on 2017/09/13.
 */
@Service
public class DebugButtonListenerCreator {

	@Inject
	private TouchEventHandler touchEventHandler;

	@Inject
	private EnemyManager enemyManager;

	@Inject
	private SimpleAssultFactory simpleAssultFactory;

	@Inject
	private StraightAssultFactory straightAssultFactory;

	@Inject
	private BattleStageController battleStageController;

	@Inject
	private DebugManager debugManager;

	@Inject
	private AppearFactory appearFactory;

	@Inject
	private BattleConstantsHolder bch;

	/**
	 * ボタンのリスナを作成
	 *
	 * @param buttonObjectMap
	 */
	public void createListener(Map<String, ButtonObject> buttonObjectMap) {

		// 登場
		ButtonObject bo0 = buttonObjectMap.get("debug_0");
		ButtonEventListener listener0 = createListener0();
		bo0.setButtonEventListener(listener0);
		touchEventHandler.addButtonObject(bo0);

		// シンプルアサルト
		ButtonObject bo1 = buttonObjectMap.get("debug_1");
		ButtonEventListener listener1 = createListener1();
		bo1.setButtonEventListener(listener1);
		touchEventHandler.addButtonObject(bo1);

		// ストレートアサルト
		ButtonObject bo2 = buttonObjectMap.get("debug_2");
		ButtonEventListener listener2 = createListener2();
		bo2.setButtonEventListener(listener2);
		touchEventHandler.addButtonObject(bo2);

		// 動かす
		ButtonObject bo7 = buttonObjectMap.get("debug_7");
		ButtonEventListener listener7 = createListener7();
		bo7.setButtonEventListener(listener7);
		touchEventHandler.addButtonObject(bo7);

		// ストップ
		ButtonObject bo8 = buttonObjectMap.get("debug_8");
		ButtonEventListener listener8 = createListener8();
		bo8.setButtonEventListener(listener8);
		touchEventHandler.addButtonObject(bo8);

		// デバッグ
		ButtonObject bo9 = buttonObjectMap.get("debug_9");
		ButtonEventListener listener9 = createListener9();
		bo9.setButtonEventListener(listener9);
		touchEventHandler.addButtonObject(bo9);
	}

	@NonNull
	private ButtonEventListener createListener9() {
		return new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {
				if (debugManager.getBattleDebugType() == NONE) {
					debugManager.setBattleDebugType(MESSAGE);
				} else if (debugManager.getBattleDebugType() == MESSAGE) {
					debugManager.setBattleDebugType(STATUS);
				} else if (debugManager.getBattleDebugType() == STATUS) {
					debugManager.setBattleDebugType(BattleDebugType.NONE);
				}
			}

			@Override
			public void release(MotionEvent event) {
			}
		};
	}

	@NonNull
	private ButtonEventListener createListener8() {
		return new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {
				battleStageController.changeStop();
			}

			@Override
			public void release(MotionEvent event) {
			}
		};
	}

	@NonNull
	private ButtonEventListener createListener7() {
		return new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {
				battleStageController.onTick();
			}

			@Override
			public void release(MotionEvent event) {
			}
		};
	}

	@NonNull
	private ButtonEventListener createListener2() {

		ButtonEventListener listener = new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {
				BattleEnemy battleEnemy = enemyManager.getTargetBattleEnemy();
				MovingObject mo = battleEnemy.getMovingObject();
				MovingCombination mc = straightAssultFactory.create(mo);
				mo.setMovingCombination(mc);
			}

			@Override
			public void release(MotionEvent event) {
			}
		};
		return listener;
	}

	@NonNull
	private ButtonEventListener createListener1() {

		ButtonEventListener listener = new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {
				BattleEnemy battleEnemy = enemyManager.getTargetBattleEnemy();
				MovingObject mo = battleEnemy.getMovingObject();
				MovingCombination mc = simpleAssultFactory.create(mo);
				mo.setMovingCombination(mc);
			}

			@Override
			public void release(MotionEvent event) {
			}
		};
		return listener;
	}

	@NonNull
	private ButtonEventListener createListener0() {

		ButtonEventListener listener = new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {

				// TODO ３つ表示されていたらパス
				if (3 <= enemyManager.getDrawingObjectList().size()) {
					return;
				}

				// enemyManager.createEnemy();
			}

			@Override
			public void release(MotionEvent event) {
			}
		};
		return listener;
	}

}
