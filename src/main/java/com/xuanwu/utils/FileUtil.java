package com.xuanwu.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileUtil {

	/**
	 * 读取文件，返回Map集合
	 * 
	 * @param path
	 *            文件相对路径
	 * @return 读取的Map集合（title、data）
	 */
	public static Map<String, Object> read(String path) {
		Map<String, Object> map = new HashMap<String, Object>();
		InputStream in = null;
		BufferedReader reader = null;
		try {
			in = FileUtil.class.getClassLoader().getResourceAsStream(path);
			reader = new BufferedReader(new InputStreamReader(in));
			String title = reader.readLine();
			String value = null;
			Set<String> set = new HashSet<String>();
			while ((value = reader.readLine()) != null) {
				set.add(value);
			}
			List<String> data = new ArrayList<String>(set);
			map.put("title", title);
			map.put("data", data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

		}
		return map;
	}

	/**
	 * 写文件
	 * 
	 * @param path
	 *            文件绝对路径
	 * @param content
	 *            内容
	 */
	public static void write(String path, String content) {
		Writer out = null;
		BufferedWriter writer = null;
		try {
			if (content != null) {
				File file = new File(path);
				if (!file.exists()) {
					file.createNewFile();
				}
				out = new FileWriter(file, true); // 追加脚本。
				writer = new BufferedWriter(out);
				writer.write(content);
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * 删除目录下的所有文件（会递归删除）
	 * 
	 * @param dir
	 */
	public static void deleteFiles(String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				deleteFiles(f.getAbsolutePath());
			}
		} else {
			file.delete();
			return;
		}
	}

	/**
	 * 读取文件为list集合
	 * 
	 * @param path
	 *            文件相对路径
	 * @return
	 */
	public static List<String> readItem(String path) {
		List<String> items = null;
		BufferedReader reader = null;
		InputStream in = null;
		try {
			in = FileUtil.class.getClassLoader().getResourceAsStream(path);
			reader = new BufferedReader(new InputStreamReader(in));
			String item = null;
			Set<String> set = new HashSet<String>();
			while ((item = reader.readLine()) != null && !item.equals("")) {
				set.add(item);
			}
			items = new LinkedList<String>(set);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return items;
	}
	
	public static String catSuffix(String name){
		int index = name.lastIndexOf(".");
		return name.substring(0, index);
	}
}
