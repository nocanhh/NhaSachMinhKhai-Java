package Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Modal.giohang;
import Modal.giohangbo;
import Modal.khachhang;
import Modal.loai;
import Modal.loaibo;

@WebServlet("/thanhtoanController")
public class thanhtoanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public thanhtoanController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	// Trong hàm doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			
			// 1. Kiểm tra đăng nhập
			if (session.getAttribute("un") == null) {
				session.setAttribute("redirectUrl", "thanhtoanController");
				session.setAttribute("tb_dangnhap", "Vui lòng đăng nhập để thanh toán!");
				response.sendRedirect("dangnhapController");
				return;
			}

			// 2. Lấy giỏ hàng
			giohangbo gh = (giohangbo) session.getAttribute("gio");
			
			// 3. Lấy danh sách mã sách ĐƯỢC CHỌN từ checkbox
			String[] listMaDaChon = request.getParameterValues("chonMua");
			
			if (listMaDaChon == null || listMaDaChon.length == 0) {
				response.sendRedirect("giohangController");
				return;
			}

			// 4. Tạo danh sách mua hàng (Chỉ chứa các món đã tick)
			ArrayList<giohang> dsMua = gh.getDanhSachMua(listMaDaChon);
			
			long tongTienMua = 0;
			for(giohang g : dsMua) tongTienMua += g.getThanhtien();

			// 5. Lưu vào Session
			session.setAttribute("dsMuaHang", dsMua); 
			session.setAttribute("tongTienMua", tongTienMua);

			loaibo lbo = new loaibo();
			request.setAttribute("dsloai", lbo.getloai());
			
			request.getRequestDispatcher("thanhtoan.jsp").forward(request, response);

		} catch (Exception e) { 
			e.printStackTrace();
			response.sendRedirect("giohangController");
		}
	}
}