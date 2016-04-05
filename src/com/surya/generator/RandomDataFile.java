package com.surya.generator;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomDataFile {
	private static Random rand = new Random();

	public static void main(String[] args) {
		String outputFilePath = "/data/common/test-data/slaskar-failure-dataset.txt";
		long rowCount = 100;
		Random r = new Random(System.currentTimeMillis());
		System.out.println(new Date() + " Being generating random data as text file ... ");
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath)))) {
			StringBuilder sb = null;
			for (int i = 0; i < rowCount; i++) {
				sb = new StringBuilder();
				sb.append(r.nextInt(128)).append(",")
				.append(r.nextInt(32767)).append(",")
				.append(r.nextInt(294967295))
				.append(",").append(r.nextLong())
				.append(",").append(r.nextBoolean())
				.append(",").append(r.nextFloat())
				.append(",").append(r.nextDouble())
				.append(",").append(getRandom(r.nextInt(900))) // Ideally this should be 255. Making 900 for failure data set testing.
				.append(",").append(r.nextDouble())
				.append(",").append(getRandom(r.nextInt(255)));

				bw.write(sb.toString());
				bw.newLine();

				if (i % 1000 == 0) {
					bw.flush();
				}
			}
			bw.flush();
		} catch (IOException e) {
			System.out.println(new Date() + " Whoops, there was an exception when running this program.");
			e.printStackTrace();
		} finally {
			System.out.println(new Date() + " Random data generation complete.");
		}
	}

	public static String getRandom(int size) {
		char[] chars = new char[size];
		IntStream is = rand.ints(size, 32, 127);
		int val = -1, charIndex = 0;
		Iterator<Integer> it = is.iterator();
		while (it.hasNext()) {
			val = it.next();
			if (val == 44) { // Escape commas in the string
				val = 39; // Replace with a single quote
			}
			chars[charIndex++] = (char) val;
		}

		return new String(chars);
	}
}
