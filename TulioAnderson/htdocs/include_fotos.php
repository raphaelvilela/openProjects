<script language="javascript" src="js/image_galery.js"></script>
<link href="css/image_galery.css" rel="stylesheet" type="text/css"/>

<div class="main_title">Fotos</div>

<table>
  <tr>
    <td width="50%">
    	<img src="images/fotos/0_normal.jpg" id="mainImage">
    </td>
    <td width="50%">
		<script>
			
			//Numero de imagens
			var number_of_images = 39;
			
			//Numero de imagens por pagina
			var pagination = 12;
			
			//Path das imagens
			var images_path = "images/fotos/";
			
			//Comprimento dos Thumbs
			var thumb_width = 100;
			
			//Altura dos Thumbs
			var thumb_heigth = 100;

			drawGaleryMenu();
			
			//Desanhando a galeria de imagem
			drawGaleryBody();
				
		</script>
	</td>
  </tr>
</table>




