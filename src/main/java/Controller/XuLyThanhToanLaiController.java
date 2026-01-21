package Controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Modal.HoaDonAdminDao;
import Modal.MoMoUtil;
import Modal.hoadondao;

/**
 * Servlet implementation class XuLyThanhToanLaiController
 */
@WebServlet("/XuLyThanhToanLaiController")
public class XuLyThanhToanLaiController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public XuLyThanhToanLaiController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			String phuongThuc = request.getParameter("phuongthuc");
			String maHoaDonStr = request.getParameter("maHoaDon");
			String tongTien = request.getParameter("tongTien");
			
            // Lấy mã giao dịch người dùng nhập
			String maGiaoDich = request.getParameter("maGiaoDich");

			if(maHoaDonStr == null) { response.sendRedirect("lichsumuahangController"); return; }
			long maHoaDon = Long.parseLong(maHoaDonStr);

			if ("MOMO".equals(phuongThuc)) {
				// --- 1. MOMO ---
				String orderId = "REPAY_" + maHoaDon + "_" + System.currentTimeMillis();
				String returnUrl = "http://localhost:8080/NhaSachMinhKhai/MoMoRepayReturnController"; 
				String payUrl = MoMoUtil.createPayment(orderId, UUID.randomUUID().toString(), tongTien, "Thanh toan don " + maHoaDon, returnUrl, returnUrl);
				if(payUrl != null) response.sendRedirect(payUrl);
				else response.sendRedirect("lichsumuahangController");
				
			} else if ("QR".equals(phuongThuc)) {
				// --- 2. QR CODE ---
				if (maGiaoDich != null && !maGiaoDich.trim().isEmpty()) {
					hoadondao dao = new hoadondao();
					dao.capNhatDaMua(maHoaDon);
					request.setAttribute("tb", "Cảm ơn! Hệ thống đã ghi nhận thanh toán (Mã GD: " + maGiaoDich + ").");
				} else {
					request.setAttribute("tb", "Đã lưu yêu cầu thanh toán QR. Vui lòng thanh toán sau.");
				}
				request.getRequestDispatcher("lichsumuahangController").forward(request, response);
				
			} else {
				// --- 3. COD ---
				request.setAttribute("tb", "Đã chọn thanh toán khi nhận hàng.");
				request.getRequestDispatcher("lichsumuahangController").forward(request, response);
			}
			
		} catch (Exception e) { 
			e.printStackTrace(); 
			response.sendRedirect("lichsumuahangController");
		}
	}

}
