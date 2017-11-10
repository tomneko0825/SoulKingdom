package com.tomneko.soulkingdom.view.battle.listener;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.manager.PartyManager;
import com.tomneko.soulkingdom.view.battle.model.BattleMemberDrawingObject;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.hander.ButtonEventListener;
import com.tomneko.soulkingdom.view.hander.TouchEventHandler;
import com.tomneko.soulkingdom.view.moving.factory.chara.DodgeFactory;
import com.tomneko.soulkingdom.view.moving.factory.chara.GuardFactory;
import com.tomneko.soulkingdom.view.moving.factory.chara.SlashSFactory;
import com.tomneko.soulkingdom.view.moving.factory.chara.SlashVFactory;

import java.util.List;

/**
 * パーティ用リスナのCreator
 * <p/>
 * Created by toyama on 2017/09/24.
 */
@Service
public class PartyButtonListenerCreator {

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
	private TouchEventHandler touchEventHandler;

	/**
	 * ボタンのリスナを作成
	 *
	 * @param drawingObjectList
	 */
	public void createListener(List<BattleMemberDrawingObject> drawingObjectList) {

		for (BattleMemberDrawingObject bmdo : drawingObjectList) {
			ButtonObject bo = bmdo.getButtonObject();
			ButtonEventListener listener = createListener(bmdo);
			bo.setButtonEventListener(listener);
			touchEventHandler.addButtonObject(bo);
		}
	}

	@NonNull
	private ButtonEventListener createListener(final BattleMemberDrawingObject bmdo) {

		ButtonEventListener listener = new ButtonEventListener() {
			@Override
			public void onDown(MotionEvent event) {
				partyManager.changeChara(bmdo);
			}

			@Override
			public void release(MotionEvent event) {
			}
		};
		return listener;
	}

}
