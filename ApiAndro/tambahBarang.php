<?php 
	
	if($_SERVER['REQUEST_METHOD']=='POST' and $_SERVER['HTTP_PATH']=='tambahBarang'){
		//Getting values
		$nama = $_SERVER['HTTP_NAMA'];
		$merek = $_SERVER['HTTP_MEREK'];
		$warna = $_SERVER['HTTP_WARNA'];
		$tahun = $_SERVER['HTTP_TAHUN'];
		$harga = $_SERVER['HTTP_HARGA'];
		$keterangan = $_SERVER['HTTP_KETERANGAN'];
		$kondisi = $_SERVER['HTTP_KONDISI'];
		date_default_timezone_set('Asia/Jakarta');
		$tanggal_masuk =date("Y-m-d H:i:s");

		if(trim($nama)==""
			or trim($merek)==""
			or trim($warna)==""
			or trim($tahun)==""
			or trim($harga)==""
			or trim($keterangan)==""
			or trim($kondisi)==""
		){
			echo "Tidak dapat mendaftarkan barang. Data yang diberikan tidak valid.";

		}else{
			//Creating an sql query
			$sql = "INSERT INTO barang (nama, merek, warna, tahun, harga, keterangan, kondisi, tanggal_masuk) 
			VALUES ('$nama','$merek','$warna','$tahun','$harga','$keterangan','$kondisi','$tanggal_masuk')";
			
			//Importing our db connection script
			require_once('dbConnect.php');
			
			//Executing query to database
			if(mysqli_query($con,$sql)){
				echo 'Barang berhasil didaftarkan.';
			}else{
				echo 'Tidak dapat mendaftarkan barang.';
			}
			
			//Closing the database 
			mysqli_close($con);

		}

		
	} else {
		echo 'Akses tidak sah.';

	}