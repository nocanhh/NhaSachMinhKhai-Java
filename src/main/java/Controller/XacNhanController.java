package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Modal.AdminDonHang;
import Modal.LichSuMuaHang;
import Modal.XacNhanDao;

@WebServlet("/XacNhanController")
public class XacNhanController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			XacNhanDao dao = new XacNhanDao();
			String action = request.getParameter("action");

			// --- TRƯỜNG HỢP 1: QUÉT ĐƠN TỒN ---
			if ("xoaton".equals(action)) {
				dao.xoaDonHangTon(); // Gọi hàm xóa trong DAO
				request.setAttribute("tb", "Đã dọn dẹp thành công các đơn hàng treo quá 15 ngày!");
			}

			// --- TRƯỜNG HỢP 1: Bấm nút XÁC NHẬN ---
			if ("xacnhan".equals(action)) {
				String maHoaDonStr = request.getParameter("mahd");
				if (maHoaDonStr != null) {
					long maHoaDon = Long.parseLong(maHoaDonStr);
					dao.xacNhanDon(maHoaDon);
					request.setAttribute("tb", "Đã duyệt đơn hàng số: " + maHoaDon);
				}
			}

			// --- TRƯỜNG HỢP 2: Bấm nút XEM CHI TIẾT (Quan trọng) ---
			if ("chitiet".equals(action)) {
				String maHoaDonStr = request.getParameter("mahd");
				if (maHoaDonStr != null) {
					long maHoaDon = Long.parseLong(maHoaDonStr);

					// 1. Lấy thông tin chung (Header)
					AdminDonHang thongTin = dao.getThongTinDonHang(maHoaDon);
					request.setAttribute("thongTin", thongTin);

					// 2. Lấy danh sách sách (Detail) -> Trả về list LichSuMuaHang
					ArrayList<LichSuMuaHang> dsSach = dao.getChiTietSach(maHoaDon);
					request.setAttribute("dsChiTiet", dsSach);

					request.setAttribute("maHD", maHoaDon);
					request.getRequestDispatcher("admin/chitiet_donhang.jsp").forward(request, response);
					return; 
				}
			}

			request.setAttribute("dsDonHang", dao.getDanhSachCho());
			request.getRequestDispatcher("admin/xacnhan.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}