package Modal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class sachdao {
	private static final int PAGE_SIZE = 6;
	public int demSach(String maloai, String key) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		Connection conn = kn.cn;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// Xây dựng câu lệnh SQL động
			StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM sach");
			ArrayList<Object> params = new ArrayList<>();

			if (maloai != null && !maloai.isEmpty()) {
				sql.append(" WHERE maloai = ?");
				params.add(maloai);
			} else if (key != null && !key.isEmpty()) {
				sql.append(" WHERE tensach LIKE ? OR tacgia LIKE ?");
				String searchKey = "%" + key + "%";
				params.add(searchKey);
				params.add(searchKey);
			}

			// Thực thi câu lệnh
			ps = conn.prepareStatement(sql.toString());
			
			// Gán tham số
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1); // Trả về giá trị của cột COUNT(*)
			}

		} catch (SQLException e) {
			System.err.println("Lỗi khi đếm sách: " + e.getMessage());
			throw new Exception("Lỗi truy vấn CSDL (đếm)", e);
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			if (conn != null) conn.close();
		}
		return 0;
	}
	
	public ArrayList<sach> getSach(String maloai, String key, int page) throws Exception {
		ArrayList<sach> ds = new ArrayList<>();
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		Connection conn = kn.cn;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// Xây dựng câu lệnh SQL động
			StringBuilder sql = new StringBuilder("SELECT * FROM sach");
			ArrayList<Object> params = new ArrayList<>();

			if (maloai != null && !maloai.isEmpty()) {
				sql.append(" WHERE maloai = ?");
				params.add(maloai);
			} else if (key != null && !key.isEmpty()) {
				sql.append(" WHERE tensach LIKE ? OR tacgia LIKE ?");
				String searchKey = "%" + key + "%";
				params.add(searchKey);
				params.add(searchKey);
			}

			// Thêm logic phân trang
			sql.append(" ORDER BY masach OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
			
			int offset = (page - 1) * PAGE_SIZE;
			params.add(offset);		// Tham số cho OFFSET
			params.add(PAGE_SIZE); 	// Tham số cho FETCH NEXT

			// Thực thi câu lệnh
			ps = conn.prepareStatement(sql.toString());

			// Gán tham số
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			
			rs = ps.executeQuery();

			// Duyệt kết quả
			while (rs.next()) {
				String masach = rs.getString("masach");
				String tensach = rs.getString("tensach");
				String tacgia = rs.getString("tacgia");
				long soluong = rs.getLong("soluong");
				long gia = rs.getLong("gia");
				String anh = rs.getString("anh");
				String maloaiDb = rs.getString("maloai");
				ds.add(new sach(masach, tensach, tacgia, soluong, gia, anh, maloaiDb));
			}

		} catch (SQLException e) {
			System.err.println("Lỗi khi lấy sách phân trang: " + e.getMessage());
			throw new Exception("Lỗi truy vấn CSDL (phân trang)", e);
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			if (conn != null) conn.close();
		}
		return ds;
	}
	
	//Lấy all danh sách sách
	public ArrayList<sach> getsach() throws Exception {
		ArrayList<sach> ds = new ArrayList<sach>();
		// b1: Kết nối vao csdl
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		// b2: Thiet lap cau lenh sql
		String sql = "select * from sach";
		PreparedStatement cmd = kn.cn.prepareStatement(sql);
		// b3: Truyen tham so vao cau lẹnh sql (neu co)
		// b4: Cho thuc hien cau lenh sql
		ResultSet rs = cmd.executeQuery();
		// b5: Duyet rs va luu vao mang dong
		while (rs.next()) {
			String masach = rs.getString("masach");
			String tensach = rs.getString("tensach");
			String tacgia = rs.getString("tacgia");
			long soluong = rs.getLong("soluong");
			long gia = rs.getLong("gia");
			;
			String anh = rs.getString("anh");
			;
			String maloai = rs.getString("maloai");
			ds.add(new sach(masach, tensach, tacgia, soluong, gia, anh, maloai));
		}
		// b6: Dong cac doi tuong dang mo: rs, ket noi
		rs.close();
		kn.cn.close();
		return ds;
	}
	
	// Thêm sách mới
	public boolean themSach(sach s) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		Connection conn = kn.cn; // Sử dụng connection từ KetNoi
		PreparedStatement ps = null;
		
		try {
			String sql = "INSERT INTO Sach (masach, tensach, tacgia, soluong, gia, anh, maloai) VALUES (?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, s.getMasach());
			ps.setString(2, s.getTensach());
			ps.setString(3, s.getTacgia());
			ps.setLong(4, s.getSoluong());
			ps.setLong(5, s.getGia());
			ps.setString(6, s.getAnh());
			ps.setString(7, s.getMaloai());
			
			int result = ps.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			System.err.println("Lỗi khi thêm sách: " + e.getMessage());
			throw new Exception("Lỗi thêm sách vào database");
		} finally {
			if (ps != null) ps.close();
			if (conn != null) conn.close();
		}
	}
	
	// Kiểm tra mã sách tồn tại
	public boolean checkMaSach(String masach) throws Exception {
	    KetNoi kn = new KetNoi(); kn.ketnoi();
	    String sql = "SELECT count(*) FROM sach WHERE masach=?";
	    PreparedStatement ps = kn.cn.prepareStatement(sql);
	    ps.setString(1, masach);
	    ResultSet rs = ps.executeQuery();
	    boolean tonTai = false;
	    if (rs.next()) {
	        tonTai = rs.getInt(1) > 0;
	    }
	    rs.close(); kn.cn.close();
	    return tonTai;
	}

	// Cập nhật sách
	public int suaSach(sach s) throws Exception {
	    KetNoi kn = new KetNoi(); kn.ketnoi();
	    // Nếu ảnh rỗng (không up ảnh mới) thì không update cột anh
	    String sql;
	    PreparedStatement ps;
	    
	    if (s.getAnh() == null || s.getAnh().isEmpty()) {
	        sql = "UPDATE sach SET tensach=?, tacgia=?, soluong=?, gia=?, maloai=? WHERE masach=?";
	        ps = kn.cn.prepareStatement(sql);
	        ps.setString(1, s.getTensach());
	        ps.setString(2, s.getTacgia());
	        ps.setLong(3, s.getSoluong());
	        ps.setLong(4, s.getGia());
	        ps.setString(5, s.getMaloai());
	        ps.setString(6, s.getMasach());
	    } else {
	        sql = "UPDATE sach SET tensach=?, tacgia=?, soluong=?, gia=?, maloai=?, anh=? WHERE masach=?";
	        ps = kn.cn.prepareStatement(sql);
	        ps.setString(1, s.getTensach());
	        ps.setString(2, s.getTacgia());
	        ps.setLong(3, s.getSoluong());
	        ps.setLong(4, s.getGia());
	        ps.setString(5, s.getMaloai());
	        ps.setString(6, s.getAnh());
	        ps.setString(7, s.getMasach());
	    }
	    
	    int kq = ps.executeUpdate();
	    kn.cn.close();
	    return kq;
	}
	
	// Xóa sách
	public boolean xoaSach(String masach) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		Connection conn = kn.cn; // Sử dụng connection từ KetNoi
		PreparedStatement ps = null;
		
		try {
			String sql = "DELETE FROM Sach WHERE masach=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, masach);
			
			int result = ps.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			System.err.println("Lỗi khi xóa sách: " + e.getMessage());
			throw new Exception("Lỗi xóa sách khỏi database");
		} finally {
			if (ps != null) ps.close();
			if (conn != null) conn.close();
		}
	}
	
	// Tìm sách theo mã
	public sach timSachTheoMa(String masach) throws Exception {
		KetNoi kn = new KetNoi();
		kn.ketnoi();
		Connection conn = kn.cn; // Sử dụng connection từ KetNoi
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM Sach WHERE masach=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, masach);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return new sach(
					rs.getString("masach"),
					rs.getString("tensach"),
					rs.getString("tacgia"),
					rs.getLong("soluong"),
					rs.getLong("gia"),
					rs.getString("anh"),
					rs.getString("maloai")
				);
			}
			return null;
		} catch (SQLException e) {
			System.err.println("Lỗi khi tìm sách theo mã: " + e.getMessage());
			throw new Exception("Lỗi truy vấn database");
		} finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			if (conn != null) conn.close();
		}
	}
}