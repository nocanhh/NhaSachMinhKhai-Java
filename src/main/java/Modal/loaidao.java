package Modal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class loaidao {	
	public ArrayList<loai> getloai() throws Exception {
		ArrayList<loai> ds = new ArrayList<loai>();
		// b1: Kết nối vao csdl
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		// b2: Thiet lap cau lenh sql
		String sql = "select * from loai";
		PreparedStatement cmd = kn.cn.prepareStatement(sql);
		// b3: Truyen tham so vao cau lẹnh sql (neu co)
		// b4: Cho thuc hien cau lenh sql
		ResultSet rs = cmd.executeQuery();
		// b5: Duyet rs va luu vao mang dong
		while (rs.next()) {
			String maloai = rs.getString("maloai");
			String tenloai = rs.getString("tenloai");
			ds.add(new loai(maloai, tenloai));
		}
		// b6: Dong cac doi tuong dang mo: rs, ket noi
		rs.close();
		kn.cn.close();
		return ds;
	}
	
	public boolean checkMaLoai(String maloai) throws Exception {
	    KetNoi kn = new KetNoi(); kn.ketnoi();
	    String sql = "SELECT count(*) FROM loai WHERE maloai=?";
	    PreparedStatement ps = kn.cn.prepareStatement(sql);
	    ps.setString(1, maloai);
	    ResultSet rs = ps.executeQuery();
	    boolean tonTai = false;
	    if (rs.next()) {
	        tonTai = rs.getInt(1) > 0;
	    }
	    rs.close(); kn.cn.close();
	    return tonTai;
	}
	
	// Thêm vào class loaidao
	public int themLoai(String maloai, String tenloai) throws Exception {
	    KetNoi kn = new KetNoi(); kn.ketnoi();
	    String sql = "INSERT INTO loai(maloai, tenloai) VALUES(?, ?)";
	    PreparedStatement ps = kn.cn.prepareStatement(sql);
	    ps.setString(1, maloai);
	    ps.setString(2, tenloai);
	    int kq = ps.executeUpdate();
	    kn.cn.close();
	    return kq;
	}

	public int xoaLoai(String maloai) throws Exception {
	    KetNoi kn = new KetNoi(); kn.ketnoi();
	    String sql = "DELETE FROM loai WHERE maloai=?";
	    PreparedStatement ps = kn.cn.prepareStatement(sql);
	    ps.setString(1, maloai);
	    int kq = ps.executeUpdate();
	    kn.cn.close();
	    return kq;
	}

	public int suaLoai(String maloai, String tenloaiMoi) throws Exception {
	    KetNoi kn = new KetNoi(); kn.ketnoi();
	    String sql = "UPDATE loai SET tenloai=? WHERE maloai=?";
	    PreparedStatement ps = kn.cn.prepareStatement(sql);
	    ps.setString(1, tenloaiMoi);
	    ps.setString(2, maloai);
	    int kq = ps.executeUpdate();
	    kn.cn.close();
	    return kq;
	}

	// Tìm kiếm loại
	public ArrayList<loai> timLoai(String key) throws Exception {
	    ArrayList<loai> ds = new ArrayList<>();
	    KetNoi kn = new KetNoi(); kn.ketnoi();
	    String sql = "SELECT * FROM loai WHERE tenloai LIKE ?";
	    PreparedStatement ps = kn.cn.prepareStatement(sql);
	    ps.setString(1, "%" + key + "%");
	    ResultSet rs = ps.executeQuery();
	    while(rs.next()) {
	        ds.add(new loai(rs.getString("maloai"), rs.getString("tenloai")));
	    }
	    kn.cn.close();
	    return ds;
	}
		
}
