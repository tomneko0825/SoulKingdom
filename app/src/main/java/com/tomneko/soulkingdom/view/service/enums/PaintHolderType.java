package com.tomneko.soulkingdom.view.service.enums;

import android.graphics.Color;
import android.graphics.Paint;

import static com.tomneko.soulkingdom.view.service.enums.PaintHolderFontType.NORMAL;
import static com.tomneko.soulkingdom.view.service.enums.PaintHolderFontType.SHADOW;

/**
 * PainterHolderにあるペインターの種類
 * <p/>
 * Created by toyama on 2017/09/21.
 */
public enum PaintHolderType {

	// アイキャッチ、ウェーブ
	EYE_CATCH_WAVE_TEXT(NORMAL, 96, Color.WHITE, "YEWBI___.ttf", Paint.Align.CENTER),

	// アイキャッチ、ウェーブ
	EYE_CATCH_WAVE_TEXT_SHADOW(SHADOW, 96, Color.DKGRAY, "YEWBI___.ttf", Paint.Align.CENTER),

	// アイキャッチ、スタート
	EYE_CATCH_START_TEXT(NORMAL, 96, Color.YELLOW, "YEWBI___.ttf", Paint.Align.CENTER),

	// アイキャッチ、スタート
	EYE_CATCH_START_TEXT_SHADOW(SHADOW, 96, Color.WHITE, "YEWBI___.ttf", Paint.Align.CENTER),

	// 戦闘背景、ウェーブ
	BATTLE_WAVE_TEXT(NORMAL, 48, Color.WHITE, "YEWBI___.ttf"),

	// 戦闘背景、ウェーブ
	BATTLE_WAVE_TEXT_SHADOW(SHADOW, 48, Color.DKGRAY, "YEWBI___.ttf"),


	// ガードのフラッシュ文字
	BATTLE_FLUSH_GUARD_TEXT(NORMAL, 24, Color.YELLOW, "YEWBI___.ttf"),

	// ガードのフラッシュ文字
	BATTLE_FLUSH_DODGE_TEXT(NORMAL, 24, Color.CYAN, "YEWBI___.ttf"),

	// ガード、回避のフラッシュ文字の影
	BATTLE_FLUSH_TEXT_SHADOW(SHADOW, 24, Color.LTGRAY, "YEWBI___.ttf"),

	// 戦闘のHP文字
	BATTLE_HP(NORMAL, 32, Color.WHITE, "KaushanScript-Regular.otf", Paint.Align.RIGHT),

	// 戦闘のHP文字の影
	BATTLE_HP_SHADOW(SHADOW, 32, Color.DKGRAY, "KaushanScript-Regular.otf", Paint.Align.RIGHT),

	// ダメージのHP文字
	DAMAGE_HP(NORMAL, 32, Color.WHITE, "KaushanScript-Regular.otf", Paint.Align.CENTER),

	// ダメージのHP文字の影
	DAMAGE_HP_SHADOW(SHADOW, 32, Color.DKGRAY, "KaushanScript-Regular.otf", Paint.Align.CENTER),

	// 敵の名前
	BATTLE_ENEMY_NAME(NORMAL, 24, Color.WHITE, "logo_type_gothic.otf"),

	// 敵の名前の影
	BATTLE_ENEMY_NAME_SHADOW(SHADOW, 24, Color.DKGRAY, "logo_type_gothic.otf"),;

	private PaintHolderFontType paintHolderFontType;

	private float textSize;

	private int color;

	private String typefaceName;

	private Paint.Align align = Paint.Align.LEFT;

	public PaintHolderFontType getPaintHolderFontType() {
		return paintHolderFontType;
	}

	public float getTextSize() {
		return textSize;
	}

	public int getColor() {
		return color;
	}

	public String getTypefaceName() {
		return typefaceName;
	}

	public Paint.Align getAlign() {
		return align;
	}

	private PaintHolderType(PaintHolderFontType paintHolderFontType, float textSize, int color, String typefaceName) {
		this.paintHolderFontType = paintHolderFontType;
		this.textSize = textSize;
		this.color = color;
		this.typefaceName = typefaceName;
	}

	private PaintHolderType(PaintHolderFontType paintHolderFontType, float textSize, int color, String typefaceName, Paint.Align align) {
		this(paintHolderFontType, textSize, color, typefaceName);
		this.align = align;
	}
}
