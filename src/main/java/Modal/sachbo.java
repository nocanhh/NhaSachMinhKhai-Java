package Modal;

import java.util.ArrayList;

public class sachbo {
    sachdao sdao = new sachdao();
    //ArrayList<sach> ds;

    public ArrayList<sach> getSach(String maloai, String key, int page) throws Exception {
        return sdao.getSach(maloai, key, page);
    }
    
    public int demSach(String maloai, String key) throws Exception {
    	return sdao.demSach(maloai, key);
    }
    
    // Lấy toàn bộ danh sách sách
    public ArrayList<sach> getsach() throws Exception {
        return sdao.getsach();
    }

    // Tìm theo mã loại
    public ArrayList<sach> TimMa(String maloai) throws Exception {
    	ArrayList<sach> ds = sdao.getsach();
        if (ds == null)
            ds = getsach();

        ArrayList<sach> tam = new ArrayList<>();
        for (sach s : ds) {
            if (s.getMaloai().equalsIgnoreCase(maloai))
                tam.add(s);
        }
        return tam;
    }

    // Tìm theo từ khóa (tên sách hoặc tên tác giả)
    public ArrayList<sach> Tim(String key) throws Exception {
    	ArrayList<sach> ds = sdao.getsach();
        if (ds == null)
            ds = getsach();

        ArrayList<sach> tam = new ArrayList<>();
        for (sach s : ds) {
            if (s.getTensach().toLowerCase().contains(key.toLowerCase()) ||
                s.getTacgia().toLowerCase().contains(key.toLowerCase()))
                tam.add(s);
        }
        return tam;
    }
}
