package Modal;

import java.util.Date;

public class AdminDonHang {
	private long maHoaDon;
	private long makh;
	private String hoten;
	private String sodt; 
	private String diachi;
	private Date ngayMua; 
	private long tongsoluong;
	private long tonggia;
	private long thanhTien;
	private boolean damua;
	private boolean daXacNhan;

	public AdminDonHang() {
		super();
	}

	// Constructor đầy đủ (dùng cho cả danh sách và chi tiết)
	public AdminDonHang(long maHoaDon, long makh, String hoten, String sodt, String diachi, Date ngayMua,
			long tongsoluong, long tonggia, long thanhTien, boolean damua, boolean daXacNhan) {
		this.maHoaDon = maHoaDon;
		this.makh = makh;
		this.hoten = hoten;
		this.sodt = sodt;
		this.diachi = diachi;
		this.ngayMua = ngayMua;
		this.tongsoluong = tongsoluong;
		this.tonggia = tonggia;
		this.thanhTien = thanhTien;
		this.damua = damua;
		this.daXacNhan = daXacNhan;
	}

	// Getter & Setter (Bạn tự generate hoặc copy hết vào)
	public long getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(long maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public long getMakh() {
		return makh;
	}

	public void setMakh(long makh) {
		this.makh = makh;
	}

	public String getHoten() {
		return hoten;
	}

	public void setHoten(String hoten) {
		this.hoten = hoten;
	}

	public String getSodt() {
		return sodt;
	}

	public void setSodt(String sodt) {
		this.sodt = sodt;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public Date getNgayMua() {
		return ngayMua;
	}

	public void setNgayMua(Date ngayMua) {
		this.ngayMua = ngayMua;
	}

	public long getTongsoluong() {
		return tongsoluong;
	}

	public void setTongsoluong(long tongsoluong) {
		this.tongsoluong = tongsoluong;
	}

	public long getTonggia() {
		return tonggia;
	}

	public void setTonggia(long tonggia) {
		this.tonggia = tonggia;
	}

	public long getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(long thanhTien) {
		this.thanhTien = thanhTien;
	}

	public boolean isDamua() {
		return damua;
	}

	public void setDamua(boolean damua) {
		this.damua = damua;
	}

	public boolean isDaXacNhan() {
		return daXacNhan;
	}

	public void setDaXacNhan(boolean daXacNhan) {
		this.daXacNhan = daXacNhan;
	}
}