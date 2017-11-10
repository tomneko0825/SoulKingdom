package com.tomneko.soulkingdom.view.battle.service;

import android.graphics.drawable.NinePatchDrawable;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleMember;
import com.tomneko.soulkingdom.view.battle.model.BattleMemberDrawingObject;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.button.enums.ButtonObjectType;
import com.tomneko.soulkingdom.view.button.enums.ButtonStateType;
import com.tomneko.soulkingdom.view.image.ImageHolder;
import com.tomneko.soulkingdom.view.image.enums.NinePatchButtonImageEnum;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * DrawingObjectListのCreator
 *
 * Created by toyama on 2017/09/23.
 */
@Service
public class DrawingObjectListCreator {

	@Inject
	private ScaleCalculator sc;

	@Inject
	private ImageHolder imageHolder;

	/**
	 * パーティの表示部分を作成
	 */
	public List<BattleMemberDrawingObject> createDrawingObjectList(int size, float y, float height, String buttonPrefix) {

		List<BattleMemberDrawingObject> drawingObjectList = new ArrayList();

		float viewWidth = sc.getViewWidth();

		float areaWidth = viewWidth / 16 * 10;

		// 一枠の大きさから作成
		float marginWidth = areaWidth / 3 / 10;

		float x = viewWidth / 16 * 3;
		float width = (areaWidth - (marginWidth * 4)) / 3;

		NinePatchDrawable buttonBlue = imageHolder.getNinePatchButtonImage(NinePatchButtonImageEnum.STATUS_BLUE);
		NinePatchDrawable buttonSilver = imageHolder.getNinePatchButtonImage(NinePatchButtonImageEnum.STATUS_SILVER);
		NinePatchDrawable buttonOrange = imageHolder.getNinePatchButtonImage(NinePatchButtonImageEnum.STATUS_ORANGE);

		for (int i = 0; i < size; i++) {

			// マージン
			x += marginWidth;

			ButtonObject bo = new ButtonObject();
			bo.setButtonObjectType(ButtonObjectType.IMAGE_NINE);
			bo.putNpdButton(ButtonStateType.NORMAL, buttonBlue);
			bo.putNpdButton(ButtonStateType.DISABLED, buttonSilver);
			bo.putNpdButton(ButtonStateType.HIGHLIGHT, buttonOrange);
			bo.setX(x);
			bo.setY(y);
			bo.setWidth(width);
			bo.setHeight(height);

			if (i == 0) {
				bo.setButtonStateType(ButtonStateType.HIGHLIGHT);
			}

			bo.setId(buttonPrefix + "_" + i);

			BattleMemberDrawingObject bmdo = new BattleMemberDrawingObject();
			bmdo.setButtonObject(bo);

			drawingObjectList.add(bmdo);

			x += width;
		}

		return drawingObjectList;
	}
}
