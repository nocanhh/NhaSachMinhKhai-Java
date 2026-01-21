package Modal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date; // Lưu ý import Date

public class HoaDonAdminDao {

	public static class HoaDonHienThi {
		private long maHoaDon;
		private String tenKhachHang;
		private Date ngayMua;
		private long tongTien;
		private boolean daMua;

		public HoaDonHienThi() {
		}

		public HoaDonHienThi(long maHoaDon, String tenKhachHang, Date ngayMua, long tongTien, boolean daMua) {
			this.maHoaDon = maHoaDon;
			this.tenKhachHang = tenKhachHang;
			this.ngayMua = ngayMua;
			this.tongTien = tongTien;
			this.daMua = daMua;
		}

		public long getMaHoaDon() {
			return maHoaDon;
		}

		public void setMaHoaDon(long maHoaDon) {
			this.maHoaDon = maHoaDon;
		}

		public String getTenKhachHang() {
			return tenKhachHang;
		}

		public void setTenKhachHang(String tenKhachHang) {
			this.tenKhachHang = tenKhachHang;
		}

		public Date getNgayMua() {
			return ngayMua;
		}

		public void setNgayMua(Date ngayMua) {
			this.ngayMua = ngayMua;
		}

		public long getTongTien() {
			return tongTien;
		}

		public void setTongTien(long tongTien) {
			this.tongTien = tongTien;
		}

		public boolean isDaMua() {
			return daMua;
		}

		public void setDaMua(boolean daMua) {
			this.daMua = daMua;
		}
	}
	
	// 1. Lấy danh sách cần Admin duyệt
	public ArrayList<HoaDonHienThi> getHoaDonChuaXacNhan() throws Exception {
		ArrayList<HoaDonHienThi> ds = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		String sql = "SELECT h.MaHoaDon, k.hoten, h.NgayMua, h.damua, " + "SUM(ct.SoLuongMua * s.gia) as TongTien "
				+ "FROM hoadon h " + "JOIN KhachHang k ON h.makh = k.makh "
				+ "JOIN ChiTietHoaDon ct ON h.MaHoaDon = ct.MaHoaDon " + "JOIN sach s ON ct.MaSach = s.masach "
				+ "WHERE h.daXacNhan = 0 " + "GROUP BY h.MaHoaDon, k.hoten, h.NgayMua, h.damua "
				+ "ORDER BY h.NgayMua DESC";

		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ds.add(new HoaDonHienThi(rs.getLong("MaHoaDon"), rs.getString("hoten"), rs.getTimestamp("NgayMua"),
					rs.getLong("TongTien"), rs.getBoolean("damua")));
		}
		rs.close();
		ps.close();
		kn.cn.close();
		return ds;
	}

	// 2. Hàm Xác nhận đơn hàng
	public void xacNhanHoaDon(long maHoaDon) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		String sql = "UPDATE hoadon SET daXacNhan = 1, damua = 1 WHERE MaHoaDon = ?";

		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ps.setLong(1, maHoaDon);
		ps.executeUpdate();
		ps.close();
		kn.cn.close();
	}

	// 3. Xóa đơn hàng tồn quá 15 ngày
	public void xoaDonHangTon() throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		// Xóa chi tiết trước
		String sqlChiTiet = "DELETE FROM ChiTietHoaDon WHERE MaHoaDon IN "
				+ "(SELECT MaHoaDon FROM HoaDon WHERE damua = 0 AND DATEDIFF(day, NgayMua, GETDATE()) > 15)";

		// Xóa hóa đơn sau
		String sqlHoaDon = "DELETE FROM HoaDon WHERE damua = 0 AND DATEDIFF(day, NgayMua, GETDATE()) > 15";

		Statement st = kn.cn.createStatement();
		st.executeUpdate(sqlChiTiet);
		st.executeUpdate(sqlHoaDon);

		st.close();
		kn.cn.close();
	}

	// 4. Lấy danh sách hóa đơn ĐÃ thanh toán (damua = 1)
	public ArrayList<HoaDonHienThi> getHoaDonDaThanhToan() throws Exception {
		ArrayList<HoaDonHienThi> ds = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		String sql = "SELECT h.MaHoaDon, k.hoten, h.NgayMua, h.damua, " + "SUM(ct.SoLuongMua * s.gia) as TongTien "
				+ "FROM HoaDon h " + "JOIN KhachHang k ON h.makh = k.makh "
				+ "JOIN ChiTietHoaDon ct ON h.MaHoaDon = ct.MaHoaDon " + "JOIN Sach s ON ct.masach = s.masach "
				+ "WHERE h.damua = 1 " + "GROUP BY h.MaHoaDon, k.hoten, h.NgayMua, h.damua "
				+ "ORDER BY h.NgayMua DESC";

		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ds.add(new HoaDonHienThi(rs.getLong("MaHoaDon"), rs.getString("hoten"), rs.getTimestamp("NgayMua"),
					rs.getLong("TongTien"), rs.getBoolean("damua")));
		}
		rs.close();
		kn.cn.close();
		return ds;
	}

	// 5. Lấy danh sách hóa đơn đã mua theo một ngày cụ thể
	public ArrayList<HoaDonHienThi> getHoaDonByDate(String ngay) throws Exception {
		ArrayList<HoaDonHienThi> ds = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		String sql = "SELECT h.MaHoaDon, k.hoten, h.NgayMua, h.damua, " + "SUM(ct.SoLuongMua * s.gia) as TongTien "
				+ "FROM HoaDon h " + "JOIN KhachHang k ON h.makh = k.makh "
				+ "JOIN ChiTietHoaDon ct ON h.MaHoaDon = ct.MaHoaDon " + "JOIN Sach s ON ct.masach = s.masach "
				+ "WHERE h.damua = 1 AND CAST(h.NgayMua AS DATE) = ? " + // Cast so sánh ngày vs ngày hiện tại
				"GROUP BY h.MaHoaDon, k.hoten, h.NgayMua, h.damua " + "ORDER BY h.NgayMua DESC";

		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ps.setString(1, ngay);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ds.add(new HoaDonHienThi(rs.getLong("MaHoaDon"), rs.getString("hoten"), rs.getTimestamp("NgayMua"),
					rs.getLong("TongTien"), rs.getBoolean("damua")));
		}
		rs.close();
		kn.cn.close();
		return ds;
	}
}