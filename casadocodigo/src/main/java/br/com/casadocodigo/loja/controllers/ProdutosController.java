package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired
	private ProdutoDAO produtoDAO;
	
	/*
	 * InitBinder é quem adiciona os validators para que os mesmos possam ser usados durante o binding para validação.
	 * 
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}

	/*
	 * Antigo método form, que servia apenas para retornar a view sem nenhum model:
	 * 
	 * @RequestMapping("/produtos/form") public String form() { return
	 * "produtos/form"; }
	 */

	/*
	 * Quando utilizamos o ModelAndView, além de retornarmos as páginas, temos a
	 * possibilidade de enviar objetos de qualquer classe para a página caso seja
	 * necessário, e então, exibir suas informações.
	 * 
	 * Um produto é necessário aqui pois como o uso dos form:input no front end,
	 * o Spring espera que haja um objeto do tipo Produto disponível para requisição,
	 * que é facilmente resolvido colocando ele no método.
	 */
	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		ModelAndView mav = new ModelAndView("/produtos/form");
		mav.addObject("tipos", TipoPreco.values());
		return mav;
	}

	/*
	 * Quando utilizamos o atributo method da anotação @RequestMapping, estamos
	 * indicando ao Spring MVC que esse mapeamento será chamado apenas se o método
	 * de requisição do HTTP for do tipo mencionado, ou seja, quando indicamos o
	 * valor RequestMethod.GET estamos dizendo que o método é GET, quando é
	 * `RequestMethod.POST, método POST e assim por diante. No Spring MVC podemos
	 * também utilizar os demais métodos que fazem parte da especificação do HTTP.
	 * Caso tenha dúvidas sobre o protocolo HTTP, temos também um curso sobre HTTP.
	 * Nesse caso, utilizamos esse atributo para acessar duas funcionalidades
	 * distintas com o mesmo endereço. Em outras palavras, para o mapeamento
	 * "/produtos" usando o método GET, acessamos a lista de produtos e quando
	 * acessamos essa mesma URL via POST, significa que estamos enviando um produto
	 * para ser salvo.
	 * 
	 * AlwaysRedurectAfterPost: padrão de projeto em que após um cadastro (request POST), o usuário deve ser redirecionado
	 * para a respectiva página cujo método seja GET. Isso evita que ao pressionar a tecla F5 o mesmo formulário seja
	 * enviado novamente, duplicando as informações. Assim, após o redirecionamento o formulário daquela requisição já
	 * feita deixa de existir.
	 * 
	 * RedirectAttributes serve para mandarmos atributos quando o método fizer um redirecionamento (código 302).
	 * Neste caso, como queremos mandar uma mensagem de sucesso após o cadastro com sucesso, um flashAttribute pode
	 * ser usado para guardar essa mensagem e ser exibida após o redirect. Atributos do tipo Flash têm uma particularidade 
	 * que é interessante observar. Eles só duram até a próxima requisição, ou seja, transportam informações de uma requisição 
	 * para a outra e, então, deixam de existir.
	 * 
	 * Valid serve para aplicar a validação no objeto, que será feito pelo validator configurado pra fazer binding com a classe
	 * Produto (no InitBinder).
	 * 
	 * BindingResult é quem guardará o resultado da validação do objeto do parâmetro anterior (o BindingResult deve sempre vir
	 * logo depois do objeto a ser validado na order da assinatura do método para que possa funcionar!).
	 * 
	 * MultipartFile serve para receber um arquivo da requisição, e já possui alguns métodos próprios para o arquivo recebido.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		
		System.out.println(sumario.getOriginalFilename());
		
		if(result.hasErrors()) {
			return form(produto);
		}
		
		System.out.println(produto);
		produtoDAO.gravar(produto);
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		return new ModelAndView("redirect:/produtos");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		List<Produto> produtos = produtoDAO.listar();
		ModelAndView mav = new ModelAndView("produtos/lista");
		mav.addObject("produtos", produtos);
		return mav;
	}
}
