﻿CREATE DATABASE PROJECT_JAVA
GO

USE PROJECT_JAVA
GO

CREATE TABLE TAIKHOAN(
	TENDANGNHAP NVARCHAR(50) PRIMARY KEY NOT NULL,
	MATKHAU NVARCHAR(50) NOT NULL,
	VAITRO NVARCHAR(20) NOT NULL CHECK (VAITRO='NHANVIEN' OR VAITRO='ADMIN'),
	IDNHANVIEN INT NOT NULL
	--Khoá Ngoại
	FOREIGN KEY(IDNHANVIEN) REFERENCES NHANVIEN(IDNHANVIEN),
);

--Chèn dữ liệu
INSERT INTO dbo.TAIKHOAN VALUES(N'VUPVPD02716',N'0123456',N'ADMIN',1)
INSERT INTO dbo.TAIKHOAN VALUES(N'CONGTTTPD02658',N'123456',N'NHANVIEN',2)
INSERT INTO dbo.TAIKHOAN VALUES(N'LULVPD02717',N'123456',N'NHANVIEN',3)

SELECT*FROM TAIKHOAN

CREATE TABLE NHANVIEN(
	TENNHANVIEN NVARCHAR(50) NOT NULL,
	IDNHANVIEN INT IDENTITY PRIMARY KEY NOT NULL,
	NGAYSINH DATE NOT NULL,
	SDT CHAR(10) NOT NULL,
	GIOITINH BIT NOT NULL
);

--Chèn dữ liệu
INSERT INTO dbo.NHANVIEN VALUES(N'PHAN VĂN VŨ','2000-12-04','0123456789',1)
INSERT INTO dbo.NHANVIEN VALUES(N'TÔN THẤT THÀNH CÔNG','2000-11-04','0123456789',1)
INSERT INTO dbo.NHANVIEN VALUES(N'LÊ VĂN LƯU','2000-10-04','0123456789',1)

SELECT*FROM NHANVIEN

CREATE TABLE DANHMUC(
	IDDANHMUC INT IDENTITY PRIMARY KEY NOT NULL,
	TENDANHMUC NVARCHAR(50) NOT NULL
);

--Chèn dữ liệu
INSERT INTO dbo.DANHMUC VALUES(N'ĐỒ ĂN')
INSERT INTO dbo.DANHMUC VALUES( N'ĐỒ UỐNG')

SELECT*FROM dbo.DANHMUC

CREATE TABLE DOUONG(
	IDDOUONG INT IDENTITY PRIMARY KEY NOT NULL,
	TENDOUONG NVARCHAR(50) NOT NULL,
	IDDANHMUC INT NOT NULL,
	DONGIA FLOAT DEFAULT 0 NOT NULL
	--Khoá ngoại
	FOREIGN KEY(IDDANHMUC) REFERENCES DANHMUC(IDDANHMUC),
);

INSERT INTO dbo.DOUONG VALUES(N'TRÀ SỮA',1,15000),(N'SINH TỐ',1,15000),(N'COCCA',1,15000),
	(N'PEPSI',1,15000),(N'CAFE',1,15000),(N'CAFE CHỒN',1,15000),(N'CAFE DÊ',1,15000)

SELECT*FROM DOUONG

CREATE TABLE DOAN(
	IDDOAN INT IDENTITY PRIMARY KEY NOT NULL,
	TENDOAN NVARCHAR(50) NOT NULL,
	IDDANHMUC INT NOT NULL,
	DONGIA FLOAT DEFAULT 0 NOT NULL
	--Khoá ngoại
	FOREIGN KEY(IDDANHMUC) REFERENCES DANHMUC(IDDANHMUC),
);

INSERT INTO dbo.DOAN VALUES(N'XÚC XÍCH',2,5000),(N'CƠM CUỘN',2,25000),(N'PIZZA',2,115000),
	(N'CƠM CHIÊN',2,35000),(N'CÁ VIÊN CHIÊN',2,155000),(N'BẮP CHIÊN',2,5000),(N'CHÈ HẺ',2,315000)

SELECT*FROM DOAN

CREATE TABLE BAN(
	IDBAN INT IDENTITY PRIMARY KEY NOT NULL,
	TENBAN NVARCHAR(20) NOT NULL,
	TRANGTHAI NVARCHAR(20) CHECK(TRANGTHAI = N'Trống' OR TRANGTHAI = N'Có Người') DEFAULT N'Trống' NOT NULL,
);

INSERT INTO BAN VALUES('BÀN 1', N'Trống'),('BÀN 2', N'Trống'),('BÀN 3', N'Trống'),('BÀN 4', N'Trống')
	,('BÀN 5', N'Trống'),('BÀN 6', N'Trống'),('BÀN 7', N'Trống'),('BÀN 7', N'Trống'),('BÀN 8', N'Trống')
	,('BÀN 9', N'Trống'),('BÀN 10', N'Trống'),('BÀN 11', N'Trống'),('BÀN 12', N'Trống'),('BÀN 13', N'Trống')
	,('BÀN 14', N'Trống'),('BÀN 15', N'Trống')

SELECT*FROM BAN

CREATE TABLE HOADON(
	IDHOADON INT IDENTITY PRIMARY KEY NOT NULL,
	IDBAN INT NOT NULL,
	IDNHANVIEN INT NOT NULL,
	NGAYLAP DATE NOT NULL DEFAULT GETDATE(),
	TRANGTHAI NVARCHAR(20) DEFAULT N'Chưa' NOT NULL,
	TONGTIEN FLOAT NOT NULL
	--Khoá ngoại
	FOREIGN KEY(IDBAN) REFERENCES BAN(IDBAN),
	FOREIGN KEY(IDNHANVIEN) REFERENCES NHANVIEN(IDNHANVIEN),
);

ALTER TABLE HOADON ADD GIODEN DATETIME
ALTER TABLE dbo.HOADON ADD TENDOUONG NVARCHAR(50) NOT NULL

CREATE TABLE CHITIETHOADON(
	IDCTHOADON INT IDENTITY PRIMARY KEY NOT NULL,
	IDHOADON INT NOT NULL,
	IDDOUONG INT,
	IDDOAN INT,
	SOLUONG INT DEFAULT 0 NOT NULL,
	DONGIA FLOAT DEFAULT 0 NOT NULL
	--Khoá ngoại
	FOREIGN KEY(IDHOADON) REFERENCES HOADON(IDHOADON),
	FOREIGN KEY(IDDOUONG) REFERENCES DOUONG(IDDOUONG),
	FOREIGN KEY(IDDOAN) REFERENCES DOAN(IDDOAN),
);

ALTER AUTHORIZATION ON DATABASE::PROJECT_JAVA TO sa 
