<?php
include("db.php");
global $conn;
$sql = "SELECT id, name, image, voice FROM animal";
$result = mysqli_query($conn, $sql);
if (mysqli_num_rows($result) > 0) {
 $json = array();
 while($row = mysqli_fetch_assoc($result))
 {
  $animal = array();
  $animal ["id"] = $row["id"];
  $animal ["name"] = $row["name"];
  $animal ["image"] = $row["image"]; 
  $animal ["voice"] = $row["voice"]; 
  array_push($json, $animal);
 }
 echo json_encode($json);
} else {
 echo "not found!!!";
return;
}
mysqli_close($conn);
?>
