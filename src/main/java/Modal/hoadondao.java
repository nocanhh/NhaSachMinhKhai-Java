package Modal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class hoadondao {

	/**
	 * Tạo một hóa đơn mới và trả về MaHoaDon vừa tạo.
	 * 
	 * @param makh Mã khách hàng
	 * @param conn Đối tượng Connection để thực hiện transaction
	 * @return MaHoaDon vừa được tạo
	 */
	// Thêm tham số boolean daThanhToan vào hàm
	public long taoHoaDon(long makh, Connection conn, boolean daThanhToan) throws Exception {
		String sql = "INSERT INTO HoaDon (makh, NgayMua, damua) VALUES (?, GETDATE(), ?)";

		try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setLong(1, makh);

			ps.setBoolean(2, daThanhToan);

			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getLong(1);
				}
			}
		}
		throw new Exception("Không thể tạo hóa đơn mới.");
	}

	/**
	 * Tạo một chi tiết hóa đơn (một dòng sản phẩm trong hóa đơn).
	 * 
	 * @param maHoaDon   Mã hóa đơn (vừa tạo ở trên)
	 * @param masach     Mã sách
	 * @param soLuongMua Số lượng
	 */
	// Hàm thêm chi tiết hóa đơn 
	public void themChiTietHoaDon(long maHoaDon, String maSach, long soLuong, Connection conn) throws Exception {
		String sql = "INSERT INTO ChiTietHoaDon (MaSach, SoLuongMua, MaHoaDon) VALUES (?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, maSach);
			ps.setLong(2, soLuong);
			ps.setLong(3, maHoaDon);
			ps.executeUpdate();
		}
	}

	/**
	 * Lấy lịch sử mua hàng của một khách hàng,
	 * 
	 * @param makh Mã khách hàng
	 * @return Danh sách các món hàng đã mua
	 */
	public ArrayList<LichSuMuaHang> getLichSu(long makh) throws Exception {
        ArrayList<LichSuMuaHang> ds = new ArrayList<>();
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        
        String sql = "SELECT " +
                     "s.masach, " +       
                     "s.tensach, " +     
                     "s.anh, " +          
                     "ct.SoLuongMua, " +  
                     "s.gia, " +          
                     "h.NgayMua, " +      
                     "h.damua, " +        
                     "h.MaHoaDon, " +     
                     "h.daXacNhan " +     
                     "FROM hoadon h " +
                     "JOIN ChiTietHoaDon ct ON h.MaHoaDon = ct.MaHoaDon " +
                     "JOIN sach s ON ct.MaSach = s.masach " +
                     "WHERE h.makh = ? " +
                     "ORDER BY h.NgayMua DESC";
        
        try {
            PreparedStatement ps = kn.cn.prepareStatement(sql);
            ps.setLong(1, makh);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String masach = rs.getString(1);
                String tensach = rs.getString(2);
                String anh = rs.getString(3);
                int soLuongMua = rs.getInt(4);
                long gia = rs.getLong(5);
                java.util.Date ngayMua = rs.getTimestamp(6);
                boolean damua = rs.getBoolean(7);
                long maDonHang = rs.getLong(8);
                boolean daXacNhan = rs.getBoolean(9); 

                ds.add(new LichSuMuaHang(masach, tensach, anh, soLuongMua, gia, ngayMua, damua, maDonHang, daXacNhan));
            }
            rs.close(); 
        } catch (Exception e) { e.printStackTrace(); throw e; } 
        finally { kn.cn.close(); }
        return ds;
    }

	// Lấy tổng tiền của 1 hóa đơn cụ thể 
	public long getTongTienHoaDon(long maHoaDon) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		String sql = "SELECT SUM(ct.SoLuongMua * s.gia) " + "FROM ChiTietHoaDon ct JOIN Sach s ON ct.masach = s.masach "
				+ "WHERE ct.MaHoaDon = ?";
		PreparedStatement ps = kn.cn.prepareStatement(sql);
		ps.setLong(1, maHoaDon);
		ResultSet rs = ps.executeQuery();
		long tong = 0;
		if (rs.next())
			tong = rs.getLong(1);
		rs.close();
		kn.cn.close();
		return tong;
	}
	
	// Lấy chi tiết các cuốn sách trong 1 hóa đơn cụ thể
    public ArrayList<LichSuMuaHang> getChiTietDonHang(long maHoaDon) throws Exception {
        ArrayList<LichSuMuaHang> ds = new ArrayList<>();
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        
        String sql = "SELECT s.masach, s.tensach, s.anh, ct.SoLuongMua, s.gia " +
                     "FROM ChiTietHoaDon ct JOIN Sach s ON ct.masach = s.masach " +
                     "WHERE ct.MaHoaDon = ?";
        
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setLong(1, maHoaDon);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String masach = rs.getString("masach");
            String tensach = rs.getString("tensach");
            String anh = rs.getString("anh");
            int soLuong = rs.getInt("SoLuongMua");
            long gia = rs.getLong("gia");
            
            // Các tham số còn lại (ngayMua, damua, maDonHang) để null/0
            ds.add(new LichSuMuaHang(masach, tensach, anh, soLuong, gia, null, false, maHoaDon, false));
        }
        rs.close(); kn.cn.close();
        return ds;
    }
    
    public void capNhatDaMua(long maHoaDon) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        // Chỉ update damua = 1 (Đã trả tiền), daXacNhan vẫn giữ nguyên (0 hoặc 1)
        String sql = "UPDATE HoaDon SET damua = 1 WHERE MaHoaDon = ?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setLong(1, maHoaDon);
        ps.executeUpdate();
        ps.close(); kn.cn.close();
    }
}