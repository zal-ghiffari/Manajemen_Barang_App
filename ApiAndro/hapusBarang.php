<?php 

 if($_SERVER['REQUEST_METHOD']=='POST' and $_SERVER['HTTP_PATH']=='hapusBarang'){
	//Getting Id
	 $id = $_SERVER['HTTP_ID'];
	 
	 //Importing database
	 require_once('dbConnect.php');
	 
	 //Creating sql query
	 $sql = "DELETE FROM barang WHERE id = $id;";
	 
	 //Deleting record in database 
	 if(mysqli_query($con,$sql)){
	 	echo 'Barang berhasil dihapus.';
	 }else{
	 	echo 'Tidak dapat menghapus barang ini, silakan coba lagi nanti.';
	 }
	 
	 //closing connection 
	 mysqli_close($con);
	} else {
		echo 'Akses tidak sah.';

	}