package br.com.casadocodigo.loja.models;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Bean para gerenciar o carrinho de compras na loja.
 * Por default, o escopo é de application, o que significa que este component seria único para
 * todos os usuários (sigleton). Isso não faz sentido, já que deveria existir um carrinho de compras
 * pra cada usuário, por isso o escopo de sessão (um objeto desse é criado para cada sessão de usuário).
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class CarrinhoCompras {
	
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();
	
	public void add(CarrinhoItem item) {
		itens.put(item, getQuantidade(item) + 1);
	}
	
	/*
	 * Método que inicializa a quantidade o item como zero na primeira vez que ele é adicionado no carrinho
	 * através do método put, que apenas atualiza o valor no map.
	 * Caso ele já exista, então a quantidade real será retornada.
	 */
	private int getQuantidade(CarrinhoItem item) {
		if(!itens.containsKey(item)) {
			itens.put(item, 0);
		}
		
		return itens.get(item);
	}
	
	/*
	 * Este código percorre toda a lista de itens e soma o valor de cada item a um aculumador para que o total
	 * de itens no carrinho seja retornado.
	 */
	public int getQuantidade(){
	    return itens.values().stream().reduce(0, (proximo, acumulador) -> (proximo + acumulador));
	}
	
}
