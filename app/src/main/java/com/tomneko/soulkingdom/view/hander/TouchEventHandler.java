package com.tomneko.soulkingdom.view.hander;

import android.util.Log;
import android.view.MotionEvent;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.drawer.ButtonDrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

/**
 * タッチイベントのハンドラ
 * <p>
 * Created by toyama on 2017/09/13.
 */
@Service
public class TouchEventHandler {

	@Inject
	private ButtonDrawer buttonDrawer;

	private List<ButtonObject> buttonObjectList = new ArrayList();

	// 押下中のボタンをキー、ポインタIDのセット
	private Map<ButtonObject, Set<Integer>> onDownButtonMap = new HashMap();

	/**
	 * ボタンを追加
	 *
	 * @param bo
	 */
	public void addButtonObject(ButtonObject bo) {
		buttonObjectList.add(bo);
	}

	/**
	 * 指定された位置に含まれるオブジェクトを取得
	 *
	 * @param posX
	 * @param posY
	 * @return
	 */
	public ButtonObject contains(float posX, float posY) {
		return buttonDrawer.contains(buttonObjectList, posX, posY);
	}

	/**
	 * タッチイベント
	 *
	 * @param event
	 * @return
	 */
	public boolean onTouchEvent(MotionEvent event) {

		// TODO とりあえずボタンのみ
		ButtonObject bo = contains(event.getX(), event.getY());

		int action = event.getAction();
		int index = event.getActionIndex();
		int pointerId = event.getPointerId(index);

		// 対応するボタンがない場合はリリース
		if (bo == null) {
			release(event, pointerId);
			return true;
		}

		Log.d("onTouch", "boId---" + bo.getId() + ":" + (action & MotionEvent.ACTION_MASK));


		Set<Integer> pointerIdSet = onDownButtonMap.get(bo);

		switch (action & MotionEvent.ACTION_MASK) {

			case ACTION_DOWN:
			case ACTION_POINTER_DOWN:
				// down処理

				// 新規down
				if (pointerIdSet == null) {
					pointerIdSet = new HashSet();
					pointerIdSet.add(pointerId);
					onDownButtonMap.put(bo, pointerIdSet);

					// downイベント
					bo.getButtonEventListener().onDown(event);
				}

				// 追加downは何もしない
				else {
					// 同じポインタはありえないと思うけどとりあえず追加
					pointerIdSet.add(pointerId);
				}

				break;
			case ACTION_MOVE:

				// move対象がdownされていない
				// または、このポインタIDでdownされていない場合
				if (pointerIdSet == null
						|| (pointerIdSet != null && !pointerIdSet.contains(pointerId))) {

					// リリース処理
					release(event, pointerId);
				}

				break;
			case ACTION_UP:
			case ACTION_POINTER_UP:
			case ACTION_CANCEL:

				// リリース処理
				release(event, pointerId);

				break;
			default:
		}
		return true;
	}

	/**
	 * リリース処理
	 *
	 * @param event
	 * @param pointerId
	 */
	private void release(MotionEvent event, int pointerId) {

		// 登録されているボタンdown情報をリリース＆削除
		List<ButtonObject> deleteBoList = new ArrayList();
		for (ButtonObject releaseBo : onDownButtonMap.keySet()) {

			Set<Integer> pointerIdSet = onDownButtonMap.get(releaseBo);
			pointerIdSet.remove(pointerId);

			// ポインタ情報がなくなったらリリース＆削除対象
			if (pointerIdSet.size() == 0) {

				// リリース
				releaseBo.getButtonEventListener().release(event);

				deleteBoList.add(releaseBo);
			}
		}

		// 削除
		for (ButtonObject deleteBo : deleteBoList) {
			onDownButtonMap.remove(deleteBo);
		}
	}

}
