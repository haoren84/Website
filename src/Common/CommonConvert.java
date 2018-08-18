package Common;

public class CommonConvert {

	/**
	 * �ֽ���תint
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
	 * int ת�ֽ���
	 * 
	 * @param a
	 * @return
	 */
	public static byte[] intToByteArray(int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}

	/**
	 * ����ת��Ϊ�ֽ�
	 * @param f
	 * @return
	 */
	public static byte[] float2byte(float f) {

		// ��floatת��Ϊbyte[]

		int fbit = Float.floatToIntBits(f);

		byte[] b = new byte[4];

		for (int i = 0; i < 4; i++) {

			b[i] = (byte) (fbit >> (24 - i * 8));

		}

		// ��ת����

		int len = b.length;

		// ����һ����Դ����Ԫ��������ͬ������

		byte[] dest = new byte[len];

		// Ϊ�˷�ֹ�޸�Դ���飬��Դ���鿽��һ�ݸ���

		System.arraycopy(b, 0, dest, 0, len);

		byte temp;

		// ��˳λ��i���뵹����i������

		for (int i = 0; i < len / 2; ++i) {

			temp = dest[i];

			dest[i] = dest[len - i - 1];

			dest[len - i - 1] = temp;

		}

		return dest;

	}

	/**
	 * �ֽ�ת��Ϊ����
	 * @param b
	 *            �ֽڣ�����4���ֽڣ�
	 * @param index
	 *            ��ʼλ��
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
