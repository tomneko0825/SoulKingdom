package com.tomneko.soulkingdom.view.battle.drawer;

import android.graphics.Canvas;
import android.graphics.Color;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.BattleStageConstatns;
import com.tomneko.soulkingdom.view.battle.manager.BattleStageManager;
import com.tomneko.soulkingdom.view.battle.model.BattleStage;
import com.tomneko.soulkingdom.view.drawer.TextDrawer;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;
import com.tomneko.soulkingdom.view.service.enums.PaintHolderType;

/**
 * 戦闘ステージ描画用
 * <p/>
 * Created by toyama on 2017/09/01.
 */
@Service
public class BattleStageDrawer {

	@Inject
	private BattleStageManager battleStageManager;

	@Inject
	private TextDrawer textDrawer;

	@Inject
	private ScaleCalculator sc;

	/**
	 * 背景の描画
	 *
	 * @param canvas
	 */
	public void drawBackground(Canvas canvas) {

		canvas.drawColor(Color.WHITE);

		// 背景の描画
		canvas.drawBitmap(battleStageManager.getBackground(), 0, 0, null);

		// ウェーブの描画
		BattleStage battleStage = battleStageManager.getBattleStage();
		String text = "WAVE " + battleStage.getCurrentWave() + "/" + battleStage.getMaxWave();

		float textX = sc.getX(BattleStageConstatns.BLOCK_WIDHT * 12);
		float textY = sc.getY(60);

		PaintHolderType type = PaintHolderType.BATTLE_WAVE_TEXT;
		PaintHolderType shadowType = PaintHolderType.BATTLE_WAVE_TEXT_SHADOW;
		textDrawer.drawDecorationText(canvas, text, textX, textY, type, shadowType);
	}
}
