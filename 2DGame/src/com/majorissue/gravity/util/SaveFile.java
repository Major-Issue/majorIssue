package com.majorissue.gravity.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.majorissue.framework.FileIO;

public class SaveFile {

	// saved data w/ defaults
	public static int level = 0;
	
	public static void write(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile("gravity.save")));
			out.write(Integer.toString(level));
			out.write("\n");
		} catch (IOException e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(files.readFile("gravity.save")));
			level = Integer.parseInt(in.readLine());
		} catch (IOException e) {
			// :( It's ok we have defaults
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}
}
