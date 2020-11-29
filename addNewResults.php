/*
Example of a .php file used to store the user scores on an external website
*/
<?php 
        $moves   = urldecode($_POST['moves']);
        $file = 'results.txt';
file_put_contents($file, $moves);
 
?>