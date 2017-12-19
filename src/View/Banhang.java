/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CQLSP;
import Controller.ControllerKho;
import Controller.ControllerSanPham;
import Controller.checkSymbolK;
import Model.ModelKho;
import Model.ModelSanPham;
import Model.ModelUser;
import Model.ThongKe;
import Model.getData;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import sun.awt.AWTAccessor;

/**
 *
 * @author Administrator
 */
public class Banhang extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
//    int b1 = 1, b2 = 1, b3 = 1, b4 = 1, b5 = 1, b6 = 1, b7 = 1, b8 = 1, b9 = 1, b10 = 1, b11 = 1, b12 = 1;
    int vitri = 0;
    int soluong = 0;
    int sizelistsp = 0;
    int price = 0;
    public static Banhang BH;
//    boolean kiemtra = false;
    String maban = "", masanpham = "";
    ControllerSanPham csanpham;
    ControllerKho ckho;
    CQLSP Cqlsp;
    DefaultTableModel model;
    ModelSanPham modelsp;
    JLabel[] lb;
    ModelKho modelkho;
    //
    //
    //
    // Khai bao cua Lai
    Connection con = null;
    ResultSet rs = null;
    Statement st = null;
    PreparedStatement pst = null;
    private String GioiTinh;
    private String Roles;
    //strin filename
    String fileImage = null;
    byte[] imageNV = null;
    ArrayList<ModelUser> ListNV = new ArrayList<ModelUser>();

    DefaultTableModel tblModel = new DefaultTableModel();

    Vector row;

    //
    // end lai
    //
    public Banhang() {

        initComponents();
        setTitle("Phần mềm quản lý quán Cafe");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setCursor(Cursor.HAND_CURSOR);
        modelsp = new ModelSanPham();
        modelkho = new ModelKho();
        panelban.setLayout(new GridLayout(4, 3, 4, 4));
        loadiconBan();
        csanpham = new ControllerSanPham();
        ckho = new ControllerKho();
        Cqlsp = new CQLSP();
        //Do du lieu vao table sanpham
        csanpham.loadTablelistSP(tableSP);
        Cqlsp.tbListSP(tbSP);
        //Do du lieu vao table kho
        ckho.loadTablelistKho(tableKho);
        // xu ly form QLSP
        XuLy(false);
        try {
            ShowData();
        } catch (SQLException ex) {
            Logger.getLogger(Banhang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// Trang thai ban

    private int trangthai(String maban) {
        maban = this.maban;
        boolean check = modelsp.kiemtraBan(maban);
        if (check) {
            return 1;
        }
        return 0;
    }
// Load icon Ban

    public void loadiconBan() {
        lb = new JLabel[13];

        for (int i = 1; i <= 12; i++) {
            int dem = i;

            boolean kiemtra = modelsp.kiemtraBan("b" + i);
            if (kiemtra) {
                String ban = String.valueOf("b" + dem);
                lb[i] = new JLabel();
                lb[i].setText("Bàn " + i);
                lb[i].setIcon(new ImageIcon(getClass().getResource("/Image/cup.png")));
                lb[i].setHorizontalAlignment(JLabel.CENTER);
                lb[dem].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                    }

                    @Override
                    public void mousePressed(MouseEvent me) {
//                        if (!kiemtra) {
//                            lb[dem].setIcon(new ImageIcon(getClass().getResource("/Image/cupblack.png")));
//                        }
                        vitri = dem;
                        lbthongtinban.setText("Thông tin Bàn số " + dem);
//                        JOptionPane.showMessageDialog(null, "Đã có bàn" + ban);
                        csanpham.loadTableBan(tableBan, ban);
                        maban = ban;
                        int thanhtien = 0;
                        for (int i = 0; i < tableBan.getRowCount(); i++) {
//                            JOptionPane.showMessageDialog(null,tableBan.getRowCount());
                            int data = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 3)));
                            int soluong = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 1)));
                            String tensp = String.valueOf(tableBan.getValueAt(i, 0));
                            ModelSanPham ds = modelsp.gettenSP(tensp);
                            String masp = ds.getMasp();
                            thanhtien += data;
                        }
                        price = thanhtien;
                        String tt = String.valueOf(thanhtien);
                        String tongtien = xulytien(tt);
                        txtthanhtien.setText(String.valueOf(tongtien) + " VNĐ");
                        txttienkhach.setText("");
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent me) {

                    }

                    @Override
                    public void mouseExited(MouseEvent me) {
                    }
                });

            } else {

                String ban = String.valueOf("b" + dem);
                lb[i] = new JLabel("Bàn " + i, new ImageIcon(getClass().getResource("/Image/cupblack.png")), JLabel.CENTER);
                lb[dem].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                    }

                    @Override
                    public void mousePressed(MouseEvent me) {
//                        lb[dem].setIcon(new ImageIcon(getClass().getResource("/Image/cup.png")));
                        vitri = dem;
                        lbthongtinban.setText("Thông tin Bàn số " + dem);
//                        JOptionPane.showMessageDialog(null, "Chưa có bàn" + ban);
                        model = new DefaultTableModel();
                        model.setDataVector(null, csanpham.vcotban());
                        tableBan.setModel(model);
                        maban = ban;
                        int thanhtien = 0;
                        for (int i = 0; i < tableBan.getRowCount(); i++) {
//                            JOptionPane.showMessageDialog(null,tableBan.getRowCount());
                            int data = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 3)));
                            int soluong = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 1)));
                            String tensp = String.valueOf(tableBan.getValueAt(i, 0));
                            ModelSanPham ds = modelsp.gettenSP(tensp);
                            String masp = ds.getMasp();
                            thanhtien += data;
                        }
                        price = thanhtien;
                        String tt = String.valueOf(thanhtien);
                        String tongtien = xulytien(tt);
                        txtthanhtien.setText(String.valueOf(tongtien) + " VNĐ");
                        txttienkhach.setText(tt);
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent me) {

                    }

                    @Override
                    public void mouseExited(MouseEvent me) {
                    }
                });
            }
            lb[i].setBorder(new LineBorder(Color.black));
//            lb[i].setPreferredSize(new Dimension(10,10));
            lb[i].setVerticalTextPosition(JLabel.BOTTOM);
            lb[i].setHorizontalTextPosition(JLabel.CENTER);

            panelban.add(lb[i]);

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panelmain = new javax.swing.JPanel();
        btnthucdon = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnthoat = new javax.swing.JButton();
        btndangxuat = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        tab = new javax.swing.JTabbedPane();
        tabB = new javax.swing.JPanel();
        panelban = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnchuyenban = new javax.swing.JButton();
        btnHoantac = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBan = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        txtsanpham = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableSP = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        btnNhap = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtthanhtien = new javax.swing.JTextField();
        txttienkhach = new javax.swing.JTextField();
        btnthanhtoan = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btneditsoluong = new javax.swing.JButton();
        lbthongtinban = new javax.swing.JLabel();
        tabK = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableKho = new javax.swing.JTable();
        txttenhangkho = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtdvtkho = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtgiakho = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txtsoluongkho = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        btnxoakho = new javax.swing.JButton();
        btnsuakho = new javax.swing.JButton();
        btnthemmoikho = new javax.swing.JButton();
        btntimkiemkho = new javax.swing.JButton();
        spngaynhap = new javax.swing.JSpinner();
        btnlammoikho = new javax.swing.JButton();
        jtxt_timkho = new javax.swing.JTextField();
        tabNS = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        rdo_Nam = new javax.swing.JRadioButton();
        rdo_Nu = new javax.swing.JRadioButton();
        txt_Pass = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_SDT = new javax.swing.JTextField();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lbl_Image = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_HoTen = new javax.swing.JTextField();
        btn_Insert = new javax.swing.JButton();
        btn_Clear = new javax.swing.JButton();
        btn_Update = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txt_User = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbo_Roles = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        txt_CMND = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txt_DiaChi = new javax.swing.JTextField();
        btn_Browser = new javax.swing.JButton();
        lbl_URL = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_NhanVien = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        tabSP = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        btnTim = new javax.swing.JButton();
        jp = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbSP = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtSPMa = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtSPTen = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtSPGia = new javax.swing.JTextField();
        txtSPGC = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtSPURL = new javax.swing.JTextField();
        txtSPDVT = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        btnBrowser = new javax.swing.JButton();
        btnNhapMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        lbImage = new javax.swing.JLabel();
        txtSPTim = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        lbtenuser = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnthucdon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/menu.png"))); // NOI18N
        btnthucdon.setText("Thực Đơn");
        btnthucdon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthucdonActionPerformed(evt);
            }
        });

        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/graph.png"))); // NOI18N
        btnThongKe.setText("Thống Kê");
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnthoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/cancel.png"))); // NOI18N
        btnthoat.setText("Thoát");
        btnthoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthoatActionPerformed(evt);
            }
        });

        btndangxuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logout.png"))); // NOI18N
        btndangxuat.setText("Đăng Xuất");
        btndangxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndangxuatActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        tab.setToolTipText("");
        tab.setName(""); // NOI18N
        tab.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabStateChanged(evt);
            }
        });
        tab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMouseClicked(evt);
            }
        });
        tab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tabComponentShown(evt);
            }
        });

        panelban.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(133, 158, 191), null));
        panelban.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(133, 158, 195), null));

        btnchuyenban.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/horizontal.png"))); // NOI18N
        btnchuyenban.setText("Chuyển Bàn");
        btnchuyenban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnchuyenbanActionPerformed(evt);
            }
        });

        btnHoantac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/undo.png"))); // NOI18N
        btnHoantac.setText("Hoàn Tác");
        btnHoantac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoantacActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/delete.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tableBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane1.setViewportView(tableBan);

        btnsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/research.png"))); // NOI18N
        btnsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchActionPerformed(evt);
            }
        });

        tableSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "MãSP", "Sản Phẩm", "Đơn vị tính", "Đơn Giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableSP);

        jLabel10.setText("Sản Phẩm:");

        btnNhap.setText("Nhập");
        btnNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtsanpham))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNhap)
                    .addComponent(btnsearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(133, 158, 191)));

        jLabel7.setText("Tổng Cộng :");

        jLabel8.setText("Tiền Khách Đưa :");

        txtthanhtien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtthanhtien.setForeground(new java.awt.Color(204, 0, 51));

        btnthanhtoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/notes.png"))); // NOI18N
        btnthanhtoan.setText("Thanh Toán");
        btnthanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthanhtoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnthanhtoan))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtthanhtien, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txttienkhach, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtthanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txttienkhach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnthanhtoan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setText("Version: Beta 4.1a");

        jLabel14.setText("Actor: Group 4");

        btneditsoluong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/edit.png"))); // NOI18N
        btneditsoluong.setText("Thêm số lượng");
        btneditsoluong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditsoluongActionPerformed(evt);
            }
        });

        lbthongtinban.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbthongtinban.setForeground(new java.awt.Color(255, 0, 51));
        lbthongtinban.setText("Thông tin Bàn");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(181, 181, 181))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnchuyenban)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHoantac)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditsoluong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 894, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbthongtinban, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(803, 803, 803))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnchuyenban)
                    .addComponent(btnHoantac)
                    .addComponent(btnDelete)
                    .addComponent(btneditsoluong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbthongtinban)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 39, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout tabBLayout = new javax.swing.GroupLayout(tabB);
        tabB.setLayout(tabBLayout);
        tabBLayout.setHorizontalGroup(
            tabBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelban, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabBLayout.setVerticalGroup(
            tabBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tab.addTab("Quản Lý Bàn", tabB);

        tableKho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên Hàng", "Số Lượng", "ĐVT", "Ngày Nhập", "Giá"
            }
        ));
        tableKho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableKhoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableKho);

        txttenhangkho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttenhangkhoActionPerformed(evt);
            }
        });

        jLabel23.setText("Đơn Vị Tính :");

        txtdvtkho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdvtkhoActionPerformed(evt);
            }
        });

        jLabel33.setText("Ngày Nhập :");

        jLabel34.setText("Giá :");

        jLabel36.setText("Tên Hàng :");

        jLabel37.setText("Số Lượng :");

        jLabel38.setText("Version: Beta 4.1a");

        jLabel39.setText("Actor: Group 4");

        btnxoakho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/delete.png"))); // NOI18N
        btnxoakho.setText("Xóa");
        btnxoakho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoakhoActionPerformed(evt);
            }
        });

        btnsuakho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/restore-icon-18.png"))); // NOI18N
        btnsuakho.setText("Sửa");
        btnsuakho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuakhoActionPerformed(evt);
            }
        });

        btnthemmoikho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add.png"))); // NOI18N
        btnthemmoikho.setText("Thêm");
        btnthemmoikho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemmoikhoActionPerformed(evt);
            }
        });

        btntimkiemkho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/research.png"))); // NOI18N
        btntimkiemkho.setText("Tìm");
        btntimkiemkho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimkiemkhoActionPerformed(evt);
            }
        });

        spngaynhap.setModel(new javax.swing.SpinnerDateModel());
        spngaynhap.setEditor(new javax.swing.JSpinner.DateEditor(spngaynhap, "dd/MM/yyyy"));

        btnlammoikho.setText("Làm mới");
        btnlammoikho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlammoikhoActionPerformed(evt);
            }
        });

        jtxt_timkho.setText(" ");

        javax.swing.GroupLayout tabKLayout = new javax.swing.GroupLayout(tabK);
        tabK.setLayout(tabKLayout);
        tabKLayout.setHorizontalGroup(
            tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabKLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabKLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabKLayout.createSequentialGroup()
                        .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel36))
                        .addGap(32, 32, 32)
                        .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabKLayout.createSequentialGroup()
                                .addComponent(txttenhangkho, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(172, 172, 172)
                                .addComponent(jLabel37)
                                .addGap(18, 18, 18)
                                .addComponent(txtsoluongkho, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtgiakho, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(85, 85, 85)
                        .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtdvtkho)
                            .addComponent(spngaynhap, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(52, 52, 52))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabKLayout.createSequentialGroup()
                .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabKLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnthemmoikho)
                        .addGap(18, 18, 18)
                        .addComponent(btnsuakho)
                        .addGap(18, 18, 18)
                        .addComponent(btnxoakho, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnlammoikho))
                    .addGroup(tabKLayout.createSequentialGroup()
                        .addGap(463, 463, 463)
                        .addComponent(jtxt_timkho, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btntimkiemkho, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(474, 474, 474))
        );
        tabKLayout.setVerticalGroup(
            tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabKLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabKLayout.createSequentialGroup()
                        .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(txttenhangkho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txtgiakho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnsuakho, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnthemmoikho, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnxoakho, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnlammoikho, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 72, Short.MAX_VALUE))
                    .addGroup(tabKLayout.createSequentialGroup()
                        .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtdvtkho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37)
                            .addComponent(txtsoluongkho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btntimkiemkho, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtxt_timkho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel33)
                                .addComponent(spngaynhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        tab.addTab("Quản Lý Kho Hàng", tabK);

        tabNS.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tabNSComponentShown(evt);
            }
        });
        tabNS.setLayout(null);

        jLabel24.setText("Version: Beta 2.1a");
        tabNS.add(jLabel24);
        jLabel24.setBounds(1140, 620, 89, 26);

        jLabel25.setText("Actor: Group 4");
        tabNS.add(jLabel25);
        jLabel25.setBounds(1140, 650, 89, 26);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 1, 36))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setText("Giới Tính:");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, 20));

        buttonGroup1.add(rdo_Nam);
        rdo_Nam.setText("Nam");
        rdo_Nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_NamActionPerformed(evt);
            }
        });
        jPanel3.add(rdo_Nam, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, -1, -1));

        buttonGroup1.add(rdo_Nu);
        rdo_Nu.setText("Nu");
        rdo_Nu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_NuActionPerformed(evt);
            }
        });
        jPanel3.add(rdo_Nu, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, -1, -1));
        jPanel3.add(txt_Pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 168, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Họ Tên :");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, 30));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Password :");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, 20));

        txt_SDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SDTActionPerformed(evt);
            }
        });
        jPanel3.add(txt_SDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 168, -1));

        jDesktopPane1.setLayer(lbl_Image, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Image, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Image, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 170, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("Điện Thoại :");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, 30));
        jPanel3.add(txt_HoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 168, -1));

        btn_Insert.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_Insert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save-icon.png"))); // NOI18N
        btn_Insert.setText("Thêm ");
        btn_Insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_InsertActionPerformed(evt);
            }
        });
        jPanel3.add(btn_Insert, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 280, 134, 44));

        btn_Clear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_Clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/erase-128.png"))); // NOI18N
        btn_Clear.setText("Làm mới");
        btn_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearActionPerformed(evt);
            }
        });
        jPanel3.add(btn_Clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 134, 44));

        btn_Update.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/update icon.png"))); // NOI18N
        btn_Update.setText("Sửa");
        btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UpdateActionPerformed(evt);
            }
        });
        jPanel3.add(btn_Update, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 280, 134, 44));

        btn_Delete.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/delete_16x16.gif"))); // NOI18N
        btn_Delete.setText("Xóa");
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });
        jPanel3.add(btn_Delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 330, 134, 44));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 0, 0));
        jLabel19.setText("UserName :");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 20));
        jPanel3.add(txt_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 168, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Roles :");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, 20));

        cbo_Roles.setText("Admin");
        jPanel3.add(cbo_Roles, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, -1, -1));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 0, 0));
        jLabel20.setText("CMND :");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, 20));
        jPanel3.add(txt_CMND, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 168, -1));

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 0));
        jLabel26.setText("Địa Chỉ :");
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, 20));
        jPanel3.add(txt_DiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 168, -1));

        btn_Browser.setText("Browser");
        btn_Browser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BrowserActionPerformed(evt);
            }
        });
        jPanel3.add(btn_Browser, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 140, -1, 40));
        jPanel3.add(lbl_URL, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 210, -1, -1));

        tabNS.add(jPanel3);
        jPanel3.setBounds(10, 90, 640, 400);

        tbl_NhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_NhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_NhanVienMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_NhanVien);

        tabNS.add(jScrollPane6);
        jScrollPane6.setBounds(660, 130, 560, 360);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 1, 14))); // NOI18N

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel27.setText("Username :");

        txt_search.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                txt_searchComponentRemoved(evt);
            }
        });
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(0, 24, Short.MAX_VALUE))
        );

        tabNS.add(jPanel9);
        jPanel9.setBounds(310, 10, 460, 80);

        tab.addTab("Quản Lý Nhân Sự", tabNS);

        tabSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabSPMouseClicked(evt);
            }
        });
        tabSP.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tabSPComponentShown(evt);
            }
        });

        jLabel46.setText("Version: Beta 4.1a");

        jLabel47.setText("Actor: Group 4");

        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/research.png"))); // NOI18N
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        jp.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Giá", "ĐVT", "URL", "Ghi Chú"
            }
        ));
        tbSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSPMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbSP);

        javax.swing.GroupLayout jpLayout = new javax.swing.GroupLayout(jp);
        jp.setLayout(jpLayout);
        jpLayout.setHorizontalGroup(
            jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpLayout.setVerticalGroup(
            jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtSPMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSPMaActionPerformed(evt);
            }
        });

        jLabel40.setText("Mã Sản Phẩm :");

        txtSPTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSPTenActionPerformed(evt);
            }
        });

        jLabel41.setText("Tên Sản Phẩm :");

        jLabel42.setText("Giá :");

        jLabel44.setText("Ghi chú :");

        jLabel45.setText("URL :");

        jLabel43.setText("Đơn Vị Tính :");

        btnBrowser.setText("Browser...");
        btnBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowserActionPerformed(evt);
            }
        });

        btnNhapMoi.setText("Nhập Mới");
        btnNhapMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapMoiActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/restore-icon-18.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        lbImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel45)
                            .addComponent(jLabel43)
                            .addComponent(jLabel42)
                            .addComponent(jLabel41))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSPDVT, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSPGia, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSPTen)
                            .addComponent(txtSPGC)
                            .addComponent(txtSPURL, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(18, 18, 18)
                        .addComponent(txtSPMa, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lbImage, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnNhapMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnThem)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(18, 18, 18)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSPMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(txtSPTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(txtSPGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSPDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(txtSPURL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(txtSPGC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lbImage, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNhapMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSua)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );

        txtSPTim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSPTimKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout tabSPLayout = new javax.swing.GroupLayout(tabSP);
        tabSP.setLayout(tabSPLayout);
        tabSPLayout.setHorizontalGroup(
            tabSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSPLayout.createSequentialGroup()
                .addGroup(tabSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSPLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabSPLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(txtSPTim, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabSPLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(tabSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(148, 148, 148))
        );
        tabSPLayout.setVerticalGroup(
            tabSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSPLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(tabSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSPTim, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tab.addTab("Quản Lý Sản Phẩm", tabSP);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setText("Nhân Viên :");

        lbtenuser.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbtenuser.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout panelmainLayout = new javax.swing.GroupLayout(panelmain);
        panelmain.setLayout(panelmainLayout);
        panelmainLayout.setHorizontalGroup(
            panelmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelmainLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnthucdon)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThongKe)
                .addGap(29, 29, 29)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btndangxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnthoat, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbtenuser, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
            .addComponent(tab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1230, Short.MAX_VALUE)
        );
        panelmainLayout.setVerticalGroup(
            panelmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelmainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelmainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnthoat)
                        .addComponent(btndangxuat)
                        .addComponent(jLabel12)
                        .addComponent(lbtenuser, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnThongKe, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnthucdon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tab))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelmain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelmain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnthoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthoatActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Bạn thực sự muốn thoát ?");
        if (i == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnthoatActionPerformed

    private void btnthucdonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthucdonActionPerformed
        // TODO add your handling code here:
        ThucDon td = new ThucDon();
        td.setVisible(true);

    }//GEN-LAST:event_btnthucdonActionPerformed

    private void btndangxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndangxuatActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Bạn muốn Đăng Xuất ?");
        if (i == JOptionPane.YES_OPTION) {
            BH.dispose();
            FormDN dn = new FormDN();
            dn.setVisible(true);
        }

    }//GEN-LAST:event_btndangxuatActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        // TODO add your handling code here:
        thongke tk = new thongke();
        tk.setVisible(true);
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void tabComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabComponentShown

    }//GEN-LAST:event_tabComponentShown

    private void tabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMouseClicked

    }//GEN-LAST:event_tabMouseClicked

    private void tabStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabStateChanged

    }//GEN-LAST:event_tabStateChanged

    private void tabNSComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabNSComponentShown

    }//GEN-LAST:event_tabNSComponentShown

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        // TODO add your handling code here:
        String query = txt_search.getText().toLowerCase();
        Search_NV(query);
        //        try {
        //            con = (new getData()).getConnect();
        //
        //            String sql = "select * from NhanVien where UserName=? ";
        //
        //            pst = con.prepareStatement(sql);
        //            pst.setString(1, txt_search.getText());
        //            rs = pst.executeQuery();
        //
        //            String add1 = rs.getString("Username");
        //            txt_User.setText(add1);
        //
        //            String add2 = rs.getString("Password");
        //            txt_Pass.setText(add2);
        //
        //            String add3 = rs.getString("Roles");
        //            cbo_Roles.setText(add3);
        //
        //            String add4 = rs.getString("HoTen");
        //            txt_HoTen.setText(add4);
        //
        //            String add5 = rs.getString("SDT");
        //            txt_SDT.setText(add5);
        //
        //            String add6 = rs.getString("CMND");
        //            txt_CMND.setText(add6);
        //
        //            String add7 = rs.getString("DiaChi");
        //            txt_CMND.setText(add7);
        //
        //            String add8 = rs.getString("GioiTinh");
        //            if (add8.equalsIgnoreCase("Nam")) {
        //                rdo_Nam.setSelected(true);
        //            } else {
        //                rdo_Nu.setSelected(false);
        //            }
        //        } catch (Exception e) {
        //            JOptionPane.showMessageDialog(null, e);
        //        } finally {
        //
        //            try {
        //
        //                rs.close();
        //                pst.close();
        //
        //            } catch (Exception e) {
        //
        //            }
        //        }
    }//GEN-LAST:event_txt_searchKeyReleased

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void txt_searchComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_txt_searchComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchComponentRemoved

    private void tbl_NhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NhanVienMouseClicked
        if (evt.getSource() == tbl_NhanVien) {
            int SelectedRow = tbl_NhanVien.getSelectedRow();
            //
            if (SelectedRow != -1) {
                try {
                    txt_User.setText(String.valueOf(tbl_NhanVien.getValueAt(SelectedRow, 0)));
                    txt_Pass.setText(String.valueOf(tbl_NhanVien.getValueAt(SelectedRow, 1)));
                    String roles = String.valueOf(tbl_NhanVien.getValueAt(SelectedRow, 2));
                    if (roles.equalsIgnoreCase("1")) {
                        cbo_Roles.setSelected(true);
                    } else {
                        cbo_Roles.setSelected(false);
                    }
                    txt_HoTen.setText(String.valueOf(tbl_NhanVien.getValueAt(SelectedRow, 3)));
                    txt_SDT.setText(String.valueOf(tbl_NhanVien.getValueAt(SelectedRow, 4)));
                    txt_CMND.setText(String.valueOf(tbl_NhanVien.getValueAt(SelectedRow, 5)));
                    txt_DiaChi.setText(String.valueOf(tbl_NhanVien.getValueAt(SelectedRow, 6)));

                    // Set Selected cho gioi tinh
                    String GT = String.valueOf(tbl_NhanVien.getValueAt(SelectedRow, 7));
                    if (GT.equalsIgnoreCase("Nam")) {
                        rdo_Nam.setSelected(true);
                    } else if (GT.equalsIgnoreCase("Nu")) {
                        rdo_Nu.setSelected(true);
                    }
                    String Imageurl = (String.valueOf(tbl_NhanVien.getValueAt(SelectedRow, 8)));
                    lbl_URL.setText(Imageurl);
                    lbl_URL.setVisible(false);
//                    lbl_Image.setIcon(new ImageIcon(getClass().getResource(Image)));
                  ImageIcon icon = new ImageIcon(new ImageIcon(Imageurl).getImage().getScaledInstance(lbl_Image.getWidth(), lbl_Image.getHeight(),Image.SCALE_SMOOTH));
                    lbl_Image.setIcon(icon);
                } catch (Exception e) {

                }
            } else {
                JOptionPane.showMessageDialog(null, "Chưa chọn Nhân Viên !");
            }
        }
    }//GEN-LAST:event_tbl_NhanVienMouseClicked

    private void btn_BrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BrowserActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();

        fileImage = f.getAbsolutePath();
        lbl_URL.setText(fileImage);
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(fileImage).getImage().getScaledInstance(lbl_Image.getWidth(), lbl_Image.getHeight(), Image.SCALE_DEFAULT));
        lbl_Image.setIcon(imageIcon);
        try {

            File image = new File(fileImage);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            for (int readNum; (readNum = fis.read(buf)) != -1;) {

                bos.write(buf, 0, readNum);
            }
            imageNV = bos.toByteArray();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_BrowserActionPerformed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        this.Delete_NV();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateActionPerformed
        // TODO add your handling code here:
        this.Update_NV();
    }//GEN-LAST:event_btn_UpdateActionPerformed

    private void btn_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearActionPerformed
        // TODO add your handling code here:
        this.Clear();
    }//GEN-LAST:event_btn_ClearActionPerformed

    private void btn_InsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_InsertActionPerformed
        this.Insert_NV();
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_InsertActionPerformed

    private void txt_SDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_SDTActionPerformed

    private void rdo_NuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_NuActionPerformed
        // TODO add your handling code here:
        GioiTinh = "Nu";
        rdo_Nu.setSelected(true);
        rdo_Nam.setSelected(false);
    }//GEN-LAST:event_rdo_NuActionPerformed

    private void rdo_NamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_NamActionPerformed
        // TODO add your handling code here:
        GioiTinh = "Nam";
        rdo_Nam.setSelected(true);
        rdo_Nu.setSelected(false);
    }//GEN-LAST:event_rdo_NamActionPerformed

    private void btnlammoikhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoikhoActionPerformed
        ckho = new ControllerKho();
        txttenhangkho.setText("");
        txtsoluongkho.setText("");
        txtgiakho.setText("");
        txtdvtkho.setText("");
        ckho.loadTablelistKho(tableKho);
    }//GEN-LAST:event_btnlammoikhoActionPerformed

    private void btntimkiemkhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemkhoActionPerformed

        ckho = new ControllerKho();
        String tenhang = jtxt_timkho.getText();
        boolean kiemtra = ckho.timkiem(tenhang, tableKho);
        if (kiemtra) {
            JOptionPane.showMessageDialog(null, "Tìm thấy!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy!");
        }
        //Tín check bỏ trống
        if (this.jtxt_timkho.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập gì");
        } else if (checkSymbolK.checkSb(this.jtxt_timkho.getText()) == true) {
            JOptionPane.showMessageDialog(null, "phát hiện có ký tự đặt biệt");
        }
        //hết
    }//GEN-LAST:event_btntimkiemkhoActionPerformed

    private void btnthemmoikhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemmoikhoActionPerformed
        int vitridong = tableKho.getSelectedRow();

        modelkho = new ModelKho();
        ckho = new ControllerKho();
        SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (vitridong != -1) {
                int id = Integer.parseInt(String.valueOf(tableKho.getValueAt(vitridong, 0)));
                boolean kiemtra = modelkho.kiemtrakho(id);
                if (kiemtra) {

                    JOptionPane.showMessageDialog(this, "Sản phẩm này đã tồn tại!");
                    ckho.loadTablelistKho(tableKho);

                }
            } else {
                int luachon = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (JOptionPane.YES_NO_OPTION == luachon) {

                    String tenhang = txttenhangkho.getText();
                    int soluong = Integer.parseInt(txtsoluongkho.getText());
                    String dvt = txtdvtkho.getText();
                    Date temp = (Date) (spngaynhap.getValue());
                    String ngaynhap = dformat.format(temp);
                    String gia = txtgiakho.getText();
                    boolean result = ckho.themkho(tenhang, soluong, dvt, gia, ngaynhap);
                    if (result) {
                        JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
                        ckho.loadTablelistKho(tableKho);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        //Tín check bỏ trống
        if (this.txttenhangkho.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập gì");
        } else if (checkSymbolK.checkSb(this.txttenhangkho.getText()) == true) {
            JOptionPane.showMessageDialog(null, "phát hiện có ký tự đặt biệt");
        }
        //hết
    }//GEN-LAST:event_btnthemmoikhoActionPerformed

    private void btnsuakhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuakhoActionPerformed
        int vitridong = tableKho.getSelectedRow();
        try {
            if (vitridong != -1) {
                SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/yyyy");
                int id = Integer.parseInt(String.valueOf(tableKho.getValueAt(vitridong, 0)));
                String tenhang = txttenhangkho.getText();
                int soluong = Integer.parseInt(txtsoluongkho.getText());
                String dvt = txtdvtkho.getText();
                Date temp = (Date) (spngaynhap.getValue());
                String ngaynhap = dformat.format(temp);
                String gia = txtgiakho.getText();
                boolean result = ckho.capnhatkho(id, tenhang, soluong, dvt, gia, ngaynhap);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    ckho.loadTablelistKho(tableKho);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn mặt hàng cần cập nhật!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Số lượng chỉ được nhập số !");
        }
        //Tín check bỏ trống
        if (this.txttenhangkho.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập gì");
        } else if (checkSymbolK.checkSb(this.txttenhangkho.getText()) == true) {
            JOptionPane.showMessageDialog(null, "phát hiện có ký tự đặt biệt");
        }
        //hết
    }//GEN-LAST:event_btnsuakhoActionPerformed

    private void btnxoakhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoakhoActionPerformed
        int vitridong = tableKho.getSelectedRow();
        int id = Integer.parseInt(String.valueOf(tableKho.getValueAt(vitridong, 0)));
        ckho = new ControllerKho();
        boolean result = ckho.xoa(id);
        if (result) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            ckho.loadTablelistKho(tableKho);
        }
    }//GEN-LAST:event_btnxoakhoActionPerformed

    private void txtdvtkhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdvtkhoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdvtkhoActionPerformed

    private void txttenhangkhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttenhangkhoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttenhangkhoActionPerformed

    private void tableKhoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableKhoMouseClicked
        int vitridong = tableKho.getSelectedRow();
        try {
            if (vitridong != -1) {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(tableKho.getValueAt(vitridong, 4)));
                txttenhangkho.setText(String.valueOf(tableKho.getValueAt(vitridong, 1)));
                txtsoluongkho.setText(String.valueOf(tableKho.getValueAt(vitridong, 2)));
                txtdvtkho.setText(String.valueOf(tableKho.getValueAt(vitridong, 3)));
                spngaynhap.setValue(date);
                txtgiakho.setText(String.valueOf(tableKho.getValueAt(vitridong, 5)));
            }
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_tableKhoMouseClicked

    private void btneditsoluongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditsoluongActionPerformed

        try {
            int soluongmoi = 0;
            String tensp = "", masp = "";
            int vitridong = tableBan.getSelectedRow();
            int soluongnhap = 0;
            int soluongtrongkho = 0;
            int slsua = 0;
            ModelKho mkho = new ModelKho();
            boolean dieukien = true;
            boolean result = false;
            if (vitridong != -1) {
                tensp = String.valueOf(tableBan.getValueAt(vitridong, 0));
                modelsp = new ModelSanPham();
                ModelSanPham ds = modelsp.gettenSP(tensp);
                masp = ds.getMasp();
                int soluongcu = Integer.parseInt(tableBan.getValueAt(vitridong, 1).toString());
                while (dieukien == true) {
                    soluongnhap = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Số lượng", "Nhập số lượng:", JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/Image/cup.png")), null, null));
                    if (soluongnhap > 0) {
                        dieukien = false;
                    } else if(soluongnhap<0) {
                        JOptionPane.showMessageDialog(null, "Số lượng không hợp lý, vui lòng nhập lại!");
                        dieukien = true;
                    }
                }
                soluongmoi = soluongnhap + soluongcu;
                switch (masp) {
                    case "cfsua":
                        int slcf = mkho.laysoluong("cf").getSoluong();
                        slsua = mkho.laysoluong("sua").getSoluong();

                        if (slcf > 0 && slsua > 0 && slcf >= soluongmoi && slsua >= soluongmoi) {

                            result = csanpham.capnhatsoluong(masp, soluongmoi);
                        } else if (slcf == 0 && slsua > 0 && slsua < soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Sản phẩm Cafe đã hết và Sữa còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (slsua == 0 && slcf > 0 && slcf < soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Cafe chỉ còn " + slcf + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (slcf > 0 && slcf < soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + slcf + " sản phẩm Cafe, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (slsua > 0 && slsua < soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + slsua + " sản phẩm Sữa, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (slsua == 0 && slcf > 0 && slcf >= soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Cafe chỉ còn " + slcf + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (slcf == 0 && slsua > 0 && slsua >= soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Sản phẩm Cafe đã hết và Sữa chỉ còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else {
                            JOptionPane.showMessageDialog(null, "Trong kho đã hết sản phẩm Sữa và Cafe, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        }

                        break;
                    case "lipsua":
                        int sllip = mkho.laysoluong("lip").getSoluong();
                        slsua = mkho.laysoluong("sua").getSoluong();
//                        soluongnhap = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Số lượng", "Nhập số lượng:", JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/Image/cup.png")),null, null));

                        if (sllip > 0 && slsua > 0 && sllip >= soluongnhap && slsua >= soluongmoi) {

                            result = csanpham.capnhatsoluong(masp, soluongmoi);
                        } else if (sllip == 0 && slsua > 0 && slsua < soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Sản phẩm Lipton đã hết và Sữa còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (slsua == 0 && sllip > 0 && sllip < soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Lipton chỉ còn " + sllip + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (sllip > 0 && sllip < soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + sllip + " sản phẩm Lipton, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (slsua > 0 && slsua < soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + slsua + " sản phẩm Sữa, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (slsua == 0 && sllip > 0 && sllip >= soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Lipton chỉ còn " + sllip + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (sllip == 0 && slsua > 0 && slsua >= soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Sản phẩm Lipton đã hết và Sữa chỉ còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else {
                            JOptionPane.showMessageDialog(null, "Trong kho đã hết sản phẩm Sữa và Lipton, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        }
                        break;
                    default:
                        soluongtrongkho = mkho.laysoluong(masp).getSoluong();
//                        soluongnhap = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Số lượng", "Nhập số lượng:", JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/Image/cup.png")), null, null));
                        if (soluongtrongkho > 0 && soluongtrongkho >= soluongmoi) {

                            result = csanpham.capnhatsoluong(masp, soluongmoi);
                        } else if (soluongtrongkho == 0) {
                            JOptionPane.showMessageDialog(null, "Sản phẩm này đã hết, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        } else if (soluongtrongkho > 0 && soluongtrongkho < soluongmoi) {
                            JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + soluongtrongkho + " sản phẩm, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                        }
                        break;
                }

                if (result) {
                    JOptionPane.showMessageDialog(null, "Cập nhật số lượng thành công!");
                    csanpham.loadTableBan(tableBan, maban);
                    int thanhtien = 0;
                    for (int i = 0; i < tableBan.getRowCount(); i++) {
                        //                            JOptionPane.showMessageDialog(null,tableBan.getRowCount());
                        int data = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 3)));
                        int soluong = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 1)));
                        thanhtien += data;
                    }
                    price = thanhtien;
                    String tt = String.valueOf(thanhtien);
                    String tongtien = xulytien(tt);
                    txtthanhtien.setText(String.valueOf(tongtien) + " VNĐ");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
            }
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_btneditsoluongActionPerformed

    private void btnthanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthanhtoanActionPerformed
        String khach = txttienkhach.getText();
        try {
            int tongtien = price;
            int tienkhach = Integer.parseInt(txttienkhach.getText());
            int tienthoi = tienkhach - tongtien;
            ThanhToan tt = new ThanhToan();
            tt.maban = maban;
            tt.lbmaban.setText("Bàn " + maban);
            tt.lbtongtien.setText(txtthanhtien.getText());
            tt.lbtienkhach.setText(xulytien(txttienkhach.getText()) + " VNĐ");
            tt.lbtienthoi.setText(xulytien(String.valueOf(tienthoi)) + " VNĐ");

            if (tienkhach > 0 && tienkhach >= tongtien) {
                //Lay gia tri MaSP va SoLuong

                //                ArrayList<ModelSanPham> lst = new ArrayList<>();
                for (int i = 0; i < tableBan.getRowCount(); i++) {
                    //                            JOptionPane.showMessageDialog(null,tableBan.getRowCount());
                    int data = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 3)));
                    int soluong = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 1)));
                    String thanhtien = xulytien(String.valueOf(tableBan.getValueAt(i, 3)));
                    String tensp = String.valueOf(tableBan.getValueAt(i, 0));
                    ModelSanPham ds = modelsp.gettenSP(tensp);
                    String masp = ds.getMasp();
                    ThongKe tk = new ThongKe();
                    tk.setMasp(masp);
                    tk.setTensp(tensp);
                    tk.setSoluong(soluong);
                    tk.setTongtien(thanhtien);
                    tk.tk.add(tk);
                    tt.setVisible(true);
                    tt.setResizable(false);
                    tt.setPreferredSize(new Dimension(480, 300));
                    tt.setLocation(455, 252);
                    //                    tt.setBounds(520, 300, 454, 252);
                }

            } else if (tienkhach > 0 && tienkhach < tongtien) {
                JOptionPane.showMessageDialog(null, "Số tiền không hợp lý!");
                txttienkhach.setText("");
                txttienkhach.requestFocus();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Khách chưa trả tiền!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            txttienkhach.requestFocus();
        }
    }//GEN-LAST:event_btnthanhtoanActionPerformed

    private void btnNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapActionPerformed
        modelsp = new ModelSanPham();
        int vitridong = tableSP.getSelectedRow();
        String masp = "";
        String tensp = "";
        int size = 0;
        ModelKho mkho = new ModelKho();
        if (vitridong != -1) {
            try {
                masp = String.valueOf(tableSP.getValueAt(vitridong, 0));
                tensp = String.valueOf(tableSP.getValueAt(vitridong, 1));
                //Lấy MaSP đưa ra biến toàn cục masanpham để sử dụng
                masanpham = masp;
                //-------------
                int soluongtrongkho = 0;
                int slsua = 0;
                int soluongnhap = 0;
                boolean dieukien = true;
                boolean kiemtra = modelsp.kiemtraTableGoiSP();
                int soluongcu = 0;
                int t = 0;
                if (!kiemtra) {
                    size = 1;
                } else {
                    List<ModelSanPham> ds = modelsp.laymagoi();
                    size = ds.get(ds.size() - 1).getMagoi() + 1;
                }
                //Kiem tra san pham da duoc nhap vao ban chua, neu nhap roi thi khong cho nhap trung
                for (int i = 0; i < tableBan.getRowCount(); i++) {
                    String tensptableban = (String.valueOf(tableBan.getValueAt(i, 0)));
                    String masptableBan = modelsp.gettenSP(tensptableban).getMasp();
                    if (masp.equals(masptableBan)) {
                        soluongcu = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 1)));
                        t = 1;
                        break;
                    }
                }
                if (t == 0) {
                    //Trường hợp sản phẩm chưa được gọi

                    while (dieukien == true) {
                        soluongnhap = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Số lượng", "Nhập số lượng:", JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/Image/cup.png")), null, null));
                        if (soluongnhap > 0) {
                            dieukien = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Số lượng không hợp lý, vui lòng nhập lại!");
                            dieukien = true;
                        }
                    }
                    switch (masp) {
                        case "cfsua":
                            int slcf = mkho.laysoluong("cf").getSoluong();
                            slsua = mkho.laysoluong("sua").getSoluong();

                            if (slcf > 0 && slsua > 0 && slcf >= soluongnhap && slsua >= soluongnhap) {
                                csanpham.themBan(maban, masp, soluongnhap, size);
                                csanpham.loadTableBan(tableBan, maban);
                                csanpham.loadTablelistSP(tableSP);
                            } else if (slcf == 0 && slsua > 0 && slsua < soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Sản phẩm Cafe đã hết và Sữa còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (slsua == 0 && slcf > 0 && slcf < soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Cafe chỉ còn " + slcf + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (slcf > 0 && slcf < soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + slcf + " sản phẩm Cafe, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (slsua > 0 && slsua < soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + slsua + " sản phẩm Sữa, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (slsua == 0 && slcf > 0 && slcf >= soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Cafe chỉ còn " + slcf + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (slcf == 0 && slsua > 0 && slsua >= soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Sản phẩm Cafe đã hết và Sữa chỉ còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else {
                                JOptionPane.showMessageDialog(null, "Trong kho đã hết sản phẩm Sữa và Cafe, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            }

                            break;
                        case "lipsua":
                            int sllip = mkho.laysoluong("lip").getSoluong();
                            slsua = mkho.laysoluong("sua").getSoluong();
//                        soluongnhap = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Số lượng", "Nhập số lượng:", JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/Image/cup.png")),null, null));

                            if (sllip > 0 && slsua > 0 && sllip >= soluongnhap && slsua >= soluongnhap) {
                                csanpham.themBan(maban, masp, soluongnhap, size);
                                csanpham.loadTableBan(tableBan, maban);
                                csanpham.loadTablelistSP(tableSP);
                            } else if (sllip == 0 && slsua > 0 && slsua < soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Sản phẩm Lipton đã hết và Sữa còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (slsua == 0 && sllip > 0 && sllip < soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Lipton chỉ còn " + sllip + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (sllip > 0 && sllip < soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + sllip + " sản phẩm Lipton, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (slsua > 0 && slsua < soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + slsua + " sản phẩm Sữa, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (slsua == 0 && sllip > 0 && sllip >= soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Lipton chỉ còn " + sllip + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (sllip == 0 && slsua > 0 && slsua >= soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Sản phẩm Lipton đã hết và Sữa chỉ còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else {
                                JOptionPane.showMessageDialog(null, "Trong kho đã hết sản phẩm Sữa và Lipton, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            }
                            break;
                        default:

                            soluongtrongkho = mkho.laysoluong(masp).getSoluong();
//                        soluongnhap = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Số lượng", "Nhập số lượng:", JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/Image/cup.png")), null, null));
                            if (soluongtrongkho > 0 && soluongtrongkho >= soluongnhap) {
                                csanpham.themBan(maban, masp, soluongnhap, size);
                                csanpham.loadTableBan(tableBan, maban);
                                csanpham.loadTablelistSP(tableSP);
                            } else if (soluongtrongkho == 0) {
                                JOptionPane.showMessageDialog(null, "Sản phẩm " + tensp + " đã hết, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            } else if (soluongtrongkho > 0 && soluongtrongkho < soluongnhap) {
                                JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + soluongtrongkho + " sản phẩm " + tensp + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                            }
                            break;
                    }
                } else if (t == 1) {
                    //Trường hợp sản phẩm đã được gọi
                    int suluachon = JOptionPane.showConfirmDialog(null, "Sản phẩm này đã được gọi, bạn có muốn thêm số lượng cho sản phẩm này?", "Thông báo", JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, new ImageIcon(getClass().getResource("/Image/question.png")));
                    if (suluachon == JOptionPane.YES_OPTION) {
                        boolean rs = false;
                        while (dieukien == true) {
                            soluongnhap = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Số lượng", "Nhập số lượng:", JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/Image/cup.png")), null, null));
                            if (soluongnhap > 0) {
                                dieukien = false;
                            } else {
                                JOptionPane.showMessageDialog(null, "Số lượng không hợp lý, vui lòng nhập lại!");
                                dieukien = true;
                            }
                            int soluongmoi = soluongnhap + soluongcu;
                            switch (masp) {
                                case "cfsua":
                                    int slcf = mkho.laysoluong("cf").getSoluong();
                                    slsua = mkho.laysoluong("sua").getSoluong();

                                    if (slcf > 0 && slsua > 0 && slcf >= soluongmoi && slsua >= soluongmoi) {

                                        rs = csanpham.capnhatsoluong(masp, soluongmoi);
                                    } else if (slcf == 0 && slsua > 0 && slsua < soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Sản phẩm Cafe đã hết và Sữa còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (slsua == 0 && slcf > 0 && slcf < soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Cafe chỉ còn " + slcf + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (slcf > 0 && slcf < soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + slcf + " sản phẩm Cafe, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (slsua > 0 && slsua < soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + slsua + " sản phẩm Sữa, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (slsua == 0 && slcf > 0 && slcf >= soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Cafe chỉ còn " + slcf + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (slcf == 0 && slsua > 0 && slsua >= soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Sản phẩm Cafe đã hết và Sữa chỉ còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Trong kho đã hết sản phẩm Sữa và Cafe, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    }

                                    break;
                                case "lipsua":
                                    int sllip = mkho.laysoluong("lip").getSoluong();
                                    slsua = mkho.laysoluong("sua").getSoluong();
//                        soluongnhap = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Số lượng", "Nhập số lượng:", JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/Image/cup.png")),null, null));

                                    if (sllip > 0 && slsua > 0 && sllip >= soluongnhap && slsua >= soluongmoi) {

                                        rs = csanpham.capnhatsoluong(masp, soluongmoi);
                                    } else if (sllip == 0 && slsua > 0 && slsua < soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Sản phẩm Lipton đã hết và Sữa còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (slsua == 0 && sllip > 0 && sllip < soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Lipton chỉ còn " + sllip + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (sllip > 0 && sllip < soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + sllip + " sản phẩm Lipton, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (slsua > 0 && slsua < soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + slsua + " sản phẩm Sữa, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (slsua == 0 && sllip > 0 && sllip >= soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Sản phẩm Sữa đã hết và Lipton chỉ còn " + sllip + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (sllip == 0 && slsua > 0 && slsua >= soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Sản phẩm Lipton đã hết và Sữa chỉ còn " + slsua + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Trong kho đã hết sản phẩm Sữa và Lipton, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    }
                                    break;
                                default:
                                    soluongtrongkho = mkho.laysoluong(masp).getSoluong();
//                        soluongnhap = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Số lượng", "Nhập số lượng:", JOptionPane.PLAIN_MESSAGE, new ImageIcon(getClass().getResource("/Image/cup.png")), null, null));
                                    if (soluongtrongkho > 0 && soluongtrongkho >= soluongmoi) {

                                        rs = csanpham.capnhatsoluong(masp, soluongmoi);
                                    } else if (soluongtrongkho == 0) {
                                        JOptionPane.showMessageDialog(null, "Sản phẩm " + tensp + " đã hết, vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    } else if (soluongtrongkho > 0 && soluongtrongkho < soluongmoi) {
                                        JOptionPane.showMessageDialog(null, "Trong kho chỉ còn " + soluongtrongkho + " sản phẩm " + tensp + ", vui lòng nhập kho!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
                                    }
                                    break;
                            }

                        }

                        if (rs) {
                            JOptionPane.showMessageDialog(null, "Thêm thành công!");
                            csanpham.loadTableBan(tableBan, maban);
                        }
                    }
                }
//            masanpham = masp;
//            csanpham.nhapsp(csanpham, tableSP, tableBan, txtthanhtien, maban, masp, size, vitridong, soluongtrongkho, soluong2sp);
                // Refresh Panel
                BH.panelban.removeAll();
                BH.loadiconBan();
                BH.panelban.repaint();
                BH.panelban.revalidate();
                //-------------
                int thanhtien = 0;
                for (int i = 0; i < tableBan.getRowCount(); i++) {
                    //                            JOptionPane.showMessageDialog(null,tableBan.getRowCount());
                    int data = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 3)));
                    thanhtien += data;
                }
                price = thanhtien;
                String tt = String.valueOf(thanhtien);
                String tongtien = xulytien(tt);
                txtthanhtien.setText(String.valueOf(tongtien) + " VNĐ");
            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm!");
        }
    }//GEN-LAST:event_btnNhapActionPerformed

    private void tableSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSPMouseClicked

    }//GEN-LAST:event_tableSPMouseClicked

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        String sanpham = txtsanpham.getText();

        modelsp = new ModelSanPham();

        csanpham = new ControllerSanPham();
        boolean result = modelsp.kiemtraSP(sanpham);

        if (result) {
            JOptionPane.showMessageDialog(null, "Tìm Thấy!");
            model = new DefaultTableModel();
            csanpham.loadTableSP(tableSP, sanpham);
        } else {
            JOptionPane.showMessageDialog(null, "Không Tìm Thấy!");
            txtsanpham.requestFocus();
        }
    }//GEN-LAST:event_btnsearchActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int vitridong = tableBan.getSelectedRow();
        String tensp = "", masp = "";
        if (vitridong != -1) {
            int input = JOptionPane.showConfirmDialog(null, "Bạn thực sự muốn xóa?");
            if (input == JOptionPane.YES_OPTION) {
                tensp = String.valueOf(tableBan.getValueAt(vitridong, 0));
                modelsp = new ModelSanPham();
                ModelSanPham ds = modelsp.gettenSP(tensp);
                masp = ds.getMasp();
                boolean result = csanpham.xoa1sp(maban, masp);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    // Refresh Panel
                    BH.panelban.removeAll();
                    BH.loadiconBan();
                    BH.panelban.repaint();
                    BH.panelban.revalidate();
                    //-------------
                    boolean kiemtra = modelsp.kiemtraBan(maban);
//                    if (!kiemtra) {
//                        lb[vitri].setIcon(new ImageIcon(getClass().getResource("/Image/cupblack.png")));
//                        csanpham.loadTableBan(tableBan, maban);
//                    }
                    //                    JOptionPane.showMessageDialog(null, vitri);
                    csanpham.loadTableBan(tableBan, maban);
                    int thanhtien = 0;
                    for (int i = 0; i < tableBan.getRowCount(); i++) {
                        //                            JOptionPane.showMessageDialog(null,tableBan.getRowCount());
                        int data = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 3)));
                        int soluong = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 1)));
                        thanhtien += data;
                    }
                    price = thanhtien;
                    String tt = String.valueOf(thanhtien);
                    String tongtien = xulytien(tt);
                    txtthanhtien.setText(String.valueOf(tongtien) + " VNĐ");

                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnHoantacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoantacActionPerformed
        //        int vitridong = tableBan.getSelectedRow();
        String tensp = "", masp = "";

        int input = JOptionPane.showConfirmDialog(null, "Bạn thực sự hoàn tác?", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, new ImageIcon(getClass().getResource("/Image/question.png")));
        if (input == JOptionPane.YES_OPTION) {
            masp = masanpham;
//            modelsp = new ModelSanPham();
//            ModelSanPham ds = modelsp.gettenSP(tensp);
//            masp = ds.getMasp();
            boolean result = csanpham.xoa1sp(maban, masanpham);
//                            JOptionPane.showMessageDialog(null, masanpham+maban);
            if (result) {
                JOptionPane.showMessageDialog(null, "Hoàn tác thành công!");
                boolean kiemtra = modelsp.kiemtraBan(maban);
                // Refresh Panel
                BH.panelban.removeAll();
                BH.loadiconBan();
                BH.panelban.repaint();
                BH.panelban.revalidate();
                //-------------

                csanpham.loadTableBan(tableBan, maban);
                int thanhtien = 0;
                for (int i = 0; i < tableBan.getRowCount(); i++) {
                    //                            JOptionPane.showMessageDialog(null,tableBan.getRowCount());
                    int data = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 3)));
                    int soluong = Integer.parseInt(String.valueOf(tableBan.getValueAt(i, 1)));
                    tensp = String.valueOf(tableBan.getValueAt(i, 0));
                    thanhtien += data;
                }
                price = thanhtien;
                String tt = String.valueOf(thanhtien);
                String tongtien = xulytien(tt);
                txtthanhtien.setText(String.valueOf(tongtien) + " VNĐ");
            }

        }
    }//GEN-LAST:event_btnHoantacActionPerformed

    private void btnchuyenbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchuyenbanActionPerformed

        String tensp = "", masp = "", mabancu = "", mabanmoi = "";
        try {
            int vitridong = tableBan.getSelectedRow();
            if (vitridong != -1) {
                int soluong = Integer.parseInt(tableBan.getValueAt(vitridong, 1).toString());
                tensp = String.valueOf(tableBan.getValueAt(vitridong, 0));
                modelsp = new ModelSanPham();
                ModelSanPham ds = modelsp.gettenSP(tensp);
                masp = ds.getMasp();
                mabancu = maban;
                csanpham.loadTableBan(tableBan, maban);
                ChuyenBan cb = new ChuyenBan();
                cb.lbmabancu.setText(mabancu);
                cb.masp = masp;
                cb.soluong = soluong;
                cb.setVisible(true);
                cb.setResizable(false);
                cb.setBounds(550, 250, 265, 224);

            } else {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE, new ImageIcon(getClass().getResource("/Image/warning.png")));
            }
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_btnchuyenbanActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        String sanpham = txtSPTim.getText();
        modelsp = new ModelSanPham();

        csanpham = new ControllerSanPham();
        boolean result = modelsp.TimSP(sanpham);

        if (result) {
            JOptionPane.showMessageDialog(null, "Tìm Thấy!");
            model = new DefaultTableModel();
            csanpham.loadtbSP(tbSP, sanpham);
        } else {
            JOptionPane.showMessageDialog(null, "Không Tìm Thấy!");
            txtSPTim.requestFocus();
        }

    }//GEN-LAST:event_btnTimActionPerformed

    private void tbSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSPMouseClicked
        // TODO add your handling code here:
        if (evt.getSource() == tbSP) {
            int so = tbSP.getSelectedRow();
            txtSPMa.setText(String.valueOf(tbSP.getValueAt(so, 1)));
            txtSPTen.setText(String.valueOf(tbSP.getValueAt(so, 2)));
            txtSPGia.setText(String.valueOf(tbSP.getValueAt(so, 3)));
            txtSPDVT.setText(String.valueOf(tbSP.getValueAt(so, 4)));
            txtSPURL.setText(String.valueOf(tbSP.getValueAt(so, 5)));
            txtSPGC.setText(String.valueOf(tbSP.getValueAt(so, 6)));
            //            lbImage.setIcon(new ImageIcon(ul));
            String URL = String.valueOf(tbSP.getValueAt(so, 5));
            txtSPURL.setText(URL);
            lbImage.setSize(180, 180);
            try {
                BufferedImage image = ImageIO.read(new File(URL));
//                int x = lbImage.getSize().width;
//                int y = lbImage.getSize().height;
//                int ix = image.getWidth();
//                int iy = image.getHeight();
//                int dx = 0;
//                int dy = 0;
//                if (x / y > ix / iy) {
//                    dy = y;
//                    dx = dy * ix / iy;
//                } else {
//                    dx = x;
//                    dy = dx * iy / ix;
//                }
                ImageIcon icon = new ImageIcon(image.getScaledInstance(180, 180, Image.SCALE_SMOOTH));
                lbImage.setIcon(icon);

            } catch (Exception ex) {

            }
        }
    }//GEN-LAST:event_tbSPMouseClicked

    private void txtSPMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSPMaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSPMaActionPerformed

    private void txtSPTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSPTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSPTenActionPerformed

    private void btnBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowserActionPerformed
       JFileChooser chooser = new JFileChooser();
//        chooser.setFileFilter(new FileNameExtensionFilter("Image files (*.GIF,*.PNG,*.JPG, *.JPEG)", "GIF","PNG","JPG", "JPEG"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("GIF PNG JPG & JPEG Images", "gif", "png", "jpg", "jpeg");
        chooser.setFileFilter(filter);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            chooser.showOpenDialog(null);
            File F = chooser.getSelectedFile();
            String URL = F.getAbsolutePath();
            txtSPURL.setText(URL);
            lbImage.setSize(180, 180);
            try {
                BufferedImage image = ImageIO.read(new File(URL));
//                int x = lbImage.getSize().width;
//                int y = lbImage.getSize().height;
//                int ix = image.getWidth();
//                int iy = image.getHeight();
//                int dx = 0;
//                int dy = 0;
//                if (x / y > ix / iy) {
//                    dy = y;
//                    dx = dy * ix / iy;
//                } else {
//                    dx = x;
//                    dy = dx * iy / ix;
//                }
                ImageIcon icon = new ImageIcon(image.getScaledInstance(180, 180, Image.SCALE_SMOOTH));
                lbImage.setIcon(icon);

            } catch (Exception ex) {

            }
        }
    }//GEN-LAST:event_btnBrowserActionPerformed

    private void btnNhapMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapMoiActionPerformed
         this.txtSPMa.setText(null);
        this.txtSPTen.setText(null);
        this.txtSPGia.setText(null);
        this.txtSPDVT.setText(null);
        this.txtSPGC.setText(null);
        this.txtSPURL.setText(null);
        this.lbImage.setIcon(new ImageIcon(""));
        txtSPMa.requestFocus();
    }//GEN-LAST:event_btnNhapMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:

        Connection con;
        Statement st = null;
        ResultSet rs;
        DefaultTableModel tbModel = new DefaultTableModel();
        String sql;

        char[] ch = txtSPGia.getText().toCharArray();

        int choice = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Thêm Không?", "Thông Báo", JOptionPane.YES_NO_OPTION);
        
            if (this.txtSPMa.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Mã Sản Phẩm Không Được Để Trống", "Thông Báo", 1);
                txtSPMa.requestFocus();
            } else if (this.txtSPTen.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Tên Sản Phẩm Không Được Để Trống", "Thông Báo", 1);
                txtSPTen.requestFocus();
            } else if (this.txtSPGia.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Giá Sản Phẩm Không Được Để Trống", "Thông Báo", 1);
                txtSPGia.requestFocus();
            } else
                for (int i = 0; i < ch.length; i++) {
                if (Character.isAlphabetic(ch[i])) {
                JOptionPane.showConfirmDialog(null, "Vui Lòng Nhập Số Vào Giá !", "Thông Báo", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                        txtSPGia.requestFocus();
                        break;
            } else if (this.txtSPDVT.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Đơn Vị Tính Không Được Để Trống", "Thông Báo", 1);
                txtSPDVT.requestFocus();
                break;
            } else if (JOptionPane.YES_OPTION == choice) {
                try {

                    con = (new getData()).getConnect();
                    st = con.createStatement();
                    String MaSP, TenSP, GiaSP, DVT, URL, GhiChu;

                    MaSP = txtSPMa.getText();
                    TenSP = txtSPTen.getText();
                    GiaSP = txtSPGia.getText();
                    DVT = txtSPDVT.getText();
                    URL = txtSPURL.getText();
                    GhiChu = txtSPGC.getText();

                    sql = "Insert into SanPham Values ('" + MaSP + "','" + TenSP + "','" + GiaSP + "','" + DVT + "','" + URL + "','" + GhiChu + "')";
                    int result = st.executeUpdate(sql);
                    if (result > 0) {
                    new ControllerSanPham().tbListSP(tbSP);
                    JOptionPane.showMessageDialog(null, "Đã Thêm Sản Phẩm Thành Công", "Thông Báo", 1);
                    break;
                    } 
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Mã Sản Phẩm Đã Tồn Tại, Vui Lòng Nhập Lại", "Thông báo", 1);
                    txtSPMa.requestFocus();
                    break;
                }
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
         Connection con;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        DefaultTableModel tbModel = new DefaultTableModel();
//
        char[] ch = txtSPGia.getText().toCharArray();

        int choice = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Sửa Không?", "Thông Báo", JOptionPane.YES_NO_OPTION);
        if (tbSP.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Bạn Chưa Chọn Dòng Cần Sửa", "Thông Báo", 1);
        } else if (this.txtSPMa.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Mã Sản Phẩm Không Được Để Trống", "Thông Báo", 1);
            txtSPMa.requestFocus();
        } else if (this.txtSPTen.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Tên Sản Phẩm Không Được Để Trống", "Thông Báo", 1);
            txtSPTen.requestFocus();
        } else if (this.txtSPGia.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Giá Sản Phẩm Không Được Để Trống", "Thông Báo", 1);
            txtSPGia.requestFocus();
        } else {
            for (int i = 0; i < ch.length; i++) {
                if (Character.isAlphabetic(ch[i])) {
                    JOptionPane.showConfirmDialog(null, "Vui Lòng Nhập Số Vào Giá !", "Thông Báo", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    txtSPGia.requestFocus();
                    break;
                } else if (this.txtSPDVT.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Đơn Vị Tính Không Được Để Trống", "Thông Báo", 1);
                    txtSPDVT.requestFocus();
                    break;
                } //
                //        int choice = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Sửa Không?", "Thông Báo", JOptionPane.YES_NO_OPTION);
                //        if (tbSP.getSelectedRow() == -1) {
                //            JOptionPane.showMessageDialog(null, "Bạn Chưa Chọn Dòng Cần Sửa","Thông Báo",1);
                //        } else if (this.txtSPTen.getText().length() == 0) {
                //            JOptionPane.showMessageDialog(null, "Tên Sản Phẩm Không Được Để Trống", "Thông Báo", 1);
                //            txtSPTen.requestFocus();
                //        } else if (this.txtSPGia.getText().length() == 0) {
                //            JOptionPane.showMessageDialog(null, "Giá Sản Phẩm Không Được Để Trống", "Thông Báo", 1);
                //            txtSPGia.requestFocus();
                //        } else if (this.txtSPDVT.getText().length() == 0) {
                //            JOptionPane.showMessageDialog(null, "Đơn Vị Tính Không Được Để Trống", "Thông Báo", 1);
                //            txtSPDVT.requestFocus();
                //        } 
                else if (JOptionPane.YES_OPTION == choice) {
                    int row = tbSP.getSelectedRow();
//            try {
//                con = (new getData()).getConnect();
//                ps = con.prepareStatement("Update SanPham Set TenSP=?, GiaSP=?, DVT=?, URL=?, GhiChu=? Where MaSP=?");
//                ps.setString(6, txtSPMa.getText());
//                ps.setString(1, txtSPTen.getText());
//                ps.setString(2, txtSPGia.getText());
//                ps.setString(3, txtSPDVT.getText());
//                ps.setString(4, txtSPURL.getText());
//                ps.setString(5, txtSPGC.getText());
//                int result = ps.executeUpdate();
//                if (result > 0) {
//                    new ControllerSanPham().tbListSP(tbSP);
//                    JOptionPane.showMessageDialog(null, "Đã Sửa Sản Phẩm Thành Công", "Thông Báo", 1);
//
//                } else {
//                    JOptionPane.showMessageDialog(null, "Mã Sản Phẩm Không Tồn Tại, Vui Lòng Nhập Lại", "Thông báo", 1);
//                }
//            } catch (Exception ex) {
//                
//                JOptionPane.showMessageDialog(null, "Mã Sản Phẩm Không Tồn Tại, Vui Lòng Nhập Lại", "Thông Báo", 1);
//            }
                    try {
                        con = (new getData()).getConnect();
                        st = con.createStatement();
                        String MaSP, TenSP, GiaSP, DVT, URL, GhiChu;

                        MaSP = txtSPMa.getText();
                        TenSP = txtSPTen.getText();
                        GiaSP = txtSPGia.getText();
                        DVT = txtSPDVT.getText();
                        URL = txtSPURL.getText();
                        GhiChu = txtSPGC.getText();

                        sql = "Update SanPham Set TenSP = '" + TenSP + "',GiaSP = '" + GiaSP + "',DVT = '" + DVT + "',URL = '" + URL + "',GhiChu = '" + GhiChu + "' Where MaSP = '" + MaSP + "'";
                        int result = st.executeUpdate(sql);
                        if (result > 0) {
                            new ControllerSanPham().tbListSP(tbSP);
                            JOptionPane.showMessageDialog(null, "Đã Sửa Sản Phẩm Thành Công", "Thông Báo", 1);
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, "Mã Sản Phẩm Không Tồn Tại, Vui Lòng Nhập Lại", "Thông báo", 1);
                            break;
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        Connection con;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        DefaultTableModel tbModel = new DefaultTableModel();
        int p = JOptionPane.showConfirmDialog(null, "Bạn Có Muốn Xóa Không?", "Thông Báo", JOptionPane.YES_NO_OPTION);
        if (tbSP.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Bạn Chưa Chọn Dòng Cần Xóa","Thông Báo",1);
        } else if (p == 0) {

            try {
                con = (new getData()).getConnect();

                sql = "Delete from SanPham where MaSP=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, txtSPMa.getText());
                ps.execute();


                int[] seletedRows = tbSP.getSelectedRows();
                for (int i = 0; i <= seletedRows.length; i++) {
                    tbModel.removeRow(seletedRows[i]);
                }

            } catch (Exception e) {
                new ControllerSanPham().tbListSP(tbSP);
                                JOptionPane.showMessageDialog(null, "Đã Xóa");

            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tabSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabSPMouseClicked

    }//GEN-LAST:event_tabSPMouseClicked

    private void tabSPComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabSPComponentShown

    }//GEN-LAST:event_tabSPComponentShown

    private void txtSPTimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSPTimKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtSPTimKeyReleased
//Xu ly tien

    public String xulytien(String thanhtien) {
        String s = thanhtien;
        int m = s.length() - 3;

        if (m > 3 && m % 3 != 0) {
            m = s.length() % 3;
        }
        if (m > 3 && m % 3 == 0) {
            m = s.length() / 3;
        }
        int vitri = s.length() - m;
//        System.out.println(m);
//        System.out.println(vitri);
        int n = (s.length() - m) / 3;
        int temp = 0;
        char[] ch = s.toCharArray();
        String s1 = "";
        for (int i = 0; i < ch.length; i++) {

            if ((vitri / 3) == 2) {
                s1 = s1 + ch[i];
                if (i == m - 1 || i == m + 2) {
                    s1 = s1 + ".";
                    temp++;
                }
            }
            if ((vitri / 3) == 1) {
                s1 = s1 + ch[i];
                if (i == m - 1) {
                    s1 = s1 + ".";
                    temp++;
                }
            }

        }
        return s1;
    }

    //
    //
    //
    // Code cua Lai
    // Tạo Table show tất cả thông tin Nhân Viên
    private void ShowData() throws SQLException {
        // Start Load Table Nhan vien 
        // Tao Ket Noi
        con = (new getData()).getConnect();
        //Tao Statement
        st = con.createStatement();
        String SQL = "Select* from NhanVien";
        // Tao resultset
        rs = st.executeQuery(SQL);
        // Tạo Cột    Connection conn = null;
        Vector vtCol = new Vector();
        ResultSetMetaData md = rs.getMetaData();
        int colNumbers = md.getColumnCount();
        for (int i = 1; i <= colNumbers; i++) {
            vtCol.addElement(md.getColumnName(i));
        }
        tblModel.setColumnIdentifiers(vtCol);
        // Du lieu
        while (rs.next()) {
            row = new Vector();
            for (int i = 1; i <= colNumbers; i++) {
                row.addElement(rs.getString(i));
            }
            tblModel.addRow(row);
        }
        // Đổ dữ liệu vào JTable
        tbl_NhanVien.setModel(tblModel);
        try {
            rs.close();
            st.close();
            pst.close();

        } catch (Exception ex) {

        }
    }

    // Reset text 
    private void Clear() {
        txt_User.setText("");
        txt_Pass.setText("");
        txt_HoTen.setText("");
        txt_SDT.setText("");
        txt_CMND.setText("");
        txt_DiaChi.setText("");
        rdo_Nam.setSelected(true);
        //
        cbo_Roles.setSelected(false);
        lbl_URL.setText("");
    }
// kiểm Tra UserName có trùng hay không

    boolean ktraTrungUsername(String User) {
        boolean ok = true;
        for (ModelUser i : ListNV) {
            if (i.getUsername().equalsIgnoreCase(User)) {
                ok = false;
            }
        }
        return ok;
    }

    //Add new
    private void Addnew() {
        // Tao Vector
        Vector addnew = new Vector();
        // Lay gia tri
        addnew.addElement(txt_User.getText());
        addnew.addElement(txt_Pass.getText());
        addnew.addElement(txt_SDT.getText());
        addnew.addElement(txt_HoTen.getText());
        addnew.addElement(txt_CMND.getText());
        addnew.addElement(txt_DiaChi.getText());
        addnew.addElement(lbl_Image.getText());
        // Get Value Gioi Tinh
        String GT = null;
        if (rdo_Nam.isSelected()) {
            GT = "Nam";
        } else if (rdo_Nu.isSelected()) {
            GT = "Nu";
        }
        addnew.addElement(GT);
        // Get Roles
        String Roles;
        if (cbo_Roles.isSelected()) {
            Roles = "1";
        } else {
            Roles = "0";
        }
        addnew.addElement(Roles);
        // Add 
        tblModel.addRow(addnew);
        tbl_NhanVien.setModel(tblModel);
    }

    private void LoadData() {
        try {
            con = (new getData()).getConnect();

            String sql = "select * from NhanVien";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            pst.execute();
            tbl_NhanVien.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // Loi trong ///
    public boolean LoiTrong(String s) {
        if (s.equals("")) {
            return false;
        }
        return true;
    }
    /// Loi So ///

    public boolean ChinhapSo(String str) {
        Pattern pt = Pattern.compile("[0-9]");
        Matcher mc = pt.matcher(str);
        if (!mc.find()) {
            return false;
        }
        return true;
    }
       /// Loi Ky tu so ///

    public boolean ChinhapChu(String str5) {
        Pattern pt = Pattern.compile("[a-zA-Z]");
        Matcher mc = pt.matcher(str5);
        if (!mc.find()) {
            return false;
        }
        return true;
    }
       /// Loi Ky tu so ///

    public boolean Loinhaphonhop(String str10) {
        Pattern pt = Pattern.compile("[a-z0-9_-]");
        Matcher mc = pt.matcher(str10);
        if (!mc.find()) {
            return false;
        }
        return true;
    }
    /// Loi Khoang Trang ///

    public boolean khoangTrang(String str) {
        Pattern pt = Pattern.compile("\\S");
        Matcher mc = pt.matcher(str);
        if (!mc.find()) {
            return false;
        }
        return true;
    }
    /// Loi max,min dien thoai ////

    public boolean MaxMinDT(String str3) {
        int i = str3.length();
        if (i < 10 || i > 11) {
            return false;
        }
        return true;
    }
    /// Loi max,min CMND ////

    public boolean MaxMinCMND(String str4) {
        int i = str4.length();
        if (i <9 || i > 12) {
            return false;
        }
        return true;
    }
 

    /// Loi max,min Usename ///
    public boolean MaxMinUser(String str1) {
        int i = str1.length();
        if (i > 5) {
            return false;
        }
        return true;
    }

    /// Loi max ten ///

    public boolean MaxTen(String str7) {
        int i = str7.length();
        if (i > 30) {
            return false;
        }
        return true;
    }

    // Insert
    private void Insert_NV() {
        String User = txt_User.getText();
        String Pass = txt_Pass.getText();
        if (cbo_Roles.isSelected()) {
            Roles = "true";
        } else {
            Roles = "false";
        }
        String HoTen = txt_HoTen.getText();
        String SDT = txt_SDT.getText();
        String CMND = txt_CMND.getText();
        String DiaChi = txt_DiaChi.getText();
        if (rdo_Nam.isSelected()) {
            GioiTinh = "Nam";
        } else if (rdo_Nu.isSelected()) {
            GioiTinh = "Nu";
        }
        String Image = lbl_Image.getText();

//       
//        while (true) {
        if (!LoiTrong(User)) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập Username.");
            txt_User.requestFocus();
        } else if (!MaxMinUser(User)) {
            JOptionPane.showMessageDialog(null, "Username không được nhập quá 5 kí tự.");
            txt_User.requestFocus();
        } else if (!khoangTrang(User)) {
            JOptionPane.showMessageDialog(null, "Username không được nhập dau cach.");
            txt_User.requestFocus();
        } else if (!LoiTrong(Pass)) {
            JOptionPane.showMessageDialog(null, " Password chưa nhập.");
            txt_Pass.requestFocus();
        } else if (!khoangTrang(Pass)) {
            JOptionPane.showMessageDialog(null, "Pass không được nhập dau cach.");
            txt_Pass.requestFocus();
        }else if (!LoiTrong(HoTen)) {
            JOptionPane.showMessageDialog(null, "HoTen chưa nhập.");
            txt_HoTen.requestFocus();
        } else if (!ChinhapChu(HoTen)) {
            JOptionPane.showMessageDialog(null, "Họ Tên không chi dc nhap chuoi.");
            txt_HoTen.requestFocus();
        }  else if (!Loinhaphonhop(HoTen)) {
            JOptionPane.showMessageDialog(null, "Họ Tên không được chứa số.");
            txt_HoTen.requestFocus();
        }else if (!LoiTrong(SDT)) {
            JOptionPane.showMessageDialog(null, "chưa Nhập SDT.");
            txt_SDT.requestFocus();
        } else if (!ChinhapSo(SDT)) {
            JOptionPane.showMessageDialog(null, "SDT phai nhap số.");
            txt_SDT.requestFocus();
        } else if (!MaxMinDT(SDT)) {
            JOptionPane.showMessageDialog(null, "SDT chỉ nhập 10 hoặc 11 số.");
            txt_SDT.requestFocus();
        } else if (!LoiTrong(CMND)) {
            JOptionPane.showMessageDialog(null, "Please enter CMND.");
            txt_CMND.requestFocus();
        } else if (!ChinhapSo(CMND)) {
            JOptionPane.showMessageDialog(null, "CMND phai nhap số.");
            txt_CMND.requestFocus();
        } else if (!ChinhapSo(CMND)) {
            JOptionPane.showMessageDialog(null, "CMND phai nhap số.");
            txt_CMND.requestFocus();
        } else if (!MaxMinCMND(CMND)) {
            JOptionPane.showMessageDialog(null, "CMND chỉ nhập 9 hoặc 12 số.");
            txt_CMND.requestFocus();
        } else if (!LoiTrong(DiaChi)) {
            JOptionPane.showMessageDialog(null, "Please enter Địa Chỉ.");
            txt_DiaChi.requestFocus();
        } else {
//

            int p = JOptionPane.showConfirmDialog(null, "Bạn muốn thêm mới ?", "Thêm mới", JOptionPane.YES_NO_OPTION);
            if (p == 0) {
                try {
                    con = (new getData()).getConnect();

                    String sql = "Insert into NhanVien(Username,Password,Roles,HoTen,SDT,CMND,DiaChi,GioiTinh,HinhAnh) Values(?,?,?,?,?,?,?,?,?)";

                    pst = con.prepareStatement(sql);
                    // Set Gia tri
                    pst.setString(1, User);
                    pst.setString(2, Pass);
                    pst.setString(3, Roles);
                    pst.setString(4, HoTen);
                    pst.setString(5, SDT);
                    pst.setString(6, CMND);
                    pst.setString(7, DiaChi);
                    pst.setString(8, GioiTinh);
                    pst.setString(9, Image);

                    pst.execute();

                    JOptionPane.showMessageDialog(null, "Thêm mới thành công !");
                    // Load Lai Table
                    this.Addnew();
                    // Reset Du lieu nhap
                    this.Clear();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Username đã tồn tại !");
                }

                try {
                    rs.close();
                    pst.close();

                } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, e);

                }
            }
        }

    }
    // Delete NV

    private void Delete_NV() {
        
        int p = JOptionPane.showConfirmDialog(null, "Bạn thực sự muốn xóa ?", "Xóa", JOptionPane.YES_NO_OPTION);
        if (p == 0) {

            try {
                con = (new getData()).getConnect();

                String sql = "Delete from NhanVien where Username=?";
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_User.getText());
                pst.execute();

                JOptionPane.showMessageDialog(null, "Đã Xóa !");

                int[] seletedRows = tbl_NhanVien.getSelectedRows();
                for (int i = 0; i <= seletedRows.length; i++) {
                    tblModel.removeRow(seletedRows[i]);
                }
                this.ShowData();
                this.Clear();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                rs.close();
                pst.close();

            } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, e);

            }

        }
    }

    // UpDate NV
    private void Update_NV() {
        String User = txt_User.getText();
        String Pass = txt_Pass.getText();
        String HoTen = txt_HoTen.getText();
        String SDT = txt_SDT.getText();
        String CMND = txt_CMND.getText();
        String DiaChi = txt_DiaChi.getText();
         if (!LoiTrong(User)) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập Username.");
            txt_User.requestFocus();
        } else if (!MaxMinUser(User)) {
            JOptionPane.showMessageDialog(null, "Username không được nhập quá 5 kí tự.");
            txt_User.requestFocus();
        } else if (!khoangTrang(User)) {
            JOptionPane.showMessageDialog(null, "Username không được nhập dau cach.");
            txt_User.requestFocus();
        } else if (!LoiTrong(Pass)) {
            JOptionPane.showMessageDialog(null, " Password chưa nhập.");
            txt_Pass.requestFocus();
        } else if (!khoangTrang(Pass)) {
            JOptionPane.showMessageDialog(null, "Pass không được nhập dau cach.");
            txt_Pass.requestFocus();
        }else if (!LoiTrong(HoTen)) {
            JOptionPane.showMessageDialog(null, "HoTen chưa nhập.");
            txt_HoTen.requestFocus();
        } else if (!ChinhapChu(HoTen)) {
            JOptionPane.showMessageDialog(null, "Họ Tên không chi dc nhap chuoi.");
            txt_HoTen.requestFocus();
        }  else if (!Loinhaphonhop(HoTen)) {
            JOptionPane.showMessageDialog(null, "Họ Tên không được chứa số.");
            txt_HoTen.requestFocus();
        }else if (!LoiTrong(SDT)) {
            JOptionPane.showMessageDialog(null, "chưa Nhập SDT.");
            txt_SDT.requestFocus();
        } else if (!ChinhapSo(SDT)) {
            JOptionPane.showMessageDialog(null, "SDT phai nhap số.");
            txt_SDT.requestFocus();
        } else if (!MaxMinDT(SDT)) {
            JOptionPane.showMessageDialog(null, "SDT chỉ nhập 10 hoặc 11 số.");
            txt_SDT.requestFocus();
        } else if (!LoiTrong(CMND)) {
            JOptionPane.showMessageDialog(null, "Hãy Nhập CMND.");
            txt_CMND.requestFocus();
        } else if (!ChinhapSo(CMND)) {
            JOptionPane.showMessageDialog(null, "CMND phải nhập số.");
            txt_CMND.requestFocus();
        } else if (!ChinhapSo(CMND)) {
            JOptionPane.showMessageDialog(null, "CMND phải nhập số.");
            txt_CMND.requestFocus();
        } else if (!MaxMinCMND(CMND)) {
            JOptionPane.showMessageDialog(null, "CMND chỉ nhập 9 hoặc 12 số.");
            txt_CMND.requestFocus();
        } else if (!LoiTrong(DiaChi)) {
            JOptionPane.showMessageDialog(null, "Hãy Nhập Địa Chỉ.");
            txt_DiaChi.requestFocus();
        } else {
        int p = JOptionPane.showConfirmDialog(null, "Bạn Muốn Cập Nhật ?", "Cập Nhật", JOptionPane.YES_NO_OPTION);
        if (p == 0) {

            try {
                con = (new getData()).getConnect();

                String value0 = txt_User.getText();
                String value1 = txt_Pass.getText();
                String value2 = null;
                if (cbo_Roles.isSelected()) {
                    value2 = "true";
                } else {
                    value2 = "false";
                }
                String value3 = txt_HoTen.getText();
                String value4 = txt_SDT.getText();
                String value5 = txt_CMND.getText();
                String value6 = txt_DiaChi.getText();
                String value7 = null;
                if (rdo_Nam.isSelected()) {
                    value7 = "Nam";
                } else if (rdo_Nu.isSelected()) {
                    value7 = "Nu";
                }
                String value8 = lbl_URL.getText();

                String sql = "Update NhanVien Set Password=?,Roles=?, HoTen = ?,SDT=?,CMND = ?,DiaChi = ?,GioiTinh = ?,HinhAnh=? Where Username = ? ";

                pst = con.prepareStatement(sql);
                pst.setString(1, value1);
                pst.setString(2, value2);
                pst.setString(3, value3);
                pst.setString(4, value4);
                pst.setString(5, value5);
                pst.setString(6, value6);
                pst.setString(7, value7);
                pst.setString(8, value8);
                pst.setString(9, value0);

                int result = pst.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                    tblModel.getDataVector().removeAllElements();
//                tblModel.fireTableDataChanged();
                    this.ShowData();
                } else {
                    JOptionPane.showMessageDialog(null, "Username không tồn tại");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

            try {
                rs.close();
                pst.close();

            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e);

            }
        }
    }
    }
// Search Nhan vien

    private void Search_NV() {
        try {
            con = (new getData()).getConnect();

            String sql = "select * from NhanVien Where Username like ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, "%" + txt_search.getText() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
//                user.setUsername(rs.getString("Username"));
//                user.setHoten(rs.getString("HoTen"));
//                user.setSdt(rs.getString("SDT"));
//                user.setCmnd(rs.getString("CMND"));
//                user.setDiachi(rs.getString("DiaChi"));
//                user.setGioitinh(rs.getString("GioiTinh"));
//                tbl_NhanVien.setModel(tblModel);
//                tbl_NhanVien.setModel(DbUtils.resultSetToTableModel(rs));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không Tìm Thấy!");
        }
    }

    private void Search_NV(String query) {

        TableRowSorter<DefaultTableModel> str = new TableRowSorter<DefaultTableModel>(tblModel);
        tbl_NhanVien.setRowSorter(str);
        str.setRowFilter(RowFilter.regexFilter(query));

    }
// end Lai
// khanh

    public void XuLy(boolean a) {
        this.txtSPURL.setEditable(a);
    }
//end khanh

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) throws Exception {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Banhang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Banhang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Banhang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Banhang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BH = new Banhang();
                BH.setVisible(true);
//                BH.setSize(new Dimension(1000,500));
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowser;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnHoantac;
    private javax.swing.JButton btnNhap;
    private javax.swing.JButton btnNhapMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    public javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btn_Browser;
    private javax.swing.JButton btn_Clear;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_Insert;
    private javax.swing.JButton btn_Update;
    private javax.swing.JButton btnchuyenban;
    private javax.swing.JButton btndangxuat;
    private javax.swing.JButton btneditsoluong;
    private javax.swing.JButton btnlammoikho;
    private javax.swing.JButton btnsearch;
    private javax.swing.JButton btnsuakho;
    private javax.swing.JButton btnthanhtoan;
    private javax.swing.JButton btnthemmoikho;
    private javax.swing.JButton btnthoat;
    private javax.swing.JButton btnthucdon;
    private javax.swing.JButton btntimkiemkho;
    private javax.swing.JButton btnxoakho;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cbo_Roles;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JPanel jp;
    private javax.swing.JTextField jtxt_timkho;
    private javax.swing.JLabel lbImage;
    private javax.swing.JLabel lbl_Image;
    private javax.swing.JLabel lbl_URL;
    public javax.swing.JLabel lbtenuser;
    private javax.swing.JLabel lbthongtinban;
    public javax.swing.JPanel panelban;
    private javax.swing.JPanel panelmain;
    private javax.swing.JRadioButton rdo_Nam;
    private javax.swing.JRadioButton rdo_Nu;
    private javax.swing.JSpinner spngaynhap;
    public javax.swing.JTabbedPane tab;
    public javax.swing.JPanel tabB;
    public javax.swing.JPanel tabK;
    public javax.swing.JPanel tabNS;
    public javax.swing.JPanel tabSP;
    public javax.swing.JTable tableBan;
    public javax.swing.JTable tableKho;
    private javax.swing.JTable tableSP;
    private javax.swing.JTable tbSP;
    private javax.swing.JTable tbl_NhanVien;
    private javax.swing.JTextField txtSPDVT;
    private javax.swing.JTextField txtSPGC;
    private javax.swing.JTextField txtSPGia;
    private javax.swing.JTextField txtSPMa;
    private javax.swing.JTextField txtSPTen;
    private javax.swing.JTextField txtSPTim;
    private javax.swing.JTextField txtSPURL;
    private javax.swing.JTextField txt_CMND;
    private javax.swing.JTextField txt_DiaChi;
    private javax.swing.JTextField txt_HoTen;
    private javax.swing.JTextField txt_Pass;
    private javax.swing.JTextField txt_SDT;
    private javax.swing.JTextField txt_User;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txtdvtkho;
    private javax.swing.JTextField txtgiakho;
    private javax.swing.JTextField txtsanpham;
    private javax.swing.JTextField txtsoluongkho;
    private javax.swing.JTextField txttenhangkho;
    public javax.swing.JTextField txtthanhtien;
    public javax.swing.JTextField txttienkhach;
    // End of variables declaration//GEN-END:variables
}
