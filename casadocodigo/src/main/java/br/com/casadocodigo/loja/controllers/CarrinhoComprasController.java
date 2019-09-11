package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

/**
 *  Controller para manipular o carrinho de compras.
 *
 */

@Controller
@RequestMapping("/carrinho")
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
