package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ThongKe {

    private String masp, tongtien, ngay, tensp, dongia;
    private int soluong, doanhthu;
    public static ArrayList<ThongKe> tk = new ArrayList<>();

    public ThongKe() {
    }

    public ThongKe(String masp, String tongtien, String ngay, String tensp, String dongia, int soluong, int doanhthu) {
        this.masp = masp;
        this.tongtien = tongtien;
        this.ngay = ngay;
        this.tensp = tensp;
        this.dongia = dongia;
        this.soluong = soluong;
        this.doanhthu = doanhthu;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public static ArrayList<ThongKe> getTk() {
        return tk;
    }

    public static void setTk(ArrayList<ThongKe> tk) {
        ThongKe.tk = tk;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(int doanhthu) {
        this.doanhthu = doanhthu;
    }

    //Update SoLuong vao bang ThongKe
    public boolean capnhatsoluong(String masp, int soluong, String ngay) {
        Connection con = null;
        try {
            getData data = new getData();
            con = data.getConnect();
            String sql = "Insert into ThongKe(MaSP,SL,Ngay) values(?,?,?)";
            PreparedStatement spt = con.prepareStatement(sql);
            spt.setString(1, masp);
            spt.setInt(2, soluong);
            spt.setString(3, ngay);
            int result = spt.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }
    // Xuat danh sach san pham theo ngay

    public List<ThongKe> xuatdstheongay(String ngaydau, String ngaycuoi) {
        Connection con = null;
        List<ThongKe> ds = new ArrayList<>();
        try {
            con = new getData().getConnect();
            String sql = "select ThongKe.Ngay,SanPham.TenSP,SanPham.GiaSP, ThongKe.SL,SanPham.GiaSP*ThongKe.SL as 'thành tiền'\n"
                    + "from SanPham join ThongKe on SanPham.MaSP = ThongKe.MaSP\n"
                    + "WHERE ThongKe.Ngay BETWEEN ? AND ?\n"
                    + "group by ThongKe.Ngay,SanPham.TenSP, SanPham.GiaSP,ThongKe.SL";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, ngaydau);
            pst.setString(2, ngaycuoi);
            ResultSet rs = pst.executeQuery();

            int tongdoanhthu = 0;
            ThongKe tke = new ThongKe();
            while (rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setNgay(rs.getString("Ngay"));
                tk.setTensp(rs.getString("TenSP"));
                tk.setDongia(rs.getString("GiaSP"));
                tk.setSoluong(rs.getInt("SL"));
                tk.setTongtien(rs.getString("Thành Tiền"));
                ds.add(tk);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ds;
    }
// phương thức tính tổng doanh thu theo khoảng ngày
    public int tongdoanhthu(String ngaydau, String ngaycuoi) {
        Connection con = null;
        List<ThongKe> ds = new ArrayList<>();
        int sum = 0;
        try {
            con = new getData().getConnect();
            String sql = "select ThongKe.Ngay,SanPham.TenSP,SanPham.GiaSP, ThongKe.SL,SanPham.GiaSP*ThongKe.SL as 'thành tiền'\n"
                    + "from SanPham join ThongKe on SanPham.MaSP = ThongKe.MaSP\n"
                    + "WHERE ThongKe.Ngay BETWEEN ? AND ?\n"
                    + "group by ThongKe.Ngay,SanPham.TenSP, SanPham.GiaSP,ThongKe.SL";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, ngaydau);
            pst.setString(2, ngaycuoi);
            ResultSet rs = pst.executeQuery();

            int tongdoanhthu = 0;
            ThongKe tke = new ThongKe();
            while (rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setTongtien(rs.getString("Thành Tiền"));
                tongdoanhthu = Integer.parseInt(rs.getString("Thành Tiền"));
                sum = sum + tongdoanhthu;
            }
            tke.setDoanhthu(sum);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return sum;
    }
//Nút xóa doanh thu theo ngày
    public boolean xoaDoanhThu(String ngay) {
        Connection con = null;
        try {
            getData data = new getData();
            con = data.getConnect();
            String sql = "Delete from ThongKe where Ngay=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ngay);
            int result = ps.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }

    public static void main(String[] args) {
        ThongKe tk = new ThongKe();
//        boolean rs = tk.capnhatsoluong("100", 1);
//        System.out.println(rs);
        List<ThongKe> ds = tk.xuatdstheongay("21/12/2017", "24/12/2017");
        int sum = tk.tongdoanhthu("21/12/2017", "24/12/2017");
        System.out.println(sum);
    }
}
