/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ModelSanPham;
import Model.getData;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Computer
 */
public class ControllerSanPham {

    ModelSanPham modelsp;
    PreparedStatement spt = null;
    ResultSet rs = null;
    DefaultTableModel model;

    // Lay gia tri Column dua vao table SP
    public Vector vcotsp() {
        Vector vcot = new Vector();
        vcot.add("MãSP");
        vcot.add("Sản Phẩm");
        vcot.add("Đơn vị tính");
        vcot.add("Đơn giá");
        return vcot;
    }

    // Lay gia tri Row dua vao table SP
    public Vector vdongsp(String masp) {

        Vector vdong = new Vector();
        modelsp = new ModelSanPham();
        List<ModelSanPham> ds = modelsp.timSP(masp);
        for (int i = 0; i < ds.size(); i++) {
            Vector v = new Vector();
            v.add(ds.get(i).getMasp());
            v.add(ds.get(i).getGiasp());
            v.add(ds.get(i).getDvt());
            v.add(ds.get(i).getGiasp());
            vdong.addElement(v);
        }
        return vdong;
    }

    // Lay gia tri Column dua vao table SP
    public Vector vcotlistsp() {
        Vector vcot = new Vector();
        vcot.add("MãSP");
        vcot.add("Sản Phẩm");
        vcot.add("Đơn vị tính");
        vcot.add("Đơn giá");
        return vcot;
    }

    // Lay gia tri Row dua vao table SP
    public Vector vdonglistsp() {

        Vector vdong = new Vector();
        modelsp = new ModelSanPham();
        List<ModelSanPham> ds = modelsp.getlistsp();
        for (int i = 0; i < ds.size(); i++) {
            Vector v = new Vector();
            v.add(ds.get(i).getMasp());
            v.add(ds.get(i).getTensp());
            v.add(ds.get(i).getDvt());
            v.add(ds.get(i).getGiasp());
            vdong.addElement(v);
        }
        return vdong;
    }

    // Lay gia tri Column dua vao table Ban
    public Vector vcotban() {
        Vector vcot = new Vector();
        vcot.add("Sản Phẩm");
        vcot.add("Số Lượng");
        vcot.add("Đơn Giá");
        vcot.add("Thành Tiền");
        return vcot;
    }

    // Lay gia tri Row dua vao table Ban
    public Vector vdongban(String maban) {

        Vector vdong = new Vector();
        modelsp = new ModelSanPham();
        List<ModelSanPham> ds = modelsp.gettheomaBan(maban);
        for (int i = 0; i < ds.size(); i++) {
            Vector v = new Vector();
            v.add(ds.get(i).getTensp());
            v.add(ds.get(i).getSoluong());
            v.add(ds.get(i).getGiasp());
            v.add(ds.get(i).getTongtien());

            vdong.addElement(v);
        }
        return vdong;
    }

    //Gan Column va Row vao Table SP
    public void loadTableSP(JTable table, String masp) {
        model = new DefaultTableModel();
        model.setDataVector(vdongsp(masp), vcotsp());
        table.setModel(model);

    }

    public void loadTablelistSP(JTable table) {
        model = new DefaultTableModel();
        model.setDataVector(vdonglistsp(), vcotlistsp());
        table.setModel(model);

    }

    //Gan Column va Row vao Table Ban
    public void loadTableBan(JTable table, String maban) {
        model = new DefaultTableModel();
        model.setDataVector(vdongban(maban), vcotban());
        table.setModel(model);

    }

    // Insert SanPham 
    public boolean themBan(String maban, String masp, int soluong, int size) {
        Connection con = null;
        modelsp = new ModelSanPham();
        List<ModelSanPham> ds = modelsp.getlistsp();

        try {

            getData data = new getData();
            con = data.getConnect();
            String sql = "Insert into GoiSP(MaGoi,MaBan,MaSP,SoLuong) values(?,?,?,?)";
            spt = con.prepareStatement(sql);
            spt.setInt(1, size);
            spt.setString(2, maban);
            spt.setString(3, masp);
            spt.setInt(4, soluong);
            int rs = spt.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn bàn !");
        }
        return false;
    }
    //Remove 1 SanPham

    public boolean xoa1sp(String maban, String masp) {
        Connection con = null;
        try {
            getData data = new getData();
            con = data.getConnect();
            String sql = "Delete from GoiSP where MaBan=? and MaSP=?";
            spt = con.prepareStatement(sql);
            spt.setString(1, maban);
            spt.setString(2, masp);
            int rs = spt.executeUpdate();
            if (rs > 0) {

                return true;

            }
        } catch (Exception ex) {
           // JOptionPane.showMessageDialog(null, ex);
        }
        return false;
    }

    // Nhap san pham vao Ban
    public void nhapsp(ControllerSanPham csanpham, JTable tableSP, JTable tableBan, JTextField txtthanhtien, String maban, String masp, int size, int vitridong) {

        String laymaban = "";
        String dongia;
        int soluong;
        try {
            if (vitridong != -1) {
//                DefaultTableModel md = new DefaultTableModel();
                model = new DefaultTableModel();
                csanpham = new ControllerSanPham();

                laymaban = maban;
                soluong = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Số lượng", "Nhập số lượng:", JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/Image/cup.png")), null, null));
//                JOptionPane.showMessageDialog(null,laymaban+masp);
                csanpham.themBan(laymaban, masp, soluong, size);
//                JOptionPane.showMessageDialog(null, maban);
//                JOptionPane.showMessageDialog(null, masp);
                csanpham.loadTableBan(tableBan, laymaban);
                csanpham.loadTablelistSP(tableSP);

            }
        } catch (Exception ex) {
        }

    }

    //Cap nhat soluong san pham
    public boolean capnhatsoluong(String masp, int soluong) {
        Connection con = null;
        try {
            getData data = new getData();
            con = data.getConnect();
            String sql = "Update GoiSP set SoLuong=? where MaSP=?";
            spt = con.prepareStatement(sql);
            spt.setInt(1, soluong);
            spt.setString(2, masp);
            int rs = spt.executeUpdate();
            if (rs > 0) {

                return true;

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return false;
    }

    //Chuyen ban
    public boolean chuyenban(String masp, String mabancu, String mabanmoi, int magoi) {

        Connection con = null;
        try {
            modelsp = new ModelSanPham();
            ModelSanPham ds;
//            ds = modelsp.laymagoi(mabancu, masp);
//            int magoi = ds.getMagoi();
            getData data = new getData();
            con = data.getConnect();
            String sql = "Update GoiSP set MaSP=?, MaBan=? where MaGoi=?";
            spt = con.prepareStatement(sql);
            spt.setString(1, masp);
            spt.setString(2, mabanmoi);
            spt.setInt(3, magoi);
            int result = spt.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception ex) {
          //  JOptionPane.showMessageDialog(null, ex);
        }
        return false;
    }
//In hoa don

    public void inhoadon(JTable table) {
        JFileChooser chooser = new JFileChooser();
        int n = chooser.showSaveDialog(chooser);
        if (n == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                FileWriter out = new FileWriter(file + ".txt");
                BufferedWriter bwriter = new BufferedWriter(out);
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                
                // In ten cot
//                for (int i = 0; i < table.getColumnCount(); i++) {
//                    bwriter.write(model.getColumnName(i).toString());
//                }
//                bwriter.write("\n");
                //In thong tin dong
                for (int i = 0; i <table.getRowCount(); i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        bwriter.write((String.valueOf( model.getValueAt(i, j) + "\t")));
                    }
                    bwriter.write("\n");
                }
                bwriter.close();
                JOptionPane.showMessageDialog(null, "Lưu file thành công");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi lưu file!"+ex);
            }
        }
    }
    // **@**
    // ** QUẢN LÝ SẢN PHẨM - PHẠM QUỐC KHÁNH
    // **@**
    public Vector vtCol(){
        Vector vtCol = new Vector();
        vtCol.add("STT");
        vtCol.add("Mã Sản Phẩm");
        vtCol.add("Tên Sản Phẩm");
        vtCol.add("Giá");
        vtCol.add("Đơn Vị Tính");
        vtCol.add("URL");
        vtCol.add("Ghi Chú");
        return vtCol;
    }
    
    public Vector vtColList() {
        Vector vcot = new Vector();
        vcot.add("STT");
        vcot.add("Mã Sản Phẩm");
        vcot.add("Tên Sản Phẩm");
        vcot.add("Giá");
        vcot.add("Đơn vị tính");
        vcot.add("URL");
        vcot.add("Ghi Chú");
        return vcot;
    }
    
    public Vector vtRow(){
        Vector vtRow = new Vector();
        modelsp = new ModelSanPham();
        List<ModelSanPham> list = modelsp.getlistqlsp();
        for (int i = 0; i < list.size(); i++) {
            Vector v = new Vector();
            v.add(list.get(i).getMasp());
            v.add(list.get(i).getTensp());
            v.add(list.get(i).getGiasp());
            v.add(list.get(i).getDvt());
            v.add(list.get(i).getUrl());
            v.add(list.get(i).getGhichu());
            vtRow.addElement(v);
        }
        return vtRow;
    }
    
    public Vector vtRowList() {

        Vector vtRow = new Vector();
        modelsp = new ModelSanPham();
        List<ModelSanPham> list = modelsp.getlistqlsp();
        for (int i = 0; i < list.size(); i++) {
            int t =i;
            t++;
            
            Vector v = new Vector();
            v.add(t);
            v.add(list.get(i).getMasp());
            v.add(list.get(i).getTensp());
            v.add(list.get(i).getGiasp());
            v.add(list.get(i).getDvt());
            v.add(list.get(i).getUrl());
            v.add(list.get(i).getGhichu());
            vtRow.addElement(v);
        }
        return vtRow;
    }
    
    public void tbSP(JTable table, String masp){
        model = new DefaultTableModel();
        model.setDataVector(vtRow(), vtCol());
        table.setModel(model);

    }

    public void tbListSP(JTable table) {
        model = new DefaultTableModel();
        model.setDataVector(vtRowList(), vtColList());
        table.setModel(model);

    }
    
    // KẾT THÚC QUẢN LÝ SẢN PHẨM - PHẠM QUỐC KHÁNHs
    
 public Vector vtRowMaSP(String MaSP) {

        Vector vtRow = new Vector();
        modelsp = new ModelSanPham();
        List<ModelSanPham> list = modelsp.locMaSP(MaSP);
        for (int i = 0; i < list.size(); i++) {
            int t =i;
            t++;
            
            Vector v = new Vector();
            v.add(t);
            v.add(list.get(i).getMasp());
            v.add(list.get(i).getTensp());
            v.add(list.get(i).getGiasp());
            v.add(list.get(i).getDvt());
            v.add(list.get(i).getUrl());
            v.add(list.get(i).getGhichu());
            vtRow.addElement(v);
        }
        return vtRow;
    }
    public void loadtbSP(JTable table, String masp){
        model = new DefaultTableModel();
        model.setDataVector(vtRowMaSP(masp), vtCol());
        table.setModel(model);

    }
 public boolean timkiem(String masp, JTable table) {
        modelsp = new ModelSanPham();
        List<ModelSanPham> ds = modelsp.locMaSP(masp);
        if (ds.size() > 0) {
            loadTableSP(table, masp);
            return true;
        } else {
            return false;
        }
    }
    public static void main(String[] args) {
        ControllerSanPham csanpham = new ControllerSanPham();
        boolean result = csanpham.chuyenban("red", "b2", "b3", 7);
        System.out.println(result);
//        ModelSanPham modelsp=new ModelSanPham();
//        ModelSanPham ds = modelsp.laymagoi("b3","red");
//            String magoi = ds.getMagoi();
//            System.out.println(magoi);
    }
}
