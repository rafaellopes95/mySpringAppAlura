package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/* 
 * Annotation para configurar beans baseado na classe.
 * Esta classe tem o propósito de salvar arquivos no disco e retornar o caminho relativo onde foi salvo.
 * O Spring se encarrega de injetar o HttpServletRequest para que seja possível pegar o caminho absoluto do servidor
 * de onde se encontra o base folder, para que depois o caminho completo com o nome do arquivo seja montado.
 */
@Component
public class FileSaver {

	@Autowired
	private HttpServletRequest request;
	
	public String write(String baseFolder, MultipartFile file) {
		String absolutePath = request.getServletContext().getRealPath("/" + baseFolder);
		String path = absolutePath + "/" + file.getOriginalFilename();
		
		try {
			file.transferTo(new File(path));
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
		
		return baseFolder + "/" + file.getOriginalFilename();
	}
	
}
