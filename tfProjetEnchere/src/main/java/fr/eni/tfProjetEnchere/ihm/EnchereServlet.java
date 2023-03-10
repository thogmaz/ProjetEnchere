package fr.eni.tfProjetEnchere.ihm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.eni.tfProjetEnchere.bll.ArticleVenduManager;
import fr.eni.tfProjetEnchere.bll.BLLException;
import fr.eni.tfProjetEnchere.bll.CategorieManager;
import fr.eni.tfProjetEnchere.bll.EnchereManager;
import fr.eni.tfProjetEnchere.bll.UtilisateurManager;
import fr.eni.tfProjetEnchere.bo.ArticleVendu;
import fr.eni.tfProjetEnchere.dal.DALException;

/**
 * Servlet implementation class EnchereServlet
 */
@WebServlet("/EnchereServlet")
public class EnchereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ArticleVenduManager articleVenduManager;
	private CategorieManager categoriesManager;
	private EnchereManager enchereManager;
	@SuppressWarnings("unused")
	private UtilisateurManager utilisateurManager;
	private ArticleVendu articleVendu;
	HttpSession session;

	public EnchereServlet() {
		articleVenduManager = new ArticleVenduManager();
		categoriesManager = new CategorieManager();
		enchereManager = new EnchereManager();
		utilisateurManager = new UtilisateurManager();
	}

	@SuppressWarnings("static-access")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//int id = Integer.parseInt(request.getParameter("noArticle"));
		try {
			//articleVendu = articleVenduManager.getArticleById(id);
			session = request.getSession();
			List<ArticleVendu> articleVendus = articleVenduManager.selectAllArticles();
			session.setAttribute("article", articleVendus);
			for (ArticleVendu art : articleVendus) {
				session.setAttribute("enchere", enchereManager.selectMesEncheres(art.getNoArticle()));

			}
			
			session.setAttribute("categorie", categoriesManager.getAllCategories());
			session.setAttribute("article", articleVenduManager.selectAllArticles());
	//		session.setAttribute("user", utilisateurManager.selectUtilisateurById(articleVendu.getVendeur().getId()));
			request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

		} catch (BLLException | DALException | SQLException e) {
			e.printStackTrace();
		}
	}
}
