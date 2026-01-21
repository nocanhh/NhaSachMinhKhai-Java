package Modal;

import java.util.ArrayList;

public class giohangbo {
    private ArrayList<giohang> ds = new ArrayList<>();

    public ArrayList<giohang> getDs() {
        return ds;
    }

    // Thêm 1 sách vào giỏ
    public void Them(String masach, String tensach, long gia, long soluong, String anh) {
        for (giohang g : ds) {
            if (g.getMasach().equals(masach)) {
                g.setSoluong(g.getSoluong() + soluong);
                return;
            }
        }
        ds.add(new giohang(masach, tensach, soluong, gia, anh));
    }

    // Xóa 1 sản phẩm khỏi giỏ
    public void Xoa(String masach) {
        ds.removeIf(g -> g.getMasach().equals(masach));
    }

    // Cập nhật số lượng
    public void CapNhat(String masach, long soluongmoi) {
        if (soluongmoi <= 0) {
            Xoa(masach); // Xóa sản phẩm nếu số lượng <= 0
            return;
        }
        for (giohang g : ds) {
            if (g.getMasach().equals(masach)) {
                g.setSoluong(soluongmoi);
                break;
            }
        }
    }

    // Tính tổng tiền
    public long TongTien() {
        long tong = 0;
        for (giohang g : ds)
            tong += g.getThanhtien();
        return tong;
    }

    // Kiểm tra sách đã có trong giỏ chưa
    public boolean coTrongGio(String masach) {
        for (giohang g : ds) {
            if (g.getMasach().equals(masach))
                return true;
        }
        return false;
    }
    
    // Trả về danh sách các món hàng dựa trên mảng mã sách được chọn
    public ArrayList<giohang> getDanhSachMua(String[] listMaSach) {
        ArrayList<giohang> dsMua = new ArrayList<>();
        if (listMaSach == null) return dsMua; 

        for (String maChon : listMaSach) {
            for (giohang g : ds) {
                if (g.getMasach().equals(maChon)) {
                    dsMua.add(g);
                    break;
                }
            }
        }
        return dsMua;
    }
    
    // Xóa các món đã mua khỏi giỏ hàng chính
    public void xoaDaMua(String[] listMaSach) {
        if (listMaSach == null) return;
        
        for (String maChon : listMaSach) {
            ds.removeIf(g -> g.getMasach().equals(maChon));
        }
    }
}