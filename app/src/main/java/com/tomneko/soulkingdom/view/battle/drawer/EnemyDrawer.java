package com.tomneko.soulkingdom.view.battle.drawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.BattleStageConstatns;
import com.tomneko.soulkingdom.view.battle.manager.EnemyManager;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemy;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemyGroup;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemyTeam;
import com.tomneko.soulkingdom.view.battle.model.BattleMember;
import com.tomneko.soulkingdom.view.battle.model.BattleMemberDrawingObject;
import com.tomneko.soulkingdom.view.battle.service.DrawingObjectListCreator;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.button.enums.ButtonObjectType;
import com.tomneko.soulkingdom.view.drawer.ButtonDrawer;
import com.tomneko.soulkingdom.view.drawer.HpBarDrawer;
import com.tomneko.soulkingdom.view.drawer.MovingObjectDrawer;
import com.tomneko.soulkingdom.view.drawer.TextDrawer;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.service.PaintHolder;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.*;
import static com.tomneko.soulkingdom.view.service.enums.PaintHolderType.BATTLE_ENEMY_NAME;
import static com.tomneko.soulkingdom.view.service.enums.PaintHolderType.BATTLE_ENEMY_NAME_SHADOW;

/**
 * 敵の描画
 *
 * Created by toyama on 2017/09/08.
 */
@Service
public class EnemyDrawer {

	private static final int BAR_FRAME_LEFT = 10;
	private static final int BAR_FRAME_RIGHT = 10;
	private static final int BAR_FRAME_BOTTOM = 8;

	@Inject
	private EnemyManager enemyManager;

	@Inject
	private ButtonDrawer buttonDrawer;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private MovingObjectDrawer movingObjectDrawer;

	@Inject
	private TextDrawer textDrawer;

	@Inject
	private PaintHolder paintHolder;

	@Inject
	private HpBarDrawer hpBarDrawer;


	/**
	 * 敵を描画
	 *
	 * @param canvas
	 */
	public void drawEnemy(Canvas canvas) {

		for (BattleMemberDrawingObject bmdo : enemyManager.getDrawingObjectList()) {

			if (bmdo.getButtonObject().isVisible() == false) {
				return;
			}

			ButtonObject bo = bmdo.getButtonObject();

			// ボタンの画像
			buttonDrawer.draw(canvas, bo);

			// 敵の描画
			BattleEnemy battleEnemy = (BattleEnemy)bmdo.getBattleMember();
			MovingObject mo = battleEnemy.getMovingObject();
			movingObjectDrawer.drawMovingObject(canvas, mo);
 			// canvas.drawBitmap(mo.getImage(), mo.getX(), mo.getY(), null);

			// 数値
			// TODO とりあえず
			String text = "イロハニホヘトチ";
			float textX = bo.getX() + sc.getX(6);
			float textY = bo.getY() + bo.getHeight() - sc.getY(32);

			textDrawer.drawDecorationText(canvas, text, textX, textY, BATTLE_ENEMY_NAME, BATTLE_ENEMY_NAME_SHADOW);

			// HPバーの描画
			float percentage = battleEnemy.getBattleMemberStatus().getHpPercentage();
			drawBar(canvas, bo, percentage);

			// 枠
			drawBarFrame(canvas, bo);
		}
	}


	/**
	 * バーを作成
	 *
	 * @param canvas
	 * @param bo
	 * @param percentage
	 */
	private void drawBar(Canvas canvas, ButtonObject bo, float percentage) {

		// バー
		final int BAR_MARGIN_X = 2;
		final int BAR_MARGIN_Y = 2;
		float barLeft = bo.getX() + sc.getX(BAR_FRAME_LEFT) + sc.getX(BAR_MARGIN_X);
		float barRight = bo.getX() + bo.getWidth() - sc.getX(BAR_FRAME_RIGHT) - sc.getX(BAR_MARGIN_X);
		barRight = (barRight - barLeft) * percentage + barLeft;
		float barBottom = bo.getY() + bo.getHeight() - sc.getY(BAR_FRAME_BOTTOM) - sc.getX(BAR_MARGIN_Y);
		float barTop = barBottom - sc.getY(12);

		// 矩形を作成
		RectF rect = new RectF(barLeft, barTop, barRight, barBottom);

		// グラデーションの作成
		int[] colors = {PartyDrawer.HP_BAR_COLOR1, PartyDrawer.HP_BAR_COLOR2};

		hpBarDrawer.drawBar(canvas, rect, colors, false);
	}

	/**
	 * バーの枠を作成
	 *
	 * @param canvas
	 * @param bo
	 */
	private void drawBarFrame(Canvas canvas, ButtonObject bo) {

		float barLeft = bo.getX() + sc.getX(BAR_FRAME_LEFT);
		float barRight = bo.getX() + bo.getWidth() - sc.getX(BAR_FRAME_RIGHT);
		float barBottom = bo.getY() + bo.getHeight() - sc.getY(BAR_FRAME_BOTTOM);
		float barTop = barBottom - sc.getY(14);

		// 矩形を作成
		RectF rect = new RectF(barLeft, barTop, barRight, barBottom);

		// グラデーションの作成
		int[] colors = {Color.DKGRAY, Color.WHITE, Color.DKGRAY};

		hpBarDrawer.drawBar(canvas, rect, colors, true);
	}

}
