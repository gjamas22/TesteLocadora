package br.ce.wcaquino.daos;

import java.util.List;

import br.ce.wcaquino.entidades.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacaco);

	public List<Locacao> obterLocacoesPendentes();
}
