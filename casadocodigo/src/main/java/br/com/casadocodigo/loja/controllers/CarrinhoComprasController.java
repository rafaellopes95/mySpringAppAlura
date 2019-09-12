package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

/**
 * Controller para manipular requisições do carrinho de compras.
 * Por default o escopo é de application, o que significa que este controller seria único para
 * todos os usuários (sigleton). É mais comum que controllers tenham escopo de request, cuja
 * intância dura apenas o tempo da requisição (um objeto do controller é criado para cada
 * request, e "morre" após seu término).
 * 
 * Controller no geral tem um papel bem definido, ele simplesmente trata a requisição. Ele recebe 
 * os dados, repassa para um outro objeto e devolve uma resposta. Pensando assim, podemos concluir 
 * que após a requisição ser atendida, não faz sentido que o controller ainda esteja "vivo". 
 * Geralmente, o escopo dos controllers é o de request. Isto significa que quando há uma requisição, 
 * o controller é criado e depois, ela deixa de existir.
 */
@Controller
@RequestMapping("/carrinho")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {

	@Autowired
	private ProdutoDAO produtoDao;
	
	@Autowired
	private CarrinhoCompras carrinhoCompras;
	
	@PostMapping("/add")
	public ModelAndView add(Integer produtoId, TipoPreco tipo) {
		ModelAndView mav = new ModelAndView("redirect:/produtos");
		CarrinhoItem item = criaItem(produtoId, tipo);
		carrinhoCompras.add(item);
		return mav;
	}

	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipo) {
		Produto produto = produtoDao.find(produtoId);
		CarrinhoItem item = new CarrinhoItem(tipo, produto);
		return item;
	}
	
}
