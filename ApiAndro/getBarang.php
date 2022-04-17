<?php 

	if($_SERVER['REQUEST_METHOD']=='POST' and $_SERVER['HTTP_PATH']=='getDetailBarang'){

		//Getting the requested id
	$id =  $_SERVER['HTTP_ID'];
    //$id = 1;
	
	//Importing database
	require_once('dbConnect.php');
	
	//Creating sql query with where clause to get an specific employee
	$sql = "SELECT * FROM barang WHERE id = $id";
	
	//getting result 
	$r = mysqli_query($con,$sql);
	
	//pushing result to an array 
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"id"=>$row['id'],
			"nama"=>$row['nama'],
			"merek"=>$row['merek'],
			"warna"=>$row['warna'],
			"tahun"=>$row['tahun'],
			"harga"=>$row['harga'],
			"keterangan"=>$row['keterangan'],
			"kondisi"=>$row['kondisi'],
			"tanggal_masuk"=>$row['tanggal_masuk'],
			"tanggal_update"=>$row['tanggal_update'],

		));

	//displaying in json format 
	echo json_encode($result);
	
	mysqli_close($con);

	}else{
		echo "Akses tidak sah.";
	}