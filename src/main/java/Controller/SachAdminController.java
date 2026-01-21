package Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import Modal.sach;
import Modal.sachdao;
import Modal.sachbo;
import Modal.loaibo;

@WebServlet("/SachAdminController")
public class SachAdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DEBUG: ---> Đã vào doGet SachAdminController");
        
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            System.out.println("DEBUG: Chưa đăng nhập Admin -> Chuyển về Login");
            response.sendRedirect("AdminLoginController");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String ms = request.getParameter("ms");
        String key = request.getParameter("key");

        sachdao sdao = new sachdao();
        sachbo sbo = new sachbo();

        try {
            // --- XÓA ---
            if ("xoa".equals(action) && ms != null) {
                try {
                    sdao.xoaSach(ms);
                    request.setAttribute("tb", "Đã xóa sách: " + ms);
                } catch (Exception e) {
                    request.setAttribute("error", "Không thể xóa! Sách này đã có trong hóa đơn mua hàng.");
                }
            }
            
            // --- CHUYỂN SANG TRANG SỬA ---
            if ("edit".equals(action) && ms != null) {
               sach s = sdao.timSachTheoMa(ms); 
               request.setAttribute("sachEdit", s);
               
               loaibo lbo = new loaibo();
               request.setAttribute("dsloai", lbo.getloai());
               request.getRequestDispatcher("admin/sua_sach.jsp").forward(request, response);
               return; 
            }

            // --- LOAD DANH SÁCH ---
            if (key != null && !key.isEmpty()) request.setAttribute("dssach", sbo.Tim(key));
            else request.setAttribute("dssach", sbo.getsach());
            
            loaibo lbo = new loaibo();
            request.setAttribute("dsloai", lbo.getloai());

            request.getRequestDispatcher("admin/quanly_sach.jsp").forward(request, response);
            
        } catch (Exception e) { 
            System.err.println("DEBUG: LỖI TRONG DOGET!");
            e.printStackTrace(); 
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DEBUG: ---> Đã vào doPost SachAdminController (Upload)");
        request.setCharacterEncoding("UTF-8");
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        String action="them", masach=null, tensach=null, tacgia=null, maloai=null, anh=null;
        long gia=0, soluong=0;

        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            // Vòng lặp 1: Lấy các tham số Text trước để biết Action và MaSach
            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {
                    String fieldName = fileItem.getFieldName();
                    String value = fileItem.getString("UTF-8");
                    switch (fieldName) {
                        case "action": action = value; break;
                        case "masach": masach = value; break;
                        case "tensach": tensach = value; break;
                        case "tacgia": tacgia = value; break;
                        case "gia": gia = Long.parseLong(value); break;
                        case "soluong": soluong = Long.parseLong(value); break;
                        case "maloai": maloai = value; break;
                    }
                }
            }
            
            sachdao sdao = new sachdao();

            // 1. KIỂM TRA TRÙNG MÃ (CHỈ KHI THÊM)
            if ("them".equals(action)) {
                if (sdao.checkMaSach(masach)) {
                    request.setAttribute("error", "Mã sách '" + masach + "' đã tồn tại!");
                    doGet(request, response); 
                    return;
                }
            }

            // Vòng lặp 2: Xử lý file ảnh (nếu có)
            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField()) {
                    String nameimg = fileItem.getName();
                    if (!nameimg.equals("")) {
                        String dirUrl = request.getServletContext().getRealPath("") + File.separator + "image_sach";
                        File dir = new File(dirUrl);
                        if (!dir.exists()) dir.mkdir();
                        File file = new File(dirUrl + File.separator + nameimg);
                        fileItem.write(file);
                        anh = "image_sach/" + nameimg;
                    }
                }
            }
            
            sach s = new sach(masach, tensach, tacgia, soluong, gia, anh, maloai);
            
            if ("them".equals(action)) {
                sdao.themSach(s);
                request.setAttribute("tb", "Thêm sách thành công!");
            } else if ("sua".equals(action)) {
                sdao.suaSach(s); // Hàm suaSach sẽ tự kiểm tra nếu anh=null thì giữ ảnh cũ
                request.setAttribute("tb", "Cập nhật sách thành công!");
            }
            
            doGet(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            doGet(request, response);
        }
    }
}