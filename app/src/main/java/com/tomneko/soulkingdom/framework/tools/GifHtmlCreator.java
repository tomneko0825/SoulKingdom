package com.tomneko.soulkingdom.framework.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * gifファイルの一覧HTMLを作成
 * <p>
 * Created by toyama on 2017/09/14.
 */
public class GifHtmlCreator {

	public static void main(String[] args) {

		// 探索対象のディレクトリ
		String dirPath = "E:\\work\\20170815_allAboutSoul\\effect";

		// ターゲットとなるディレクトリ名
		Set<String> targetDirNameSet = new HashSet();
		targetDirNameSet.add("アニメGIF");

		// 出力するhtmlファイル
		String htmlFilePath = "E:\\work\\20170815_allAboutSoul\\effect\\allGifIndex.html";

		GifHtmlCreator creator = new GifHtmlCreator();
		creator.create(dirPath, targetDirNameSet, htmlFilePath);


	}

	public void create(String dirPath, Set<String> targetDirNameSet, String htmlFilePath) {

		Map<File, List<File>> fileList = createFileList(dirPath, targetDirNameSet);

		createHtml(fileList, htmlFilePath);

	}

	private void createHtml(Map<File, List<File>> fileList, String htmlFilePath) {

		List<String> textList = createHtmlText(fileList);

		writeText(htmlFilePath, textList);

	}

	private void writeText(String htmlFilePath, List<String> textList) {


		for (String text : textList) {

			System.out.println(text);
		}

	}

	private List<String> createHtmlText(Map<File, List<File>> fileList) {

		List<String> textList = new ArrayList();

		textList.add("<html>");
		textList.add("<body>");

		for (File dir : fileList.keySet()) {

			textList.add(dir.getPath() + "</br>");

			for (File file : fileList.get(dir)) {
				textList.add("<img src=\"file://" + file.getPath() + "\" title=\"" + file.getName() + "\"/>");
			}

			textList.add("</br>");
			textList.add("</br>");
		}

		textList.add("</body>");
		textList.add("</html>");

		return textList;
	}


	/**
	 * ディレクトリ内のファイルのリストを作成
	 *
	 * @param dir
	 * @return
	 */
	private List<File> createFileList(File dir) {

		List<File> list = new ArrayList();

		for (File file : dir.listFiles()) {

			// gifなら
			// anime gifでないけどjpgも
			if (file.getName().endsWith(".gif")
					|| file.getName().endsWith(".jpg")) {
				list.add(file);
			}
		}
		return list;
	}

	/**
	 * 対象のファイルのリストを作成
	 *
	 * @param dirPath
	 * @param targetDirNameSet
	 * @return
	 */
	private Map<File, List<File>> createFileList(String dirPath, Set<String> targetDirNameSet) {

		Map<File, List<File>> fileList = new LinkedHashMap();

		File dir = new File(dirPath);

		for (File file : dir.listFiles()) {

			// ディレクトリ
			if (file.isDirectory()) {

				// 対象のディレクトリなら
				if (targetDirNameSet.contains(file.getName())) {
					fileList.put(file, createFileList(file));
				}

				// 対象でなければ再帰
				else {
					fileList.putAll(createFileList(file.getPath(), targetDirNameSet));
				}
			}
		}

		return fileList;
	}

}
