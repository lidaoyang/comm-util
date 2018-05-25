package com.util;

import java.util.Random;
import java.util.UUID;

public class RedeemCodeUtils {

	public static void main(String[] args) {
		
		/*HashSet<String> set = new HashSet<>();
		for (int i = 0; i < 1000000; i++) {
			String st1 = createBigSmallLetterStrOrNumberRadom(12);
			set.add(st1);
			System.out.println(st1);
		}
		System.out.println(set.size());*/
		String st1 = createBigSmallLetterStrOrNumberRadom(18);
		String st2 = createSmallStrOrNumberRadom(18);
		String st3 = createBigStrOrNumberRadom(18);
		System.out.println(st1);
		System.out.println(st2);
		System.out.println(st3);
		System.out.println(getUUID().toUpperCase());
	}

	/**
	 * 
	 * @function 生成num位的随机字符串(数字、大小写字母随机混排)
	 * @param num
	 * @return
	 */
	public static String createBigSmallLetterStrOrNumberRadom(int num) {

		String str = "";
		for (int i = 0; i < num; i++) {
			int intVal = (int) (Math.random() * 58 + 65);
			if (intVal >= 91 && intVal <= 96) {
				i--;
			}
			if (intVal < 91 || intVal > 96) {
				if (intVal % 2 == 0) {
					str += (char) intVal;
				} else {
					str += (int) (Math.random() * 10);
				}
			}
		}
		return str;
	}

	/**
	 * 
	 * @function 生成num位的随机字符串(数字、小写字母随机混排)
	 * @param num
	 * @return
	 */
	public static String createSmallStrOrNumberRadom(int num) {

		String str = "";
		for (int i = 0; i < num; i++) {
			int intVal = (int) (Math.random() * 26 + 97);
			if (intVal % 2 == 0) {
				str += (char) intVal;
			} else {
				str += (int) (Math.random() * 10);
			}
		}
		return str;
	}

	/**
	 * 
	 * @function 生成num位的随机字符串(大写字母与数字混排)
	 * @param num
	 * @return
	 */
	public static String createBigStrOrNumberRadom(int num) {

		String str = "";
		for (int i = 0; i < num; i++) {
			int intVal = (int) (Math.random() * 26 + 65);
			if (intVal % 2 == 0) {
				str += (char) intVal;
			} else {
				str += (int) (Math.random() * 10);
			}
		}
		return str;
	}
	/**获取UUID
	 * @param str
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}
	// 获取9位随机数
		public static String rand_num_code(int num) {
			int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			Random rand = new Random();
			for (int i = 10; i > 1; i--) {
				int index = rand.nextInt(i);
				int tmp = array[index];
				array[index] = array[i - 1];
				array[i - 1] = tmp;
			}
			int result = 0;
			for (int i = 0; i < num; i++)
				result = result * 10 + array[i];

			String code = result + "";
			if (code.length() < num) {
				code = "0" + code;
			}
			return code;
		}
		// 获取范围随机数
		public static int rand_num_range(int min,int max) {
	        Random random = new Random();
	        int s = random.nextInt(max)%(max-min+1) + min;
			return s;
		}
}
