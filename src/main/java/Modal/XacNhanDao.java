package Modal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class XacNhanDao {

    // 1. Lấy danh sách đơn hàng chờ xác nhận
    public ArrayList<AdminDonHang> getDanhSachCho() throws Exception {
        ArrayList<AdminDonHang> ds = new ArrayList<>();
        KetNoi kn = new KetNoi(); kn.ketnoi();
        String sql = "SELECT * FROM V_DonHangAdmin ORDER BY MaHoaDon DESC"; 
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            ds.add(new AdminDonHang(
                rs.getLong("MaHoaDon"),
                rs.getLong("makh"),
                rs.getString("hoten"),
                "", "", null, // Sdt, DiaChi, NgayMua tạm để trống
                rs.getLong("tongsoluong"),
                rs.getLong("tonggia"),
                rs.getLong("ThanhTien"),
                rs.getBoolean("damua"),
                false // daXacNhan trong View toàn là 0
            ));
        }
        rs.close(); kn.cn.close();
        return ds;
    }

    // 2. Lấy thông tin chi tiết 1 đơn hàng 
    public AdminDonHang getThongTinDonHang(long maHoaDon) throws Exception {
        AdminDonHang tt = null;
        KetNoi kn = new KetNoi(); kn.ketnoi();
        
        String sql = "SELECT k.hoten, k.sodt, k.diachi, h.NgayMua, h.damua, h.daXacNhan " +
                     "FROM hoadon h JOIN KhachHang k ON h.makh = k.makh WHERE h.MaHoaDon = ?";
        
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setLong(1, maHoaDon);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Tận dụng AdminDonHang để chứa thông tin
            tt = new AdminDonHang();
            tt.setMaHoaDon(maHoaDon);
            tt.setHoten(rs.getString("hoten"));
            tt.setSodt(rs.getString("sodt"));
            tt.setDiachi(rs.getString("diachi"));
            tt.setNgayMua(rs.getTimestamp("NgayMua"));
            tt.setDamua(rs.getBoolean("damua"));
            tt.setDaXacNhan(rs.getBoolean("daXacNhan"));
        }
        rs.close(); kn.cn.close();
        return tt;
    }

    // 3. Lấy danh sách sách 
    public ArrayList<LichSuMuaHang> getChiTietSach(long maHoaDon) throws Exception {
        ArrayList<LichSuMuaHang> ds = new ArrayList<>();
        KetNoi kn = new KetNoi(); kn.ketnoi();
        
        String sql = "SELECT * FROM V_ChiTietAdmin WHERE MaHoaDon = ?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setLong(1, maHoaDon);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            LichSuMuaHang ls = new LichSuMuaHang();
            ls.setTensach(rs.getString("tensach"));
            ls.setAnh(rs.getString("anh"));
            ls.setSoLuongMua(rs.getInt("SoLuongMua"));
            ls.setGia(rs.getLong("gia"));
            ds.add(ls);
        }
        rs.close(); kn.cn.close();
        return ds;
    }
    
    // 4. Hàm xác nhận đơn
    public void xacNhanDon(long maHoaDon) throws Exception {
        KetNoi kn = new KetNoi(); kn.ketnoi();
        String sql = "UPDATE hoadon SET daXacNhan = 1 WHERE MaHoaDon = ?";
        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ps.setLong(1, maHoaDon);
        ps.executeUpdate();
        ps.close(); kn.cn.close();
    }
    
    // Lấy TẤT CẢ đơn hàng (Dùng cho QuanLyDonHangController)
    public ArrayList<AdminDonHang> getAllDonHang() throws Exception {
        ArrayList<AdminDonHang> ds = new ArrayList<>();
        KetNoi kn = new KetNoi(); 
        kn.ketnoi();
        
        // Câu lệnh SQL lấy tất cả, gộp nhóm để tính tổng tiền
        String sql = "SELECT h.MaHoaDon, h.makh, k.hoten, k.sodt, k.diachi, h.NgayMua, " +
                     "h.damua, h.daXacNhan, " +
                     "SUM(ct.SoLuongMua) as tongsoluong, " +
                     "SUM(s.gia) as tonggia, " +
                     "SUM(ct.SoLuongMua * s.gia) as ThanhTien " +
                     "FROM hoadon h " +
                     "JOIN KhachHang k ON h.makh = k.makh " +
                     "JOIN ChiTietHoaDon ct ON h.MaHoaDon = ct.MaHoaDon " +
                     "JOIN sach s ON ct.MaSach = s.masach " +
                     "GROUP BY h.MaHoaDon, h.makh, k.hoten, k.sodt, k.diachi, h.NgayMua, h.damua, h.daXacNhan " +
                     "ORDER BY h.MaHoaDon DESC";

        PreparedStatement ps = kn.cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            ds.add(new AdminDonHang(
                rs.getLong("MaHoaDon"),
                rs.getLong("makh"),
                rs.getString("hoten"),
                rs.getString("sodt"),   
                rs.getString("diachi"),
                rs.getTimestamp("NgayMua"),
                rs.getLong("tongsoluong"),
                rs.getLong("tonggia"),
                rs.getLong("ThanhTien"),
                rs.getBoolean("damua"),
                rs.getBoolean("daXacNhan")
            ));
        }
        rs.close(); 
        kn.cn.close();
        return ds;
    }
    
    public void xoaDonHangTon() throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        
        // Xóa chi tiết trước
        String sqlChiTiet = "DELETE FROM ChiTietHoaDon WHERE MaHoaDon IN " +
                            "(SELECT MaHoaDon FROM HoaDon WHERE damua = 0 AND DATEDIFF(day, NgayMua, GETDATE()) > 15)";
        
        // Xóa hóa đơn sau
        String sqlHoaDon = "DELETE FROM HoaDon WHERE damua = 0 AND DATEDIFF(day, NgayMua, GETDATE()) > 15";
        
        Statement ps = kn.cn.createStatement();
        ps.executeUpdate(sqlChiTiet);
        ps.executeUpdate(sqlHoaDon);
        
        ps.close(); 
        kn.cn.close();
    }
}