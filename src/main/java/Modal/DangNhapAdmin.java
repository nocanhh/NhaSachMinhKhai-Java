package Modal;

public class DangNhapAdmin {
	private String tenDangNhap;
	private String matKhau;
	private boolean quyen;
	private String hoTen;
	private String email;

	public DangNhapAdmin() {
	}

	public DangNhapAdmin(String tenDangNhap, String matKhau, boolean quyen, String hoTen, String email) {
		super();
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.quyen = quyen;
		this.hoTen = hoTen;
		this.email = email;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public boolean isQuyen() {
		return quyen;
	}

	public void setQuyen(boolean quyen) {
		this.quyen = quyen;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}