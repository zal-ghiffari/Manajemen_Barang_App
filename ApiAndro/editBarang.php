<?php 
	
	if($_SERVER['REQUEST_METHOD']=='POST' and $_SERVER['HTTP_PATH']=='editBarang'){
		//Getting values
		$id = $_SERVER['HTTP_ID'];
		$nama = $_SERVER['HTTP_NAMA'];
		$merek = $_SERVER['HTTP_MEREK'];
		$warna = $_SERVER['HTTP_WARNA'];
		$tahun = $_SERVER['HTTP_TAHUN'];
		$harga = $_SERVER['HTTP_HARGA'];
		$keterangan = $_SERVER['HTTP_KETERANGAN'];
		$kondisi = $_SERVER['HTTP_KONDISI'];
		//$dt_cadastro = $_SERVER['HTTP_DT_CADASTRO'];
		date_default_timezone_set('Asia/Jakarta');
		$tanggal_update =date("Y-m-d H:i:s");

		if(trim($nama)==""
			or trim($merek)==""
			or trim($warna)==""
			or trim($tahun)==""
			or trim($harga)==""
			or trim($keterangan)==""
			or trim($kondisi)==""
		){
			echo "Tidak dapat edit barang. Data yang dimasukkan tidak valid.";

		}else{
			//Creating an sql query
			$sql = "UPDATE barang SET 
			nama = '$nama', 
			merek = '$merek', 
			warna = '$warna',
			tahun = '$tahun',
			harga = '$harga',
			keterangan = '$keterangan',
			kondisi = '$kondisi',
			tanggal_update = '$tanggal_update'
			WHERE id = $id;";
			
			//Importing our db connection script
			require_once('dbConnect.php');
			
			//Executing query to database
			if(mysqli_query($con,$sql)){
				echo 'Barang berhasil diperbarui.';
			}else{
				echo 'Tidak dapat memperbarui barang.';
			}
			
			//Closing the database 
			mysqli_close($con);

		}

		
	} else {
		echo 'Akses tidak sah.';
	}