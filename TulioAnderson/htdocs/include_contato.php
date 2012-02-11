<?php

$nome 			=	 $_POST["nome"];
$email 			=	 $_POST["email"];
$assunto 		=	 $_POST["assunto"];
$mensagem 		=	 $_POST["mensagem"];

include("sendemail_config.php");

//Testa campos obrigatorios
if ($nome != "" and $mensagem != "" and $email!=""){

	$msg="Nome: $nome\n";
	$msg.="E-mail: $email\n";
	$msg.="Assunto: $assunto\n";
	$msg.="DDD: $ddd\n";
	$msg.="Telefone: $fone\n";
	$msg.="$mensagem\n";

	if (mail ($mail_destino, $assunto, $msg, $mail_header)){
		//Imprimindo confirmação de envio
		echo "<div class=alertEmail>Sua mensagem foi enviada com sucesso para e-mail $mail_destino!</div><br>";
	} else {
		echo "<div class=alertEmail>Erro ao enviar e-mail! Por favor, confira se todos os dados foram preenchidos corretamente.</div>";
	}
} else {
?>

	<table width="100%" border="0">
		<tr>
			<td align="center">
			<p class="main_title">Formul&aacute;rio de Contato</p>
			<form action="include_contato.php" method="post" name="form" id="form" target="_blank">
				<table width="100" border="0" cellspacing="10" cellpadding="0"
					align="center">
					<tr>
						<td valign="top" width="100" nowrap="nowrap"><font class="text">Nome:</font></td>
						<td><input class="pesquisaSelect" type="text" name="nome" size="34" /></td>
					</tr>
					<tr>
						<td valign="top" width="100" nowrap="nowrap"><font class="text">E-mail:</font></td>
						<td><input class="pesquisaSelect" type="text" name="email" size="34" /></td>
					</tr>
					<tr>
						<td valign="top" width="100" nowrap="nowrap"><font class="text">Telefone:</font></td>
						<td><input class="campoSimples" value="31" type="text" name="ddd"
							size="2" maxlength="2" /> <input class="campoSimples" type="text"
							name="fone" size="10" maxlength="8" /></td>
					</tr>
					<tr>
						<td valign="top" width="100" nowrap="nowrap"><font class="text">Assunto:</font></td>
						<td><select class="pesquisaSelect" name="assunto">
							<option value="Sugest&atilde;o">Sugest&atilde;o</option>
							<option value="Shows">Contrata&ccedil;&atilde;o de Shows</option>
							<option value="Sem assunto">Outros</option>
						</select></td>
					</tr>
					<tr>
						<td valign="top" width="100" nowrap="nowrap"><font class="text">Mensagem:</font></td>
						<td><textarea class="pesquisaSelect" name="mensagem" cols="34"
							rows="4"></textarea></td>
					</tr>
					<tr>
						<td colspan="2" valign="middle"><br />
						<div align="center"><input class="form_botao" type="submit"
							name="Enviar" value="Enviar" /> <input class="form_botao"
							type="reset" name="Limpar" value="Limpar" /></div>
						</td>
					</tr>
				</table>
			</form>
			</td>
			<td>
				<p class="main_title">Dados Para Contato</p>
				Diva Carvalhar<br/>
				(31)92960462 / (31)96525619 / (31)32345619<br/>
				tulioeanderson@gmail.com
			</td> 
		</tr>
	</table>

<?php 
}
?>
