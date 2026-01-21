package Modal;

public class DangNhapAdminBo {
    
    DangNhapAdminDao dao = new DangNhapAdminDao();

    // 1. Kiểm tra đăng nhập
    public DangNhapAdmin ktdn(String tenDangNhap, String matKhau) throws Exception {
        return dao.ktdn(tenDangNhap, matKhau);
    }

    // 2. Check tồn tại User
    public boolean checkTonTai(String tenDangNhap) throws Exception {
        return dao.checkTonTai(tenDangNhap);
    }

    // 3. Đăng ký
    public void dangKy(String hoTen, String email, String tenDangNhap, String matKhau) throws Exception {
        dao.dangKy(hoTen, email, tenDangNhap, matKhau);
    }

    // 4. Check Email
    public boolean checkEmail(String email) throws Exception {
        return dao.checkEmail(email);
    }

    // 5. Update Password
    public void updatePass(String email, String newPass) throws Exception {
        dao.updatePass(email, newPass);
    }
}