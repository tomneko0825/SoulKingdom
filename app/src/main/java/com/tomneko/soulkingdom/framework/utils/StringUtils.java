package com.tomneko.soulkingdom.framework.utils;

/**
 * Stringのユーティリティ
 * <p/>
 * Created by toyama on 2017/10/21.
 */
public class StringUtils {

	/**
	 * スネークケースをキャメルケースに変換
	 *
	 * @param str
	 * @return
	 */
	public static String toCamelCase(String str) {

		// いったん全部小文字に
		str = str.toLowerCase();

		String ret = "";

		ret = str.substring(0, 1).toUpperCase();

		boolean underScore = false;

		for (int i = 1; i < str.length(); i++) {

			String c = str.substring(i, i + 1);

			// アンダースコアならパス
			if (c.equals("_")) {
				underScore = true;
				continue;
			}

			// 前回アンダースコアだったら大文字に
			if (underScore) {
				c = c.toUpperCase();
			}

			ret += c;

			underScore = false;
		}

		return ret;
	}

	public static void main(String[] args) {
		String str = "WILD_WOLF_IMG";
		System.out.println(StringUtils.toCamelCase(str));
	}


}
