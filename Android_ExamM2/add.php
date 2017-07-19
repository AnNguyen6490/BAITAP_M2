<?php
include("db.php");
global $conn;

$name = $_POST["name"];
$image = $_POST["image"];
$voice = $_POST["voice"];

$insert  = "INSERT INTO animal(name, image, voice)
VALUES('$name', '$image','$voice')";

if ($conn->query($insert) === TRUE) {
    echo "{\"OK\":\"Them thanh cong}";
} else {
   echo "{\"loi\":\"Them khong thanh cong\"}";
}

$conn->close();
?>