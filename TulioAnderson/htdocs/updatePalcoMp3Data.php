<?php
	$file = './PalcoMp3Data.html';
	$delete = unlink ($file);
	if($delete) {
		echo "Cache deletado com sucesso!";
	}
	else {
		echo "Cache n‹o pode ser deletado";
	}
	
	// Open the file to get existing content
	$current = str_replace('display:none', '', file_get_contents('http://www.palcomp3.com/tulioeanderson'));
	$current = str_replace('/busca_show', 'http://www.palcomp3.com/busca_show', $current);
	$current = str_replace('<h3>', '<div class=main_title>', $current);
	$current = str_replace('</h3>', '</div>', $current);
	
	echo ($current);
	
	// Write the contents back to the file
	file_put_contents($file, $current);
?>