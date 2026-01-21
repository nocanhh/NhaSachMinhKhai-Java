package Modal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DangNhapAdminDao {

    // 1. Kiểm tra đăng nhập
    public DangNhapAdmin ktdn(String tenDangNhap, String matKhau) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        
        String sql = "SELECT TenDangNhap, MatKhau, Quyen, HoTen, Email FROM DangNhap WHERE TenDangNhap=? AND MatKhau=?";
        
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        cmd.setString(1, tenDangNhap);
        cmd.setString(2, matKhau);
        
        ResultSet rs = cmd.executeQuery();
        DangNhapAdmin admin = null;
        
        if (rs.next()) {
            boolean quyen = rs.getBoolean("Quyen");
            String hoTen = rs.getString("HoTen");
            String email = rs.getString("Email");
            
            // Xử lý nếu dữ liệu cũ chưa có HoTen/Email (tránh lỗi null)
            if(hoTen == null) hoTen = "Admin"; 
            if(email == null) email = "";
            
            admin = new DangNhapAdmin(tenDangNhap, matKhau, quyen, hoTen, email);
        }
        
        rs.close();
        kn.cn.close();
        return admin;
    }

    // 2. Kiểm tra tên đăng nhập tồn tại (Cho Đăng ký)
    public boolean checkTonTai(String tenDangNhap) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        String sql = "SELECT TenDangNhap FROM DangNhap WHERE TenDangNhap=?";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        cmd.setString(1, tenDangNhap);
        ResultSet rs = cmd.executeQuery();
        boolean result = rs.next();
        rs.close();
        kn.cn.close();
        return result;
    }

    // 3. Đăng ký tài khoản Admin mới đủ 5 cột
    public void dangKy(String hoTen, String email, String tenDangNhap, String matKhau) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        
        // Mặc định Quyen = 0 (False - Nhân viên thường)
        String sql = "INSERT INTO DangNhap(TenDangNhap, MatKhau, Quyen, HoTen, Email) VALUES (?, ?, 0, ?, ?)";
        
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        cmd.setString(1, tenDangNhap);
        cmd.setString(2, matKhau);
        cmd.setString(3, hoTen);
        cmd.setString(4, email);
        
        cmd.executeUpdate();
        kn.cn.close();
    }

    // 4. Kiểm tra Email tồn tại (Cho Quên mật khẩu)
    public boolean checkEmail(String email) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        String sql = "SELECT Email FROM DangNhap WHERE Email=?";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        cmd.setString(1, email);
        ResultSet rs = cmd.executeQuery();
        boolean result = rs.next();
        rs.close();
        kn.cn.close();
        return result;
    }

    // 5. Cập nhật mật khẩu mới qua Email
    public void updatePass(String email, String newPass) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        String sql = "UPDATE DangNhap SET MatKhau=? WHERE Email=?";
        PreparedStatement cmd = kn.cn.prepareStatement(sql);
        cmd.setString(1, newPass);
        cmd.setString(2, email);
        cmd.executeUpdate();
        kn.cn.close();
    }
}