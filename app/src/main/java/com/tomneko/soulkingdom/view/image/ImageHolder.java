package com.tomneko.soulkingdom.view.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.content.res.ResourcesCompat;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.image.enums.BackBattleImageEnum;
import com.tomneko.soulkingdom.view.image.enums.ButtonImageEnum;
import com.tomneko.soulkingdom.view.image.enums.CharaImageEnum;
import com.tomneko.soulkingdom.view.image.enums.EffectImageEnum;
import com.tomneko.soulkingdom.view.image.enums.EnemyImageEnum;
import com.tomneko.soulkingdom.view.image.enums.NinePatchButtonImageEnum;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import java.util.HashMap;
import java.util.Map;

/**
 * イメージのホルダー
 * <p>
 * Created by toyama on 2017/09/10.
 */
@Service
public class ImageHolder {

	@Inject
	private ScaleCalculator sc;

	private Map<BackBattleImageEnum, Bitmap> backBattleImageEnumBitmapMap = new HashMap();

	private Map<ButtonImageEnum, Bitmap> buttonImageEnumBitmapMap = new HashMap();

	private Map<NinePatchButtonImageEnum, NinePatchDrawable> ninePatchButtonImageEnumBitmapMap = new HashMap();

	private Map<CharaImageEnum, Bitmap> charaImageEnumBitmapMap = new HashMap();

	private Map<EffectImageEnum, Bitmap> effectImageEnumBitmapMap = new HashMap();

	private Map<EnemyImageEnum, Bitmap> enemyImageEnumBitmapMap = new HashMap();

	private Resources resources;

	public Bitmap getBackBattleImage(BackBattleImageEnum bbie) {
		return backBattleImageEnumBitmapMap.get(bbie);
	}

	public Bitmap getButtonImage(ButtonImageEnum bie) {
		return buttonImageEnumBitmapMap.get(bie);
	}

	public NinePatchDrawable getNinePatchButtonImage(NinePatchButtonImageEnum npbie) {
		return ninePatchButtonImageEnumBitmapMap.get(npbie);
	}

	public Bitmap getCharaImage(CharaImageEnum cie) {
		return charaImageEnumBitmapMap.get(cie);
	}

	public Bitmap getEnemyImage(EnemyImageEnum eie) {
		return enemyImageEnumBitmapMap.get(eie);
	}

	public Bitmap getEffectImage(EffectImageEnum eie) {
		return effectImageEnumBitmapMap.get(eie);
	}


	/**
	 * 画像をロードする
	 *
	 * @param resources
	 */
	public void load(Resources resources) {

		if (this.resources == null) {
			this.resources = resources;
		}

		// TODO とりあえず全部ロードする
		loadBackBattleImageMap();
		loadButtonImageMap();
		loadNinePatchButtonImageMap();
		loadCharaImageMap();
		loadEnemyImageMap();
		loadEffectImageMap();

	}


	/**
	 * 戦闘背景のロード
	 */
	private void loadBackBattleImageMap() {
		for (BackBattleImageEnum bbie : BackBattleImageEnum.values()) {
			Bitmap bmp = loadBackImage(bbie.getId());
			backBattleImageEnumBitmapMap.put(bbie, bmp);
		}
	}

	/**
	 * ボタンのロード
	 */
	private void loadButtonImageMap() {
		for (ButtonImageEnum bie : ButtonImageEnum.values()) {
			Bitmap bmp = loadImage(bie.getId());
			buttonImageEnumBitmapMap.put(bie, bmp);
		}
	}

	/**
	 * 9-patchボタンのロード
	 */
	private void loadNinePatchButtonImageMap() {
		for (NinePatchButtonImageEnum bie : NinePatchButtonImageEnum.values()) {
			NinePatchDrawable npd = loadNinePatchImage(bie.getId());
			ninePatchButtonImageEnumBitmapMap.put(bie, npd);
		}
	}

	/**
	 * キャラのロード
	 */
	private void loadCharaImageMap() {
		for (CharaImageEnum cie : CharaImageEnum.values()) {
			Bitmap bmp = loadImage(cie.getId());
			charaImageEnumBitmapMap.put(cie, bmp);
		}
	}

	/**
	 * 敵のロード
	 */
	private void loadEnemyImageMap() {
		for (EnemyImageEnum eie : EnemyImageEnum.values()) {
			Bitmap bmp = loadImage(eie.getId());
			enemyImageEnumBitmapMap.put(eie, bmp);
		}
	}

	/**
	 * 敵のロード
	 */
	private void loadEffectImageMap() {
		for (EffectImageEnum eie : EffectImageEnum.values()) {
			Bitmap bmp = loadImage(eie.getId());
			effectImageEnumBitmapMap.put(eie, bmp);
		}
	}


	/**
	 * 画像のロード
	 *
	 * @param id
	 * @return
	 */
	private Bitmap loadImage(int id) {

		Bitmap image = BitmapFactory.decodeResource(resources, id);

		// スケールによるサイズ調整
		return Bitmap.createScaledBitmap(image, sc.getIntX(image.getWidth()), sc.getIntY(image.getHeight()), true);
	}

	/**
	 * 9-patch画像のロード
	 *
	 * @param id
	 * @return
	 */
	private NinePatchDrawable loadNinePatchImage(int id) {

		// 画像の読み込み
		return (NinePatchDrawable) ResourcesCompat.getDrawable(resources, id, null);
	}

	/**
	 * 背景のロード
	 *
	 * @param id
	 * @return
	 */
	private Bitmap loadBackImage(int id) {

		Bitmap background = BitmapFactory.decodeResource(resources, id);

		// 背景に大きさを合わせる
		return Bitmap.createScaledBitmap(background, (int) sc.getViewWidth(), (int) sc.getViewHeight(), true);
	}

}
