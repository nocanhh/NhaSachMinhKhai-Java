package Modal;

public class giohang {
	private String masach;
	private String tensach;
	private long soluong;
	private long gia;
	private String anh;
	private long thanhtien;

	public giohang() {
		super();
		// TODO Auto-generated constructor stub
	}

	public giohang(String masach, String tensach, long soluong, long gia, String anh) {
		super();
		this.masach = masach;
		this.tensach = tensach;
		this.soluong = soluong;
		this.gia = gia;
		this.anh = anh;
		this.thanhtien = soluong*gia;
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

	public long getSoluong() {
		return soluong;
	}

	public void setSoluong(long soluong) {
		this.soluong = soluong;
	}

	public long getGia() {
		return gia;
	}

	public void setGia(long gia) {
		this.gia = gia;
	}

	public String getAnh() {
		return anh;
	}

	public void setAnh(String anh) {
		this.anh = anh;
	}

	public long getThanhtien() {
		return soluong*gia;
	}

	public void setThanhtien(long thanhtien) {
		this.thanhtien = thanhtien;
	}

	

}
