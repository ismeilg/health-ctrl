package com.healthctrl.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import com.healthctrl.objects.ComponentResult;
import com.healthctrl.utils.AnalyzeUtils;
import com.healthctrl.utils.ComponentsUtils;
import com.healthctrl.utils.UserDetailsUtils;

/**
 * Servlet implementation class TrackingServlet
 */
@WebServlet("/TrackingServlet")
public class TrackingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ComponentsUtils compUtils;
	@Resource(name="jdbc/sadna")
	private DataSource dataSource;

	/**
	 * @throws ServletException 
	 * @see HttpServlet#HttpServlet()
	 */
	public TrackingServlet() throws ServletException {
		super.init();
		try{
			compUtils = new ComponentsUtils(dataSource);
		}
		catch (Exception e){
			throw new ServletException(e);
		}
	}
	public TrackingServlet(DataSource dataSource) throws ServletException {
		super.init();
		try{
			compUtils = new ComponentsUtils(dataSource);
		}
		catch (Exception e){
			throw new ServletException(e);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//read command param
			compUtils = new ComponentsUtils(dataSource);
			String theCommand = request.getParameter("command");
			if(theCommand == null){
				theCommand = "LIST";		
			}
			//route it to appropiate method
			switch (theCommand){
			case "summary":
				getAllTests(request, response);
				break;
			case "recommendation":
				getLastRecommendation(request, response);
				break;
			case "all_results":
				getAllTests(request, response);
				break;
			} 
			//list the components result in MVC
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ServletException(e);
		}
	}

	private void getLastRecommendation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnalyzeUtils analyze = new AnalyzeUtils(dataSource);
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		String [] recommendation = analyze.getLastRecommendation(user_id);
		String sportRecommendation = analyze.getLastSportRecommendation(user_id);
		int heartRrate = analyze.getrecommendedHeartRate(user_id);
		String [] sportBefore = analyze.getBeforeSport(user_id);
		String [] notBeforeSport = analyze.getNotBeforeSport();
		List <String> unrecommended = analyze.getUnrecommendedListOfFood(user_id);
		String [] unrecommendedAfterSport = analyze.getUnrecommendedFood();
		String [] foodAfterSportrecommendation = analyze.getRecommendation(user_id,true);



		session.setAttribute("recommendation", recommendation);
		session.setAttribute("sportRecommendation", sportRecommendation);
		session.setAttribute("heartRrate", heartRrate);
		session.setAttribute("sportBefore", sportBefore);
		session.setAttribute("notBeforeSport", notBeforeSport);
		session.setAttribute("unrecommendedAfterSport", unrecommendedAfterSport);
		session.setAttribute("unrecommended", unrecommended);
		session.setAttribute("foodAfterSportrecommendation", foodAfterSportrecommendation);

		RequestDispatcher rqDisp = request.getRequestDispatcher("recommendation.jsp");
		rqDisp.forward(request, response);
	}
	public void getAllTests(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		List<List<ComponentResult>> allResults = new ArrayList<>();
		List<List<String>> deltaResults = new ArrayList<>();
		try {
			allResults = compUtils.getUserAllTestResults(user_id);
			deltaResults = caluclateDelta(allResults);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//add user to request object 
		request.setAttribute("allResults", allResults);
		List <String> labels =  getLabels(allResults);
		request.setAttribute("comp_labels", labels);

		request.setAttribute("deltaResults", deltaResults);
		request.setAttribute("bmiWeight", compUtils.getWeightBmi(user_id));
		//return to homepage log in
		RequestDispatcher rqDisp = request.getRequestDispatcher("tracking.jsp");
		rqDisp.forward(request, response);
	}
	private List<List<String>> caluclateDelta (List<List<ComponentResult>> allResults){

		List <String> delta = new ArrayList<>();
		List <List<String>> allDelta = new ArrayList<>();
		ComponentResult [] firstRes = null,secondRes=null;
		double difference = 0;
		int index = 0;
		for (List<ComponentResult> allRes: allResults){
			delta = new ArrayList<>();
			if (index < allResults.size() - 1){
				firstRes = allResults.get(index).toArray(new ComponentResult[allRes.size()]);
				secondRes = allResults.get(index+1).toArray(new ComponentResult[allRes.size()]);

				for (int i=0; i<firstRes.length; i++){
					if (secondRes[i].getName()!="date"){
						difference = secondRes[i].getValue() -  firstRes[i].getValue();
						DecimalFormat df = new DecimalFormat("#.00");
						String dif = df.format(difference);
						difference = Double.parseDouble(dif);
						if (difference>0)
							delta.add("+" + String.valueOf(difference));
						else 
							delta.add(String.valueOf(difference));
					}
				}
				allDelta.add(delta);
				index++;
			}
			else {
				for (int i=0; i<firstRes.length; i++){
					delta.add("-");
				}
				allDelta.add(delta);
			}
		}		
		return allDelta;
	}
	public List<String> getLabels(List<List<ComponentResult>> allResults) {
		List <String> labels = new ArrayList<>();
		ComponentResult [] firstRes=null;
		for (List<ComponentResult> allRes: allResults){
			firstRes = allResults.get(0).toArray(new ComponentResult[allRes.size()]);
			for (int i=1;i<firstRes.length;i++){
				labels.add(firstRes[i].getName());
			}
			break;
		}
		return labels;
	}
}
