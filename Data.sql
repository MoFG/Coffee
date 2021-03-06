USE [master]
GO
/****** Object:  Database [Coffee]    Script Date: 25/08/2017 2:54:57 SA ******/
CREATE DATABASE [Coffee]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Coffee', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\Coffee.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'Coffee_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\Coffee_log.ldf' , SIZE = 2048KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [Coffee] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Coffee].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Coffee] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Coffee] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Coffee] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Coffee] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Coffee] SET ARITHABORT OFF 
GO
ALTER DATABASE [Coffee] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [Coffee] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [Coffee] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Coffee] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Coffee] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Coffee] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Coffee] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Coffee] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Coffee] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Coffee] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Coffee] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Coffee] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Coffee] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Coffee] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Coffee] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Coffee] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Coffee] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Coffee] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Coffee] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Coffee] SET  MULTI_USER 
GO
ALTER DATABASE [Coffee] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Coffee] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Coffee] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Coffee] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [Coffee]
GO
/****** Object:  Table [dbo].[Ban]    Script Date: 25/08/2017 2:54:57 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Ban](
	[MaBan] [varchar](50) NOT NULL,
	[GhiChu] [varchar](50) NULL,
 CONSTRAINT [PK_Ban] PRIMARY KEY CLUSTERED 
(
	[MaBan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ChiTietHD]    Script Date: 25/08/2017 2:54:57 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ChiTietHD](
	[MaCT] [varchar](50) NOT NULL,
	[MaHD] [varchar](50) NULL,
	[MaBan] [varchar](50) NOT NULL,
	[GhiChu] [varchar](50) NULL,
 CONSTRAINT [PK_ChiTietHD_1] PRIMARY KEY CLUSTERED 
(
	[MaCT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GoiSP]    Script Date: 25/08/2017 2:54:57 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GoiSP](
	[MaGoi] [int] NOT NULL,
	[MaBan] [varchar](50) NOT NULL,
	[MaSP] [varchar](50) NOT NULL,
	[SoLuong] [int] NULL,
 CONSTRAINT [PK_GoiSP] PRIMARY KEY CLUSTERED 
(
	[MaGoi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 25/08/2017 2:54:57 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[HoaDon](
	[MaHD] [varchar](50) NOT NULL,
	[Username] [varchar](50) NOT NULL,
	[Ngay] [varchar](50) NULL,
 CONSTRAINT [PK_HoaDon] PRIMARY KEY CLUSTERED 
(
	[MaHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Kho]    Script Date: 25/08/2017 2:54:57 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Kho](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[TenHang] [varchar](50) NOT NULL,
	[SoLuong] [int] NULL,
	[DVT] [varchar](50) NULL,
	[Gia] [varchar](50) NULL,
	[Ngay] [varchar](50) NULL,
	[GhiChu] [varchar](50) NULL,
 CONSTRAINT [PK_Kho] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 25/08/2017 2:54:57 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[NhanVien](
	[Username] [varchar](50) NOT NULL,
	[Password] [varchar](50) NOT NULL,
	[Roles] [bit] NOT NULL,
	[HoTen] [varchar](50) NOT NULL,
	[SDT] [varchar](50) NOT NULL,
	[CMND] [varchar](50) NOT NULL,
	[DiaChi] [varchar](50) NULL,
	[GioiTinh] [varchar](50) NULL,
	[HinhAnh] [varchar](50) NULL,
 CONSTRAINT [PK_ChiTietNV] PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SanPham]    Script Date: 25/08/2017 2:54:57 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SanPham](
	[MaSP] [varchar](50) NOT NULL,
	[TenSP] [varchar](50) NOT NULL,
	[GiaSP] [varchar](50) NOT NULL,
	[DVT] [varchar](50) NULL,
	[URL] [ntext] NULL,
	[GhiChu] [varchar](50) NULL,
 CONSTRAINT [PK_SanPham] PRIMARY KEY CLUSTERED 
(
	[MaSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ThongKe]    Script Date: 25/08/2017 2:54:57 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ThongKe](
	[STT] [int] IDENTITY(1,1) NOT NULL,
	[MaSP] [varchar](50) NULL,
	[Ngay] [varchar](16) NULL,
	[SL] [int] NULL,
 CONSTRAINT [PK_ThongKe] PRIMARY KEY CLUSTERED 
(
	[STT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b1', N'lau1')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b10', N'tret')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b11', N'tret')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b12', N'tret')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b2', N'lau1')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b3', N'lau1')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b4', N'lau1')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b5', N'tret')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b6', N'tret')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b7', N'tret')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b8', N'tret')
INSERT [dbo].[Ban] ([MaBan], [GhiChu]) VALUES (N'b9', N'tret')
INSERT [dbo].[GoiSP] ([MaGoi], [MaBan], [MaSP], [SoLuong]) VALUES (5, N'b11', N'mon', 1)
INSERT [dbo].[HoaDon] ([MaHD], [Username], [Ngay]) VALUES (N'hd1', N'nv1', N'20/07/2017')
INSERT [dbo].[HoaDon] ([MaHD], [Username], [Ngay]) VALUES (N'hd2', N'nv1', N'20/07/2017')
INSERT [dbo].[HoaDon] ([MaHD], [Username], [Ngay]) VALUES (N'hd3', N'nv1', N'20/07/2017')
INSERT [dbo].[HoaDon] ([MaHD], [Username], [Ngay]) VALUES (N'hd4', N'nv2', N'20/07/2017')
INSERT [dbo].[HoaDon] ([MaHD], [Username], [Ngay]) VALUES (N'hd5', N'nv2', N'20/07/2017')
INSERT [dbo].[HoaDon] ([MaHD], [Username], [Ngay]) VALUES (N'hd6', N'nv2', N'20/07/2017')
SET IDENTITY_INSERT [dbo].[Kho] ON 

INSERT [dbo].[Kho] ([ID], [TenHang], [SoLuong], [DVT], [Gia], [Ngay], [GhiChu]) VALUES (1, N'lipton', 22, N'ly', N'25000', N'17/07/2017', NULL)
INSERT [dbo].[Kho] ([ID], [TenHang], [SoLuong], [DVT], [Gia], [Ngay], [GhiChu]) VALUES (2, N'sua', 10, N'ly', N'17000', N'17/07/2017', NULL)
INSERT [dbo].[Kho] ([ID], [TenHang], [SoLuong], [DVT], [Gia], [Ngay], [GhiChu]) VALUES (3, N'sd', 22, N'lon', N'5000', N'17/07/2017', NULL)
INSERT [dbo].[Kho] ([ID], [TenHang], [SoLuong], [DVT], [Gia], [Ngay], [GhiChu]) VALUES (4, N'cf', 10, N'ly', N'60000', N'19/07/2017', NULL)
INSERT [dbo].[Kho] ([ID], [TenHang], [SoLuong], [DVT], [Gia], [Ngay], [GhiChu]) VALUES (6, N'pep', 48, N'lon', N'7000', N'15/07/2017', NULL)
INSERT [dbo].[Kho] ([ID], [TenHang], [SoLuong], [DVT], [Gia], [Ngay], [GhiChu]) VALUES (9, N'coca', 24, N'lon', N'120000', N'21/08/2017', NULL)
INSERT [dbo].[Kho] ([ID], [TenHang], [SoLuong], [DVT], [Gia], [Ngay], [GhiChu]) VALUES (10, N'100', 28, N'lon', N'8000', N'15/07/2017', NULL)
SET IDENTITY_INSERT [dbo].[Kho] OFF
INSERT [dbo].[NhanVien] ([Username], [Password], [Roles], [HoTen], [SDT], [CMND], [DiaChi], [GioiTinh], [HinhAnh]) VALUES (N'admin', N'admin', 1, N'admin', N'0923676231', N'093278635', NULL, N'Nam', NULL)
INSERT [dbo].[NhanVien] ([Username], [Password], [Roles], [HoTen], [SDT], [CMND], [DiaChi], [GioiTinh], [HinhAnh]) VALUES (N'nv1', N'123', 0, N'Nguyen Van D', N'01778226733', N'032748316', N'12 Ho Ba Kien Q.10', N'Nam', N'D:\sp\cafeda.jpg')
INSERT [dbo].[NhanVien] ([Username], [Password], [Roles], [HoTen], [SDT], [CMND], [DiaChi], [GioiTinh], [HinhAnh]) VALUES (N'nv2', N'123', 0, N'Nguyen Van B', N'01778226733', N'032748316', N'12 Ho Ba Kien Q.10', N'Nam', N'D:\sp\cafeda.jpg')
INSERT [dbo].[NhanVien] ([Username], [Password], [Roles], [HoTen], [SDT], [CMND], [DiaChi], [GioiTinh], [HinhAnh]) VALUES (N'nv3', N'123', 1, N'Nguyen Van C', N'áđâsd', N'áđá', N'22 trương đ?nh', N'Nu', N'')
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'100', N'100 plus', N'13000', N'lon', N'D:\sp\100.jpg', N'2 lon tang vong tay')
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'cfden', N'cafe đen', N'17000', N'ly', N'D:\sp\cfden.jpg', N'3 tang 1')
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'cfsua', N'cafe sua', N'22000', N'ly', N'D:\sp\cfsua.jpg', NULL)
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'coca', N'cocacola', N'10000', N'chai', N'D:\sp\coca.jpg', N'3 chai tang so tay')
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'lip', N'lipton', N'17000', N'ly', N'D:\sp\lip.jpg', N'3 tang 1')
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'lipsua', N'lipton sua', N'22000', N'ly', N'D:\sp\lipsua.jpg', NULL)
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'mon', N'monster energy', N'25000', N'lon', N'C:\Users\Admin\Downloads\Compressed\CoffeeShopbeta4\src\Image\sp\mon.jpg', N'tang sticker')
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'mons', N'monster energy', N'25000', N'lon', N'D:\sp\mon.jpg', N'tang sticker')
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'pep', N'pepsi', N'10000', N'chai', N'D:\sp\pep.jpg', NULL)
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'poca', N'pocari sweet', N'12000', N'chai', N'D:\sp\poca.jpg', N'2 chai tang vong tay')
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'red', N'redbull', N'15000', N'lon', N'D:\sp\red.jpg', NULL)
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'sdblue', N'soda blueberry', N'25000', N'ly', N'D:\sp\sdblue.jpg', NULL)
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'sdbsky', N'soda bluesky', N'25000', N'ly', N'D:\sp\sdbsky.jpg', NULL)
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'sdras', N'soda rasberry', N'25000', N'ly', N'D:\sp\sdras.jpg', NULL)
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'yg', N'yogurt', N'20000', N'hu', N'D:\sp\yg.jpg', NULL)
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'ygda', N'yogurt da', N'22000', N'ly', N'D:\sp\ygda.jpg', NULL)
INSERT [dbo].[SanPham] ([MaSP], [TenSP], [GiaSP], [DVT], [URL], [GhiChu]) VALUES (N'ygxay', N'yogurt da xay', N'25000', N'ly', N'D:\sp\100.jpg', NULL)
SET IDENTITY_INSERT [dbo].[ThongKe] ON 

INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (1, N'lip', N'20/08/2017', 1)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (2, N'coca', N'20/08/2017', 2)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (3, N'lipsua', N'20/08/2017', 2)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (4, N'coca', N'20/08/2017', 2)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (5, N'lipsua', N'20/08/2017', 2)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (6, N'pep', N'20/08/2017', 2)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (7, N'ygda', N'20/08/2017', 1)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (8, N'ygxay', N'20/08/2017', 3)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (9, N'lip', N'20/08/2017', 1)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (29, N'cfden', N'22/08/2017', 1)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (30, N'lip', N'25/08/2017', 2)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (31, N'lip', N'25/08/2017', 2)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (32, N'cfden', N'25/08/2017', 5)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (33, N'cfsua', N'25/08/2017', 5)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (34, N'100', N'25/08/2017', 20)
INSERT [dbo].[ThongKe] ([STT], [MaSP], [Ngay], [SL]) VALUES (35, N'cfsua', N'25/08/2017', 22)
SET IDENTITY_INSERT [dbo].[ThongKe] OFF
ALTER TABLE [dbo].[ChiTietHD]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHD_Ban] FOREIGN KEY([MaBan])
REFERENCES [dbo].[Ban] ([MaBan])
GO
ALTER TABLE [dbo].[ChiTietHD] CHECK CONSTRAINT [FK_ChiTietHD_Ban]
GO
ALTER TABLE [dbo].[ChiTietHD]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHD_HoaDon] FOREIGN KEY([MaHD])
REFERENCES [dbo].[HoaDon] ([MaHD])
GO
ALTER TABLE [dbo].[ChiTietHD] CHECK CONSTRAINT [FK_ChiTietHD_HoaDon]
GO
ALTER TABLE [dbo].[GoiSP]  WITH CHECK ADD  CONSTRAINT [FK_GoiSP_Ban] FOREIGN KEY([MaBan])
REFERENCES [dbo].[Ban] ([MaBan])
GO
ALTER TABLE [dbo].[GoiSP] CHECK CONSTRAINT [FK_GoiSP_Ban]
GO
ALTER TABLE [dbo].[GoiSP]  WITH CHECK ADD  CONSTRAINT [FK_GoiSP_SanPham] FOREIGN KEY([MaSP])
REFERENCES [dbo].[SanPham] ([MaSP])
GO
ALTER TABLE [dbo].[GoiSP] CHECK CONSTRAINT [FK_GoiSP_SanPham]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_ChiTietNV] FOREIGN KEY([Username])
REFERENCES [dbo].[NhanVien] ([Username])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_ChiTietNV]
GO
ALTER TABLE [dbo].[ThongKe]  WITH CHECK ADD  CONSTRAINT [FK_ThongKe_SanPham] FOREIGN KEY([MaSP])
REFERENCES [dbo].[SanPham] ([MaSP])
GO
ALTER TABLE [dbo].[ThongKe] CHECK CONSTRAINT [FK_ThongKe_SanPham]
GO
USE [master]
GO
ALTER DATABASE [Coffee] SET  READ_WRITE 
GO
