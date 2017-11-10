package com.tomneko.soulkingdom.view.battle.drawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.manager.DebugManager;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.drawer.ButtonDrawer;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;
import com.tomneko.soulkingdom.view.drawer.TextDrawer;

import java.util.List;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_WIDHT;
import static com.tomneko.soulkingdom.view.battle.manager.enums.BattleDebugType.MESSAGE;
import static com.tomneko.soulkingdom.view.battle.manager.enums.BattleDebugType.NONE;
import static com.tomneko.soulkingdom.view.battle.manager.enums.BattleDebugType.STATUS;

/**
 * デバッグ関連の描画
 * <p/>
 * Created by toyama on 2017/09/08.
 */
@Service
public class DebugDrawer {

	@Inject
	private ScaleCalculator sc;

	@Inject
	private ButtonDrawer buttonDrawer;

	@Inject
	private DebugManager debugManager;

	@Inject
	private TextDrawer textDrawer;

	// グリッド用の線
	private Paint gridLinePaint = null;

	// デバッグ用メッセージ
	private Paint messagePaint = null;

	// デバッグ文字のサイズ
	private static final int DEBUG_FONT_SIZE = 24;


	/**
	 * 初期化する
	 */
	private void initialize() {

		// グリッド用の線
		gridLinePaint = new Paint(ANTI_ALIAS_FLAG);
		gridLinePaint.setAntiAlias(true);
		gridLinePaint.setColor(Color.DKGRAY);
		gridLinePaint.setStrokeWidth(2);

		// デバッグ用メッセージ
		messagePaint = new Paint(ANTI_ALIAS_FLAG);
		messagePaint.setColor(Color.argb(196, 219, 36, 91));
		messagePaint.setTextSize(sc.getIntX(DEBUG_FONT_SIZE));
		messagePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
//
//		debugManager.getMessageList().add("０いろはにほへと");
//		debugManager.getMessageList().add("１ちりぬるを");
//		debugManager.getMessageList().add("２わかよたれそ");
//		debugManager.getMessageList().add("３つねならむ");
//		debugManager.getMessageList().add("４うゐのおくやま");
//		debugManager.getMessageList().add("５けふこえて");
//		debugManager.getMessageList().add("６あさきゆめみし");
//		debugManager.getMessageList().add("７ゑひもせす");
//		debugManager.getMessageList().add("８いろはにほへと");
//		debugManager.getMessageList().add("９ちりぬるを");
//		debugManager.getMessageList().add("０わかよたれそ");
//		debugManager.getMessageList().add("１つねならむ");
//		debugManager.getMessageList().add("２うゐのおくやま");
//		debugManager.getMessageList().add("３けふこえて");
//		debugManager.getMessageList().add("４あさきゆめみし");
//		debugManager.getMessageList().add("５ゑひもせす");
//		debugManager.getMessageList().add("６いろはにほへと");
//		debugManager.getMessageList().add("７ちりぬるを");
//		debugManager.getMessageList().add("８わかよたれそ");
//		debugManager.getMessageList().add("９つねならむ");
	}

	/**
	 * 描画
	 *
	 * @param canvas
	 */
	public void drawButton(Canvas canvas) {

		if (debugManager.getBattleDebugType() == NONE) {
			return;
		}

		for (ButtonObject bo : debugManager.getButtonObjectList()) {
			buttonDrawer.draw(canvas, bo);
		}
	}

	/**
	 * デバッグ用メッセージを表示
	 *
	 * @param canvas
	 */
	public void drawDebugMessage(Canvas canvas) {

		if (debugManager.getBattleDebugType() != MESSAGE
				&& debugManager.getBattleDebugType() != STATUS) {
			return;
		}

		float x = sc.getX((float) (BLOCK_WIDHT * 6.5));
		float y = sc.getY((float) (BLOCK_WIDHT * 1));

		// 初期化
		if (messagePaint == null) {
			initialize();
		}

		List<String> messageList = debugManager.getDebugMessageList();

		for (int i = 0; i < messageList.size(); i++) {
			String message = messageList.get(i);
			canvas.drawText(message, x, y, messagePaint);

			y += sc.getIntX(DEBUG_FONT_SIZE);
		}
	}

	/**
	 * グリッドを表示
	 */
	public void drawGrid(Canvas canvas) {

		if (debugManager.getBattleDebugType() == NONE) {
			return;
		}

		// 初期化
		if (gridLinePaint == null) {
			initialize();
		}

		// 縦線
		int count = 0;
		for (float x = 0; x <= sc.getScaledWidth(); ) {
			if (count % 2 == 0) {
				gridLinePaint.setStrokeWidth(2);
			} else {
				gridLinePaint.setStrokeWidth(1);
			}
			count++;
			canvas.drawLine(x, 0, x, sc.getScaledHeight(), gridLinePaint);
			x = x + (sc.getX(BLOCK_WIDHT));
		}

		count = 0;

		// 横線
		for (float y = 0; y <= sc.getScaledHeight(); ) {
			if (count % 2 == 0) {
				gridLinePaint.setStrokeWidth(2);
			} else {
				gridLinePaint.setStrokeWidth(1);
			}
			count++;
			canvas.drawLine(0, y, sc.getScaledWidth(), y, gridLinePaint);
			y = y + (sc.getY(BLOCK_WIDHT));
		}

	}
}
