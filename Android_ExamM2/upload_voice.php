<?php 
include("db.php");
global $conn;
ini_set('upload_max_filesize', '10M');
ini_set('post_max_size', '10M');
ini_set('max_input_time', 300);
ini_set('max_execution_time', 300);
$target_path = "";

$name = $_POST["fullname"];

try{
// 	
//$target_path = $target_path . basename($_FILES['fileDuocChon']['name']);

// tao file moi cung ten voi tai khoan dang nhap
$target_path = $target_path . $name.".wav";
// ten hinh == username.jpg
move_uploaded_file($_FILES['fileDuocChon']['tmp_name'], "sound/".$target_path);
// cap nhat lai thong tin hinh cua tai khoan dang dang nhap

$sql = "UPDATE animal SET voice ='".$name.".wav"."'";

	if ($conn->query($sql) === TRUE) {
		$fileName = $_FILES['fileDuocChon']['name'];
		$fileType = $_FILES['fileDuocChon']['type'];
		echo "\"TapTinUpload\":{\"TenFile\":\"$fileName\",\"LoaiFile\":\"$fileType\"}";
	} else {
		 echo "{\"loi\":\"Khong cap nhat dc hinh\"}";
	}
}
catch(Exception $ex)
{
	echo 'Message: ' .$ex->getMessage();
}
?>