package com.tomneko.soulkingdom.view.button;

import android.graphics.drawable.NinePatchDrawable;

import com.tomneko.soulkingdom.view.button.enums.ButtonObjectType;
import com.tomneko.soulkingdom.view.button.enums.ButtonStateType;
import com.tomneko.soulkingdom.view.hander.ButtonEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * ボタン
 * <p/>
 * Created by toyama on 2017/09/02.
 */
public class ButtonObject {

	// ボタンが有効かどうか
	private boolean enabled = true;

	// ボタンの表示、非表示
	private boolean visible = true;

	// 現在のボタンの状態
	private ButtonStateType buttonStateType = ButtonStateType.NORMAL;

	// ボタンの種類
	private ButtonObjectType buttonObjectType;

	private float x;

	private float y;

	private float width;

	private float height;

	private String id;

	private Map<ButtonStateType, NinePatchDrawable> npdButtonMap = new HashMap();

	// ボタンのイベントリスナ
	private ButtonEventListener buttonEventListener;

	// ボタンのテキストの描画方法
	private ButtonTextDrawer buttonTextDrawer;

	public ButtonStateType getButtonStateType() {
		return buttonStateType;
	}

	public void setButtonStateType(ButtonStateType buttonStateType) {
		this.buttonStateType = buttonStateType;
	}

	public void putNpdButton(ButtonStateType buttonStateType, NinePatchDrawable npd) {
		npdButtonMap.put(buttonStateType, npd);
	}

	/**
	 * 現在表示すべきNPD
	 *
	 * @return
	 */
	public NinePatchDrawable getCurrentNpd() {
		return npdButtonMap.get(buttonStateType);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ButtonObjectType getButtonObjectType() {
		return buttonObjectType;
	}

	public void setButtonObjectType(ButtonObjectType buttonObjectType) {
		this.buttonObjectType = buttonObjectType;
	}

	public ButtonEventListener getButtonEventListener() {
		return buttonEventListener;
	}

	public void setButtonEventListener(ButtonEventListener buttonEventListener) {
		this.buttonEventListener = buttonEventListener;
	}

	public ButtonTextDrawer getButtonTextDrawer() {
		return buttonTextDrawer;
	}

	public void setButtonTextDrawer(ButtonTextDrawer buttonTextDrawer) {
		this.buttonTextDrawer = buttonTextDrawer;
	}

	/**
	 * 指定された座標が含まれているかどうか
	 *
	 * @param posX
	 * @param posY
	 * @return
	 */
	public boolean contains(float posX, float posY) {

		if (posX < x) {
			return false;
		}

		if (x + width < posX) {
			return false;
		}

		if (posY < y) {
			return false;
		}

		if (y + height < posY) {
			return false;
		}

		return true;
	}
}
