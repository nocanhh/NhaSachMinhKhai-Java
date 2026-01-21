package Modal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class khachhangdao {
	public khachhang ktDangNhap(String tendn, String pass) throws Exception {
		// b1: Kết nối vao csdl
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		// b2: Thiet lap cau lenh sql
		String sql = "select * from khachhang where tendn=? and pass=?";
		PreparedStatement cmd = kn.cn.prepareStatement(sql);
		// b3: Truyen tham so vao cau lẹnh sql (neu co)
		cmd.setString(1, tendn);

		String matKhauDaMaHoa = MaHoaMatKhau.maHoaMD5(pass);
		cmd.setString(2, matKhauDaMaHoa);

		// cmd.setString(2,pass);
		// b4: Cho thuc hien cau lenh sql
		ResultSet rs = cmd.executeQuery();
		// b5: Duyet rs va luu vao mang dong
		khachhang kh = null;
		if (rs.next()) {// Neu dang nhap thanh cong
			long makh = rs.getLong("makh");
			String hoten = rs.getString("hoten");
			String diachi = rs.getString("diachi");
			String sodt = rs.getString("sodt");
			String email = rs.getString("email");
			kh = new khachhang(makh, hoten, diachi, sodt, email, tendn, pass);
		}
		// b6: Dong cac doi tuong dang mo: rs, ket noi
		rs.close();
		kn.cn.close();
		return kh;
	}

	/**
	 * Kiểm tra Tên đăng nhập (tendn) có tồn tại hay không
	 * 
	 * @param tendn Tên đăng nhập cần kiểm tra
	 * @return true nếu đã tồn tại, false nếu chưa
	 */
	public boolean checkTendnTonTai(String tendn) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		Connection conn = kn.cn;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT COUNT(*) FROM KhachHang WHERE tendn = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, tendn);
			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				return (count > 0); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Lỗi kiểm tra tên đăng nhập");
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return false;
	}

	/**
	 * Thêm một khách hàng mới vào CSDL. (Giả định rằng 'makh' là cột tự động tăng -
	 * IDENTITY)
	 */
	public void dangKyKhachHang(String hoten, String diachi, String sodt, String email, String tendn, String pass)
			throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		Connection conn = kn.cn;
		PreparedStatement ps = null;

		// Giả định makh là tự tăng và không cần chèn vào
		String sql = "INSERT INTO KhachHang (hoten, diachi, sodt, email, tendn, pass) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, hoten);
			ps.setString(2, diachi);
			ps.setString(3, sodt);
			ps.setString(4, email);
			ps.setString(5, tendn);

			String matKhauDaMaHoa = MaHoaMatKhau.maHoaMD5(pass);
			ps.setString(6, matKhauDaMaHoa);
			
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Lỗi đăng ký tài khoản");
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
	}

	// Kiểm tra email có tồn tại không
	public boolean checkEmail(String email) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		String sql = "SELECT COUNT(*) FROM KhachHang WHERE email = ?";
		try (PreparedStatement ps = kn.cn.prepareStatement(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} finally {
			kn.cn.close();
		}
		return false;
	}

	// Cập nhật mật khẩu mới cho email
	public void capNhatMatKhau(String email, String matKhauMoiMD5) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		String sql = "UPDATE KhachHang SET pass = ? WHERE email = ?";
		try (PreparedStatement ps = kn.cn.prepareStatement(sql)) {
			ps.setString(1, matKhauMoiMD5);
			ps.setString(2, email);
			ps.executeUpdate();
		} finally {
			kn.cn.close();
		}
	}
}
