
function drawGaleryMenu(){
	var groups = Math.round(number_of_images / pagination);
	document.write("<div class='barra_paginacao'>");
	for (i = 1; i <= groups; i++) {
		document.write("<a onClick='showGroup(" + i + ");'>Galeria " + i + "</a> | ");
	}
	document.write("</div>");
}

function drawGaleryBody() {
	var groups = Math.round(number_of_images / pagination);
	var count = 0;
	var count_elements = 1;
	var count_groups = 0;
	
	for (i = 0; i < number_of_images; i++) {

		if (count_elements == 1) {
			count_groups = count_groups + 1;
			document.write("<DIV id='group" + (count_groups) + "' style='display:none;'>");
		}

		document.write("<a href='" + images_path + i
				+ "_full.jpg' target=_blank>");
		document.write("<img src='" + images_path + i + "_mini.jpg' "
				+ " width=" + thumb_width 
				+ " height=" + thumb_heigth
				+ " class='thumb' " 
				+ "onmouseover=\"swapImage('mainImage','','" + images_path + i + "_normal.jpg',1);\"/></a>");

		if (count_elements == pagination || (count - 1) == i) {
			count_elements = 1;
			document.write("</DIV>");
		} else {
			count_elements = count_elements + 1;
		}
	}

	showGroup(1);
}

function showGroup(group) {
	var groups = Math.round(number_of_images / pagination);
	for (i = 1; i <= groups; i++) {
		if (i == group) {
			document.getElementById('group' + i).style.display = 'block';
		} else {
			document.getElementById('group' + i).style.display = 'none';
		}
	}
}