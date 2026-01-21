package Modal;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ThongKeDao {

	// Class con để chứa dữ liệu biểu đồ
	public static class DuLieuBieuDo {
		public String ngay;
		public long tongTien;

		public DuLieuBieuDo(String ngay, long tongTien) {
			this.ngay = ngay;
			this.tongTien = tongTien;
		}

		public String getNgay() {
			return ngay;
		}

		public void setNgay(String ngay) {
			this.ngay = ngay;
		}

		public long getTongTien() {
			return tongTien;
		}

		public void setTongTien(long tongTien) {
			this.tongTien = tongTien;
		}
	}

	public ArrayList<DuLieuBieuDo> getDoanhThu7Ngay() throws Exception {
		ArrayList<DuLieuBieuDo> list = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		// SQL: Lấy 7 ngày gần nhất có doanh thu
		String sql = "SELECT TOP 7 * FROM V_DoanhThuNgay ORDER BY Ngay DESC";

		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

		while (rs.next()) {
			Date d = rs.getDate("Ngay");
			long t = rs.getLong("TongTien");
			list.add(new DuLieuBieuDo(sdf.format(d), t));
		}
		rs.close();
		kn.cn.close();
		return list;
	}

	// Lấy doanh thu của ngày hôm nay
	public long getDoanhThuHomNay() throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		// Tính tổng tiền của các hóa đơn damua=1 trong ngày hiện tại
		String sql = "SELECT SUM(ct.SoLuongMua * s.gia) FROM HoaDon h "
				+ "JOIN ChiTietHoaDon ct ON h.MaHoaDon = ct.MaHoaDon " + "JOIN Sach s ON ct.masach = s.masach "
				+ "WHERE h.damua = 1 AND CAST(h.NgayMua AS DATE) = CAST(GETDATE() AS DATE)";
		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		long tong = 0;
		if (rs.next())
			tong = rs.getLong(1);
		rs.close();
		kn.cn.close();
		return tong;
	}

	// Lấy sách sắp hết (Soluong <= 10)
	public ArrayList<sach> getSachSapHet() throws Exception {
		ArrayList<sach> ds = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		String sql = "SELECT * FROM Sach WHERE soluong <= 10";
		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ds.add(new sach(rs.getString("masach"), rs.getString("tensach"), rs.getString("tacgia"),
					rs.getLong("soluong"), rs.getLong("gia"), rs.getString("anh"), rs.getString("maloai")));
		}
		return ds;
	}

	public ArrayList<sach> getSachBanChay() throws Exception {
		ArrayList<sach> ds = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();

		String sql = "SELECT * FROM V_SachBanChay";

		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String masach = rs.getString("masach");
			String tensach = rs.getString("tensach");
			String tacgia = rs.getString("tacgia");
			long soLuongBan = rs.getLong("TongSoLuongBan");

			ds.add(new sach(masach, tensach, tacgia, soLuongBan, 0, "", ""));
		}
		rs.close();
		kn.cn.close();
		return ds;
	}
}

//Lưu ý: View chỉ trả về masach, tensach, tacgia, TongSoLuongBan.
// Các cột khác (gia, anh, maloai) ta để tạm giá trị mặc định để không bị lỗi Constructor.
// Ở đây tôi gán 'soLuongBan' vào thuộc tính 'soluong' của đối tượng sách để hiển thị.