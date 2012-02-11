<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Tulio e Anderson</title>
	<link href="css/default.css" rel="stylesheet" type="text/css"/>
	<script language="Javascript" src="js/jquery_1.4.4.js"></script>
	<script language="Javascript" src="js/default.js"></script>
	<script type="text/javascript">
	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-381374-2']);
	  _gaq.push(['_trackPageview']);
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();
	</script>
</head>
<body bgcolor="#fffefc">

	<div id="header" align="center">
	    <?php include("include_header.php"); ?>
	</div>
	
	<div id="main_body">
		<?php
			$page = $_GET["page"];
			if ($page == null) $page="index"
		?>
		<?php include("include_". $page .".php"); ?>
	</div>
	
	<div id="footer">
		Todos os direitos reservados. Site by Vilela.
	</div>

</body>
</html>
