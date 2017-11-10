package com.tomneko.soulkingdom.view.battle.drawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.manager.CharaManager;
import com.tomneko.soulkingdom.view.battle.manager.PartyManager;
import com.tomneko.soulkingdom.view.battle.model.BattleChara;
import com.tomneko.soulkingdom.view.battle.model.BattleMemberDrawingObject;
import com.tomneko.soulkingdom.view.battle.model.FlashStatus;
import com.tomneko.soulkingdom.view.battle.model.FlashStatusCounter;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.drawer.MovingObjectDrawer;
import com.tomneko.soulkingdom.view.drawer.TextDrawer;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.service.PaintHolder;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;
import com.tomneko.soulkingdom.view.service.enums.PaintHolderType;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_COUNT;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_WIDHT;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.CHARA_BASE_X;
import static com.tomneko.soulkingdom.view.battle.model.enums.FlashStatusType.DODGE_FLUSH;
import static com.tomneko.soulkingdom.view.battle.model.enums.FlashStatusType.GUARD_FLUSH;
import static com.tomneko.soulkingdom.view.service.enums.PaintHolderType.BATTLE_FLUSH_DODGE_TEXT;
import static com.tomneko.soulkingdom.view.service.enums.PaintHolderType.BATTLE_FLUSH_GUARD_TEXT;
import static com.tomneko.soulkingdom.view.service.enums.PaintHolderType.BATTLE_FLUSH_TEXT_SHADOW;

/**
 * 戦闘キャラの描写
 * <p/>
 * Created by toyama on 2017/09/08.
 */
@Service
public class CharaDrawer {

	@Inject
	private CharaManager charaManager;

	@Inject
	private PartyManager partyManager;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private TextDrawer textDrawer;

	@Inject
	private MovingObjectDrawer movingObjectDrawer;

	@Inject
	private PaintHolder paintHolder;

	/**
	 * キャラを描画
	 *
	 * @param canvas
	 */
	public void drawChara(Canvas canvas) {

		for (BattleMemberDrawingObject bmdo : partyManager.getDrawingObjectList()) {

			BattleChara battleChara = (BattleChara) bmdo.getBattleMember();
			MovingObject mo = battleChara.getMovingObject();

			// 控えはボタンの位置に描画
			if (battleChara.getBattleMovingType() == BattleMovingType.ON_RESERVE) {

				// TODO これは初期設定でやりたい
				ButtonObject bo = bmdo.getButtonObject();
				float left = bo.getX();
				float top = bo.getY();
				mo.setX(left);
				mo.setY(top - sc.getY(45));
				mo.setTileX(0);
				mo.setTileY(0);
				movingObjectDrawer.drawMovingObject(canvas, mo);

			} else {

				movingObjectDrawer.drawMovingObject(canvas, mo);

				if (battleChara.isOnBattle()) {
					// フラッシュステータス
					drawFlushStatus(canvas, battleChara);
				}
			}
		}
	}

	/**
	 * フラッシュステータスを描画
	 *
	 * @param canvas
	 * @param battleChara
	 */
	private void drawFlushStatus(Canvas canvas, BattleChara battleChara) {

		FlashStatus flashStatus = battleChara.getFlashStatus();

		// ガード
		FlashStatusCounter guardCounter = flashStatus.getCounter(GUARD_FLUSH);
		if (guardCounter != null) {

			float baseY = sc.getY(BLOCK_WIDHT) * 4;
			int textColor = Color.YELLOW;
			int barColor = Color.rgb(255, 69, 0);
			drawFlushStatus(canvas, guardCounter, "Guard", baseY, textColor, barColor);
		}

		// 回避
		FlashStatusCounter dodgeCounter = flashStatus.getCounter(DODGE_FLUSH);
		if (dodgeCounter != null) {
			float baseY = sc.getY(BLOCK_WIDHT) * 3;
			int textColor = Color.CYAN;
			int barColor = Color.rgb(0, 0, 69);
			drawFlushStatus(canvas, dodgeCounter, "Dodge", baseY, textColor, barColor);
		}

	}

	/**
	 * フラッシュステータスを描画
	 *
	 * @param canvas
	 * @param flushCounter
	 * @param text
	 * @param baseY
	 * @param textColor
	 * @param barColor
	 */
	private void drawFlushStatus(Canvas canvas, FlashStatusCounter flushCounter, String text, float baseY, int textColor, int barColor) {

		// 文字
		float baseX = sc.getViewWidth() / BLOCK_COUNT * CHARA_BASE_X + sc.getX(BLOCK_WIDHT / 4);

		PaintHolderType type = BATTLE_FLUSH_GUARD_TEXT;
		if (flushCounter.getFlashStatusType() == DODGE_FLUSH) {
			type = BATTLE_FLUSH_DODGE_TEXT;
		}

		textDrawer.drawDecorationText(canvas, text, baseX, baseY, type, BATTLE_FLUSH_TEXT_SHADOW);

		// メーター
		float right = baseX;
		float bottom = baseY;
		float left = right - sc.getX(flushCounter.getRestCount() * 8);
		float top = bottom - sc.getY(16);

		// 矩形を作成
		RectF rect = new RectF(left, top, right, bottom);

		Paint paint = new Paint(ANTI_ALIAS_FLAG);

		// グラデーションの作成
		int[] colors = {Color.WHITE, barColor};

		float gradientTop = top;
		float gradientBottom = bottom;

		if (flushCounter.getRestCount() % 2 == 0) {
			gradientTop = bottom;
			gradientBottom = top;
		}

		LinearGradient lig = new LinearGradient(0, gradientTop, 0, gradientBottom, colors, null, Shader.TileMode.CLAMP);
		paint.setShader(lig);

		// 半透明
		paint.setAlpha(192);

		// バーを描画
		canvas.drawRect(rect, paint);
	}

}

