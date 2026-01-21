package Modal;

public class khachhangbo {
	khachhangdao khdao= new khachhangdao();
	public khachhang ktDangNhap(String tendn, String pass) throws Exception{
		return khdao.ktDangNhap(tendn, pass);
	}
	
	/**
     * Kiểm tra xem Tên đăng nhập đã tồn tại trong CSDL chưa
     */
    public boolean checkTendnTonTai(String tendn) throws Exception {
        return khdao.checkTendnTonTai(tendn);
    }
    
    /**
     * Thêm khách hàng mới vào CSDL
     */
    public void dangKyKhachHang(String hoten, String diachi, String sodt, String email, String tendn, String pass) throws Exception {
        khdao.dangKyKhachHang(hoten, diachi, sodt, email, tendn, pass);
    }
}
