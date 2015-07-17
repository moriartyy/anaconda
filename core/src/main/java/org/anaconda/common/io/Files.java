package org.anaconda.common.io;

import java.io.File;

public class Files {
	
	public static File file(File parent, String child) {
		return new File(parent, child);
	}
	
	public static File file(String... pathes) {
		if (pathes.length == 1) {
			return file(pathes[0]);
		} else {
			File file = file(pathes[0]);
			for (int i = 1; i < pathes.length; i++) {
				file = file(file, pathes[i]);
			}
			return file;
		}
	}
	
	public static File file(String path) {
		return new File(path);
	}
}
