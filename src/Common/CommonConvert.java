package Common;

public class CommonConvert {

	/**
	 * 字节组转int
	 * 
	 * @param b
	 * @return
	 */
	public static int byteArrayToInt(byte[] b) {
		//return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
		return b[0] & 0xFF 
				| (b[1] & 0xFF) << 8 
				| (b[2] & 0xFF) << 16 
				| (b[3] & 0xFF) << 24;
	}

	/**
	 * int 转字节组
	 * 
	 * @param a
	 * @return
	 */
	public static byte[] intToByteArray(int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}

	/**
	 * 浮点转换为字节
	 * @param f
	 * @return
	 */
	public static byte[] float2byte(float f) {

		// 把float转换为byte[]

		int fbit = Float.floatToIntBits(f);

		byte[] b = new byte[4];

		for (int i = 0; i < 4; i++) {

			b[i] = (byte) (fbit >> (24 - i * 8));

		}

		// 翻转数组

		int len = b.length;

		// 建立一个与源数组元素类型相同的数组

		byte[] dest = new byte[len];

		// 为了防止修改源数组，将源数组拷贝一份副本

		System.arraycopy(b, 0, dest, 0, len);

		byte temp;

		// 将顺位第i个与倒数第i个交换

		for (int i = 0; i < len / 2; ++i) {

			temp = dest[i];

			dest[i] = dest[len - i - 1];

			dest[len - i - 1] = temp;

		}

		return dest;

	}

	/**
	 * 字节转换为浮点
	 * @param b
	 *            字节（至少4个字节）
	 * @param index
	 *            开始位置
	 * @return
	 */
	public static float byte2float(byte[] b, int index) {

		int l;

		l = b[index + 0];

		l &= 0xff;

		l |= ((long) b[index + 1] << 8);

		l &= 0xffff;

		l |= ((long) b[index + 2] << 16);

		l &= 0xffffff;

		l |= ((long) b[index + 3] << 24);

		return Float.intBitsToFloat(l);

	}

}
