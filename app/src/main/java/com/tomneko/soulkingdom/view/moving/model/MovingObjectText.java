package com.tomneko.soulkingdom.view.moving.model;

import com.tomneko.soulkingdom.view.service.enums.PaintHolderType;

/**
 * テキストの表示
 * <p/>
 * Created by toyama on 2017/09/30.
 */
public class MovingObjectText {

	private String text;

	private PaintHolderType paintTypeNormal;

	private PaintHolderType paintTypeShadow;

	public MovingObjectText(String text, PaintHolderType paintTypeNormal, PaintHolderType paintTypeShadow) {
		this.text = text;
		this.paintTypeNormal = paintTypeNormal;
		this.paintTypeShadow = paintTypeShadow;
	}

	public String getText() {
		return text;
	}

	public PaintHolderType getPaintTypeNormal() {
		return paintTypeNormal;
	}

	public PaintHolderType getPaintTypeShadow() {
		return paintTypeShadow;
	}
}
