<?php

	//Defining Constants
	define('HOST','localhost');
	define('USER','root');
	define('PASS','');
	define('DB','andro_api');
	
	//Connecting to Database
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Gak bisa konek!.');
	
?>