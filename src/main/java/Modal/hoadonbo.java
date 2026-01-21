package Modal;

import java.sql.Connection;
import java.util.ArrayList;

public class hoadonbo {
    
    hoadondao hddao = new hoadondao();

    /**
     * Xử lý thanh toán: Tạo hóa đơn và các chi tiết hóa đơn
     * trong một giao dịch (transaction).
     * @param makh Mã khách hàng
     * @param gh Giỏ hàng
     */
    public long xuLyThanhToan(long makh, giohangbo gh, boolean daThanhToan) throws Exception {
        KetNoi kn = new KetNoi();
        kn.ketnoi();
        Connection conn = kn.cn;
        
        long maHoaDon = 0;

        try {
            // Tắt tự động lưu để quản lý giao dịch
            conn.setAutoCommit(false);

            // 1. Tạo hóa đơn và LẤY MÃ HÓA ĐƠN VỀ
            maHoaDon = hddao.taoHoaDon(makh, conn, daThanhToan);

            // 2. Thêm chi tiết hóa đơn
            for (giohang g : gh.getDs()) { 
                hddao.themChiTietHoaDon(maHoaDon, g.getMasach(), g.getSoluong(), conn);
            }

            conn.commit();
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        
        return maHoaDon;
    }

    /**
     * Lấy lịch sử mua hàng (chỉ gọi qua DAO)
     */
    public ArrayList<LichSuMuaHang> getLichSu(long makh) throws Exception {
        return hddao.getLichSu(makh);
    }
}