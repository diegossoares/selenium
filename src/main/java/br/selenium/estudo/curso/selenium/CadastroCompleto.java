package br.selenium.estudo.curso.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CadastroCompleto {
	private WebDriver driver;
	private DSL dsl;
	private CampoTreinamentoPage page;

	@Before
	public void inicializa() {
		driver = new FirefoxDriver();
		driver.manage().window().setSize(new Dimension(1200, 765));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		dsl = new DSL(driver);
		page = new CampoTreinamentoPage(driver);

	}

	@After
	public void finaliza() {
		driver.quit();
	}

	@Test
	public void deveRealizarCadastroComSucesso() {
		page.setNome("Diego");
		page.setSobrenome("Soares");
		page.setSexoMasculino();
		page.setComidaPizza();
		page.setEscolaridade("Mestrado");
		page.setEsporte("Natacao");
		page.cadastrar();


		

		Assert.assertTrue(page.obterResultadoCadastro().startsWith("Cadastrado!"));
		Assert.assertTrue(page.obterNomeCadastro().endsWith("Diego"));
		Assert.assertEquals("Sobrenome: Soares", page.obterSobrenomeCadastro());
		Assert.assertEquals("Sexo: Masculino",page.obterSexoCadastro());
		Assert.assertEquals("Comida: Pizza", page.obterComidaCadastro());
		Assert.assertEquals("Escolaridade: mestrado", page.obterEscolaridadeCadastro());
		Assert.assertEquals("Esportes: Natacao", page.obterEsporteCadastro());
	}

	@Test
	public void deveValidarNomeObrigatorio() {
		page.cadastrar();
		Assert.assertEquals("Nome eh obrigatorio", dsl.alertaObterTextoEAceita());
	}

	@Test
	public void deveValidarSobrenomeObrigatorio() {
		page.setNome("Diego");
		page.cadastrar();
		Assert.assertEquals("Sobrenome eh obrigatorio", dsl.alertaObterTextoEAceita());
	}

	@Test
	public void deveValidarSexoObrigatorio() {
		page.setNome("Diego");
		page.setSobrenome("Soares");
		page.cadastrar();
		Assert.assertEquals("Sexo eh obrigatorio", dsl.alertaObterTextoEAceita());
	}

	@Test
	public void deveValidarComidaVegetariana() {
		
		page.setNome("Diego");
		page.setSobrenome("Soares");
		page.setSexoFeminino();
		page.setComidaCarne();
		page.setComidaVegetariana();
		page.cadastrar();
		Assert.assertEquals("Tem certeza que voce eh vegetariano?", dsl.alertaObterTextoEAceita());
	}

	@Test
	public void deveValidarEsportistaIndeciso() {
		page.setNome("Diego");
		page.setSobrenome("Soares");
		page.setSexoFeminino();
		page.setComidaCarne();
		page.setEsporte("Karate", "O que eh esporte?");
		dsl.clicarBotao("elementosForm:cadastrar");
		Assert.assertEquals("Voce faz esporte ou nao?", dsl.alertaObterTextoEAceita());
	}
}
