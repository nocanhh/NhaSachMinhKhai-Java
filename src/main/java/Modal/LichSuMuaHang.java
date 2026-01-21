package Modal;

import java.util.Date;

public class LichSuMuaHang {
	private String masach;
	private String tensach;
	private String anh;
	private int soLuongMua;
	private long gia;
	private Date ngayMua;
	private boolean damua;
	private long maDonHang;
	private boolean daXacNhan;

	public LichSuMuaHang() {
		super();
	}

	public LichSuMuaHang(String masach, String tensach, String anh, int soLuongMua, long gia, Date ngayMua,
			boolean damua, long maDonHang, boolean daXacNhan) {
		super();
		this.masach = masach;
		this.tensach = tensach;
		this.anh = anh;
		this.soLuongMua = soLuongMua;
		this.gia = gia;
		this.ngayMua = ngayMua;
		this.damua = damua;
		this.maDonHang = maDonHang;
		this.daXacNhan = daXacNhan;
	}

	// Getter & Setter
	public boolean isDaXacNhan() {
		return daXacNhan;
	}

	public void setDaXacNhan(boolean daXacNhan) {
		this.daXacNhan = daXacNhan;
	}

	public String getMasach() {
		return masach;
	}

	public void setMasach(String masach) {
		this.masach = masach;
	}

	public String getTensach() {
		return tensach;
	}

	public void setTensach(String tensach) {
		this.tensach = tensach;
	}

	public String getAnh() {
		return anh;
	}

	public void setAnh(String anh) {
		this.anh = anh;
	}

	public int getSoLuongMua() {
		return soLuongMua;
	}

	public void setSoLuongMua(int soLuongMua) {
		this.soLuongMua = soLuongMua;
	}

	public long getGia() {
		return gia;
	}

	public void setGia(long gia) {
		this.gia = gia;
	}

	public Date getNgayMua() {
		return ngayMua;
	}

	public void setNgayMua(Date ngayMua) {
		this.ngayMua = ngayMua;
	}

	public boolean isDamua() {
		return damua;
	}

	public void setDamua(boolean damua) {
		this.damua = damua;
	}

	public long getMaDonHang() {
		return maDonHang;
	}

	public void setMaDonHang(long maDonHang) {
		this.maDonHang = maDonHang;
	}

	public long getThanhTien() {
		return soLuongMua * gia;
	}
}