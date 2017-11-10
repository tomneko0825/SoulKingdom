package com.tomneko.soulkingdom.view.battle.listener;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.manager.PartyManager;
import com.tomneko.soulkingdom.view.battle.model.BattleChara;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType;
import com.tomneko.soulkingdom.view.battle.service.BattleConstantsHolder;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.hander.ButtonEventListener;
import com.tomneko.soulkingdom.view.hander.TouchEventHandler;
import com.tomneko.soulkingdom.view.moving.factory.chara.DodgeFactory;
import com.tomneko.soulkingdom.view.moving.factory.chara.GuardFactory;
import com.tomneko.soulkingdom.view.moving.factory.chara.SlashFactory;
import com.tomneko.soulkingdom.view.moving.factory.chara.SlashSFactory;
import com.tomneko.soulkingdom.view.moving.factory.chara.SlashVFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;

import java.util.List;
import java.util.Map;

import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_WAIT;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.DODGE_MOVE;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.SHAKE_MOVE;

/**
 * キャラ用リスナのクリエーター
 * <p/>
 * Created by toyama on 2017/09/13.
 */
@Service
public class CharaButtonListenerCreator {

	@Inject
	private PartyManager partyManager;

	@Inject
	private GuardFactory guardFactory;

	@Inject
	private DodgeFactory dodgeFactory;

	@Inject
	private SlashSFactory slashSFactory;

	@Inject
	private SlashVFactory slashVFactory;

	@Inject
	private SlashFactory slashFactory;

	@Inject
	private TouchEventHandler touchEventHandler;

	@Inject
	private BattleConstantsHolder bch;

	/**
	 * ボタンのリスナを作成
	 *
	 * @param buttonObjectMap
	 */
	public void createListener(Map<String, ButtonObject> buttonObjectMap) {

		// ガード
		ButtonObject boR0 = buttonObjectMap.get("right_0");
		ButtonEventListener listenerR0 = createListenerR0();
		boR0.setButtonEventListener(listenerR0);
		touchEventHandler.addButtonObject(boR0);

		// 回避
		ButtonObject boR1 = buttonObjectMap.get("right_1");
		ButtonEventListener listenerR1 = createListenerR1();
		boR1.setButtonEventListener(listenerR1);
		touchEventHandler.addButtonObject(boR1);

		// スラッシュ強
		ButtonObject boR2 = buttonObjectMap.get("right_2");
		ButtonEventListener listenerR2 = createListenerR2();
		boR2.setButtonEventListener(listenerR2);
		touchEventHandler.addButtonObject(boR2);

		// スラッシュ
		ButtonObject boR3 = buttonObjectMap.get("right_3");
		ButtonEventListener listenerR3 = createListenerR3();
		boR3.setButtonEventListener(listenerR3);
		touchEventHandler.addButtonObject(boR3);
	}

	@NonNull
	private ButtonEventListener createListenerR3() {

		ButtonEventListener listener = new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {
				BattleChara battleChara = partyManager.getBattleParty().getCurrentBattleChara();

				if (battleChara == null) {
					return;
				}

				if (battleChara.getBattleMovingType() != BattleMovingType.ON_WAIT
						&& battleChara.getBattleMovingType() != BattleMovingType.ON_DODGE) {
					return;
				}

				MovingObject mo = battleChara.getMovingObject();
				MovingCombination mc = slashFactory.create(mo);
				mo.setMovingCombination(mc);
			}

			@Override
			public void release(MotionEvent event) {
			}
		};
		return listener;
	}

	@NonNull
	private ButtonEventListener createListenerR2() {

		ButtonEventListener listener = new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {
				BattleChara battleChara = partyManager.getBattleParty().getCurrentBattleChara();

				if (battleChara == null) {
					return;
				}

				if (battleChara.getBattleMovingType() != BattleMovingType.ON_WAIT
						&& battleChara.getBattleMovingType() != BattleMovingType.ON_DODGE) {
					return;
				}

				MovingObject mo = battleChara.getMovingObject();
				MovingCombination mc = slashSFactory.create(mo);
				mo.setMovingCombination(mc);
			}

			@Override
			public void release(MotionEvent event) {
			}
		};
		return listener;
	}

	@NonNull
	private ButtonEventListener createListenerR1() {
		return new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {
				BattleChara battleChara = partyManager.getBattleParty().getCurrentBattleChara();

				// WAIT中のみ
				if (battleChara.getBattleMovingType() == ON_WAIT) {
					MovingObject mo = battleChara.getMovingObject();

					// 微妙なタイミングですでにシェイクが発生している可能性がある
					// その場合は何もしない
					if ((mo.getMovingCombination() != null)
							&& (mo.getMovingCombination().getCurrentMovingType() == SHAKE_MOVE)) {
					} else {
						MovingCombination dodgeMc = dodgeFactory.createDodge(mo, bch.getStep(2), bch.getStepMulti(2), bch.getStep(2));
						mo.setMovingCombination(dodgeMc);
					}
				}
			}

			@Override
			public void release(MotionEvent event) {

				Log.d("charaButtonListener", "release!");

				BattleChara battleChara = partyManager.getBattleParty().getCurrentBattleChara();

				if (battleChara == null) {
					return;
				}

				// 回避前、回避中なら回避を終了させる
				MovingCombination mc = battleChara.getMovingObject().getMovingCombination();
				if (mc != null) {
					finishDodge(mc);
				}
			}

			/**
			 * 回避を終了させる
			 *
			 * @param mc
			 * @return
			 */
			private void finishDodge(MovingCombination mc) {
				List<MovingTarget> list = mc.getMovingTargetList();
				for (MovingTarget mt : list) {

					// 回避中があれば強制終了
					if (mt.getMovingType() == DODGE_MOVE) {
						mt.forcedFinish();
					}
				}
			}
		};
	}

	@NonNull
	private ButtonEventListener createListenerR0() {
		return new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {
				BattleChara battleChara = partyManager.getBattleParty().getCurrentBattleChara();

				if (battleChara == null) {
					return;
				}

				// WAIT中のみ
				if (battleChara.getBattleMovingType() == ON_WAIT) {
					MovingObject mo = battleChara.getMovingObject();

					// 微妙なタイミングですでにシェイクが発生している可能性がある
					// その場合は何もしない
					if ((mo.getMovingCombination() != null)
							&& (mo.getMovingCombination().getCurrentMovingType() == SHAKE_MOVE)) {
					} else {
						MovingCombination guardMc = guardFactory.createGuard(mo, bch.getStep());
						mo.setMovingCombination(guardMc);
					}
				}
			}

			@Override
			public void release(MotionEvent event) {
			}
		};
	}
}
