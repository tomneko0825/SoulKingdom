package com.tomneko.soulkingdom.view.battle.manager;

import android.graphics.drawable.NinePatchDrawable;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.listener.CharaButtonListenerCreator;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.button.enums.ButtonObjectType;
import com.tomneko.soulkingdom.view.button.enums.ButtonStateType;
import com.tomneko.soulkingdom.view.image.ImageHolder;
import com.tomneko.soulkingdom.view.drawer.ButtonDrawer;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_COUNT;
import static com.tomneko.soulkingdom.view.image.enums.NinePatchButtonImageEnum.*;

/**
 * 戦闘用ボタンの管理
 * <p/>
 * Created by toyama on 2017/09/02.
 */
@Service
public class BattleButtonManager {

	@Inject
	private ButtonDrawer buttonDrawer;

	@Inject
	private ImageHolder imageHolder;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private CharaButtonListenerCreator charaButtonListenerCreator;

	private List<ButtonObject> buttonObjectList = new ArrayList();


	public List<ButtonObject> getButtonObjectList() {
		return buttonObjectList;
	}

	/**
	 * 戦闘用ボタン群の作成
	 */
	public void createButtons() {

		Map<String, ButtonObject> buttonObjectMap = createButtonObjectMap();
		charaButtonListenerCreator.createListener(buttonObjectMap);
	}

	private Map<String, ButtonObject> createButtonObjectMap() {

		Map<String, ButtonObject> buttonObjectMap = new HashMap();

		// 画像の読み込み
		NinePatchDrawable buttonR = imageHolder.getNinePatchButtonImage(BLUE_CIRCLE_R);
		NinePatchDrawable buttonL = imageHolder.getNinePatchButtonImage(BLUE_CIRCLE_L);

		float y = 0;

		float viewWidth = sc.getViewWidth();
		float viewHeight = sc.getViewHeight();

		float tmpHeight = viewHeight / 4;

		float buttonHeight = tmpHeight - (tmpHeight / 10);
		float margin = (viewHeight - (buttonHeight * 4)) / 5;
		float buttonWidth = (viewWidth / BLOCK_COUNT) * 3 - (margin);

		float leftX = 0;
		float rightX = (viewWidth / BLOCK_COUNT) * 13 + margin;

		// 左側
		for (int i = 0; i < 4; i++) {

			// マージン
			y += margin;

			ButtonObject bo = new ButtonObject();
			bo.setButtonObjectType(ButtonObjectType.IMAGE_NINE);
			bo.putNpdButton(ButtonStateType.NORMAL, buttonL);
			bo.setX(leftX);
			bo.setY(y);
			bo.setWidth(buttonWidth);
			bo.setHeight(buttonHeight);

			bo.setId("left_" + i);

			buttonObjectMap.put(bo.getId(), bo);
			buttonObjectList.add(bo);

			y += buttonHeight;
		}

		// 右側
		y = 0;
		for (int i = 0; i < 4; i++) {

			// マージン
			y += margin;

			ButtonObject bo = new ButtonObject();
			bo.setButtonObjectType(ButtonObjectType.IMAGE);
			bo.putNpdButton(ButtonStateType.NORMAL, buttonR);
			bo.setX(rightX);
			bo.setY(y);
			bo.setWidth(buttonWidth);
			bo.setHeight(buttonHeight);

			bo.setId("right_" + i);

			buttonObjectMap.put(bo.getId(), bo);
			buttonObjectList.add(bo);

			y += buttonHeight;
		}

		return buttonObjectMap;
	}
}
