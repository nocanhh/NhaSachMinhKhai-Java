package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Modal.MoMoUtil;
import Modal.giohang;
import Modal.giohangbo;
import Modal.hoadonbo;
import Modal.khachhang;
import Modal.loai;
import Modal.loaibo;

/**
 * Servlet này CHỈ xử lý việc lưu đơn hàng vào CSDL và xóa giỏ hàng, sau đó
 * chuyển đến Lịch sử.
 */
@WebServlet("/xuLyThanhToanController")
public class xuLyThanhToanController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public xuLyThanhToanController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Ngăn người dùng truy cập bằng GET
		response.sendRedirect("tcController");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession();
			
			khachhang kh = (khachhang) session.getAttribute("un");
			ArrayList<giohang> dsMua = (ArrayList<giohang>) session.getAttribute("dsMuaHang");
			
			if (kh != null && dsMua != null && !dsMua.isEmpty()) {
				String phuongThuc = request.getParameter("phuongthuc");
				Long tongTienObj = (Long) session.getAttribute("tongTienMua");
				long tongTien = (tongTienObj != null) ? tongTienObj : 0;

				// --- 1. XỬ LÝ MOMO (Chuyển hướng ngay, chưa tạo đơn trong DB) ---
				if ("MOMO".equals(phuongThuc)) {
					String orderId = "DH" + System.currentTimeMillis();
					String returnUrl = "http://localhost:8080/NhaSachMinhKhai/MoMoReturnController"; 
					String payUrl = MoMoUtil.createPayment(orderId, UUID.randomUUID().toString(), String.valueOf(tongTien), "Thanh toan sach", returnUrl, returnUrl);
					if (payUrl != null) response.sendRedirect(payUrl);
					return;
				}

				// --- 2. XỬ LÝ COD HOẶC QR ---
				// (Tạo hóa đơn trước rồi mới tính tiếp)
				hoadonbo hdbo = new hoadonbo();
				giohangbo ghTam = new giohangbo();
				for(giohang g : dsMua) ghTam.Them(g.getMasach(), g.getTensach(), g.getGia(), g.getSoluong(), g.getAnh());
				
				// Mặc định tạo đơn là CHƯA thanh toán (false)
				long maHoaDonMoi = hdbo.xuLyThanhToan(kh.getMakh(), ghTam, false);
				
				// Xóa giỏ hàng sau khi tạo đơn thành công
				giohangbo ghChinh = (giohangbo) session.getAttribute("gio");
				String[] maDaMua = new String[dsMua.size()];
				for(int i=0; i<dsMua.size(); i++) maDaMua[i] = dsMua.get(i).getMasach();
				ghChinh.xoaDaMua(maDaMua);
				session.setAttribute("gio", ghChinh);
				session.removeAttribute("dsMuaHang");
				session.removeAttribute("tongTienMua");

				if ("QR".equals(phuongThuc)) {
					// NẾU CHỌN QR -> CHUYỂN NGAY SANG TRANG THANH TOÁN LẠI (CÓ SEPAY AUTO)
					response.sendRedirect("ThanhToanLaiController?mahd=" + maHoaDonMoi);
					return;
				} 
				
				// NẾU LÀ COD -> Về lịch sử báo thành công
				request.setAttribute("tb", "Đặt hàng thành công! (Thanh toán khi nhận hàng)");
				request.getRequestDispatcher("lichsumuahangController").forward(request, response);
				
				loaibo lbo = new loaibo();
				ArrayList<loai> dsloai = lbo.getloai();
				request.setAttribute("dsloai", dsloai);
				
			} else {
				response.sendRedirect("tcController");
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
			response.sendRedirect("giohangController");
		}
	}
}