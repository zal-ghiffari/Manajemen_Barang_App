-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 17 Apr 2022 pada 10.21
-- Versi server: 10.4.21-MariaDB
-- Versi PHP: 7.4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `andro_api`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `id` int(11) NOT NULL,
  `nama` varchar(40) NOT NULL,
  `merek` varchar(50) NOT NULL,
  `warna` varchar(30) NOT NULL,
  `tahun` int(11) NOT NULL,
  `harga` double NOT NULL,
  `keterangan` varchar(150) DEFAULT NULL,
  `kondisi` tinyint(1) NOT NULL,
  `tanggal_masuk` datetime NOT NULL,
  `tanggal_update` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`id`, `nama`, `merek`, `warna`, `tahun`, `harga`, `keterangan`, `kondisi`, `tanggal_masuk`, `tanggal_update`) VALUES
(1, 'laptop', 'Lenovo W540', 'Biru', 2014, 2600000, 'Dilelang', 0, '2022-04-17 06:28:47', NULL),
(2, 'Laptop', 'Acer A23', 'Hitam', 2018, 4760000, 'Dilelang', 1, '2022-04-17 06:33:36', '2022-04-17 06:33:36'),
(3, 'Printer', 'Canon 360', 'Abu', 2019, 1500000, 'Pengadaan organisasi', 1, '2022-04-17 10:01:15', NULL),
(4, 'Kamera', 'Fuji Film XT40', 'Hijau', 2021, 6000000, 'Hadiah Paman', 0, '2022-04-17 10:02:06', NULL);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `barang`
--
ALTER TABLE `barang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
