package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Modal.LichSuMuaHang;
import Modal.hoadondao;
import Modal.loai;
import Modal.loaibo;

/**
 * Servlet implementation class ThanhToanLaiController
 */
@WebServlet("/ThanhToanLaiController")
public class ThanhToanLaiController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ThanhToanLaiController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String maHoaDonStr = request.getParameter("mahd");
			
			if(maHoaDonStr != null && !maHoaDonStr.isEmpty()) {
				long maHoaDon = Long.parseLong(maHoaDonStr);
				
				hoadondao dao = new hoadondao();
				// 1. Lấy tổng tiền
				long tongTien = dao.getTongTienHoaDon(maHoaDon);
				// 2. Lấy danh sách sản phẩm
				ArrayList<LichSuMuaHang> dsChiTiet = dao.getChiTietDonHang(maHoaDon);
				
				// Gửi sang JSP
				request.setAttribute("maHoaDon", maHoaDon);
				request.setAttribute("tongTien", tongTien);
				request.setAttribute("dsChiTiet", dsChiTiet);
				
				loaibo lbo = new loaibo();
	            ArrayList<loai> dsloai = lbo.getloai();
	            request.setAttribute("dsloai", dsloai);
				
				request.getRequestDispatcher("thanhtoan_lai.jsp").forward(request, response);
			} else {
				response.sendRedirect("lichsumuahangController");
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
			response.sendRedirect("lichsumuahangController");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
