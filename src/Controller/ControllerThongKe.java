
package Controller;

import Model.ThongKe;
import com.sun.javafx.geom.Vec2d;
import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ControllerThongKe {
    
    ThongKe tk;
    
    public Vector vcot() {
        Vector vcot = new Vector();
        vcot.add("STT");
        vcot.add("Ngày");
        vcot.add("Tên SP");
        vcot.add("Đơn Giá");
        vcot.add("Số Lượng");
        vcot.add("Thành Tiền");
        return vcot;
    }
    
    public Vector vdong(String ngaydau, String ngaycuoi) {
        Vector vdong = new Vector();
        tk = new ThongKe();
        List<ThongKe> ds = tk.xuatdstheongay(ngaydau, ngaycuoi);
        for (int i = 0; i < ds.size(); i++) {
            int t = i + 1;
            Vector v = new Vector();
            v.add(t);
            v.add(ds.get(i).getNgay());
            v.add(ds.get(i).getTensp());
            v.add(ds.get(i).getDongia());
            v.add(ds.get(i).getSoluong());
            v.add(ds.get(i).getTongtien());
            vdong.addElement(v);
        }
        return vdong;
    }
    
    //Load du lieu len table
    public void loadTableTK(String ngaydau, String ngaycuoi, JTable table) {
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(vdong(ngaydau, ngaycuoi), vcot());
        table.setModel(model);
    }
    
    
}
