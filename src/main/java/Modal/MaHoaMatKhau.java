package Modal;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MaHoaMatKhau {

	/**
	 * Mã hóa một chuỗi văn bản sang chuỗi MD5 32 ký tự.
	 * 
	 * @param input Chuỗi cần mã hóa (mật khẩu)
	 * @return Chuỗi MD5 (32 ký tự hex)
	 */
	public static String maHoaMD5(String input) {
		try {
			// 1. Lấy một đối tượng MessageDigest với thuật toán MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 2. Tính toán hash (ra một mảng byte)
			byte[] messageDigest = md.digest(input.getBytes());

			// 3. Chuyển mảng byte thành một số BigInteger
			BigInteger no = new BigInteger(1, messageDigest);

			// 4. Chuyển số BigInteger thành chuỗi Hexa (hệ 16)
			String hashtext = no.toString(16);

			// 5. MD5 phải dài 32 ký tự. Thêm '0' vào đầu nếu cần
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;

		} catch (NoSuchAlgorithmException e) {
			// Lỗi này gần như không bao giờ xảy ra nếu "MD5" là đúng
			System.err.println("Lỗi thuật toán mã hóa: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

}