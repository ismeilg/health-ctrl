package com.healthctrl.servlets;

import java.io.IOException;
import java.util.ArrayList;
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
import com.healthctrl.objects.ComponentDetails;
import com.healthctrl.objects.ComponentResult;
import com.healthctrl.objects.TestComponents;
import com.healthctrl.objects.UserMoreDetails;
import com.healthctrl.utils.AnalyzeUtils;
import com.healthctrl.utils.ComponentsUtils;

/**
 * Servlet implementation class BloodTestServlet
 */
@WebServlet("/BloodTestServlet")
public class BloodTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ComponentsUtils componentUtils;
	HttpSession session ;
	List <ComponentResult> lastResults = new ArrayList<>();

	@Resource(name="jdbc/sadna")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		//create testblood db util..and pass the conn pool / data source
		try{
			componentUtils = new ComponentsUtils(dataSource);
		}
		catch (Exception e){
			throw new ServletException(e);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		//get the data and set the attribute
		AnalyzeUtils analyze = new AnalyzeUtils(dataSource);
		try {
			//read command param
			String theCommand = request.getParameter("command");
			if(theCommand == null){
				theCommand = "LIST";		
			}
			//route it to appropiate method
			switch (theCommand){
			case "LIST":
				//list the components result in MVC
				listTestComponents(request, response);
				break;
			case "ADD":
				addTestResult(request, response);
				listTestComponents(request, response);
				break;
			case "recommendation":
				session = request.getSession();
				RequestDispatcher rqDisp;
				String user_id = (String)session.getAttribute("user_id");
				String [] recommendation = analyze.getRecommendation(user_id,false);
				String [] foodAfterSportrecommendation = analyze.getRecommendation(user_id,true);

				
				//need to make place in html where to write
				String sportRecommendation = analyze.getSportRecommendation(user_id);
				int heartRrate = analyze.getrecommendedHeartRate(user_id);
				String [] sportBefore = analyze.getBeforeSport(user_id);
				String [] notBeforeSport = analyze.getNotBeforeSport();
				//need to filter only 5 - random
				List <String> unrecommended = analyze.getUnrecommendedListOfFood(user_id);
				String [] unrecommendedAfterSport = analyze.getUnrecommendedFood();

				

				HttpSession session = request.getSession();
				session.setAttribute("recommendation", recommendation);
				session.setAttribute("foodAfterSportrecommendation", foodAfterSportrecommendation);

				session.setAttribute("sportRecommendation", sportRecommendation);
				session.setAttribute("heartRrate", heartRrate);
				session.setAttribute("sportBefore", sportBefore);
				session.setAttribute("notBeforeSport", notBeforeSport);
				session.setAttribute("unrecommended", unrecommended);
				session.setAttribute("unrecommendedAfterSport", unrecommendedAfterSport);


				rqDisp = request.getRequestDispatcher("recommendation.jsp");
				rqDisp.forward(request, response);
				break;
			} 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ServletException(e);
		}
	}

	private void addTestResult(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			session = request.getSession();
			String user_id = (String)session.getAttribute("user_id");

			//read from the form
			
			String gclucose = request.getParameter("glucose");
			if (gclucose.equals(""))
				gclucose = "0";
			String potassium = request.getParameter("potassium");
			if (potassium.equals(""))
				potassium = "0";
			String sodium = request.getParameter("sodium");
			if (sodium.equals(""))
				sodium = "0";
			String albumin = request.getParameter("albumin");
			if (albumin.equals(""))
				albumin = "0";
			String calcium = request.getParameter("calcium");
			if (calcium.equals(""))
				calcium = "0";
			String triglycerides = request.getParameter("triglycerides");
			if (triglycerides.equals(""))
				triglycerides = "0";
			String hdl = request.getParameter("hdl");
			if (hdl.equals(""))
				hdl = "0";
			String ldl = request.getParameter("ldl");
			if (ldl.equals(""))
				ldl = "0";
			String cholestrol = request.getParameter("cholestrol");
			if (cholestrol.equals(""))
				cholestrol = "0";
			String b12 = request.getParameter("b12");
			if (b12.equals(""))
				b12 = "0";
			String protein_total = request.getParameter("protein_total");
			if (protein_total.equals(""))
				protein_total = "0";
			String height = request.getParameter("height");
			String weight = request.getParameter("weight");
			float bmi = (float)Integer.parseInt(weight)/(((float)Integer.parseInt(height)/100)*((float)Integer.parseInt(height)/100));

			String[] checkBoxSensitive = request.getParameterValues("sensitive");
			String gluten ="0", sugar="0", lactose="0";
			if(checkBoxSensitive != null){
				for (int i = 0; i < checkBoxSensitive.length; i++) {
					if (checkBoxSensitive[i].equals("gluten")){
						gluten ="1";
					}
					else if (checkBoxSensitive[i].equals("sugar")){
						sugar ="1";
					}
					else if (checkBoxSensitive[i].equals("lactose")){
						lactose ="1";
					}   
				}
			}
			String[] checkBoxPreferences = request.getParameterValues("prefer");
			String vegan = "0", vegiterian="0";
			if(checkBoxPreferences != null){
				for (int i = 0; i < checkBoxPreferences.length; i++) {
					if (checkBoxPreferences[i].equals("vegan")){
						vegan ="1";
					}
					else if (checkBoxPreferences[i].equals("vegiterian")){
						vegiterian ="1";
					}  
				}
			}

			UserMoreDetails userDet = new UserMoreDetails(gluten, sugar, lactose, vegiterian, vegan, (float)Integer.parseInt(weight), Integer.parseInt(height));

			TestComponents results = new TestComponents(Float.parseFloat(gclucose),Float.parseFloat(sodium),Float.parseFloat(potassium),
					Float.parseFloat(albumin),Float.parseFloat(calcium),Float.parseFloat(triglycerides),
					Float.parseFloat(hdl),Float.parseFloat(ldl),Float.parseFloat(cholestrol),
					Float.parseFloat(b12),Float.parseFloat(protein_total),bmi);

			lastResults = componentUtils.addTestResult(results,user_id,userDet);

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		//create new blood result object

		//add it to db

		//send back to main page

	}

	private void listTestComponents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		///get test components by ID
		String user_id = (String)session.getAttribute("user_id");
		float bmi=0;
		//add test components to request
		//request.setAttribute("results", results);
		for (ComponentResult res : lastResults){
			if (res.getName().equals("bmi")){
				request.setAttribute("bmi",res.getValue());
				bmi = res.getValue();
			}
		}
		
		String image = componentUtils.getBmiImage(bmi);
		
		request.setAttribute("bmi_img", image);
		List <ComponentDetails> components = componentUtils.getComponentDetails();
		
		///from list to array
		ComponentDetails [] comps = components.toArray(new ComponentDetails[components.size()]);
		ComponentResult [] res = lastResults.toArray(new ComponentResult[lastResults.size()]);
		float avalue;
		for (int i=0; i<res.length-1; i++){
			for (int j=0; j<comps.length; j++){
				if (res[i].getName().equals(comps[j].getType())){
					comps[j].setValue(res[i].getValue());
					avalue = (200*(comps[j].getValue() - comps[j].getAmin()))/(comps[j].getAmax() - comps[j].getAmin());
					comps[j].setAvalue(avalue);
					break;
				}
			}
		}
		List <ComponentDetails> allComponents = new ArrayList<>();
		for (ComponentDetails tmp : components){
			if (tmp.getValue()!=0){
				allComponents.add(tmp);
			}
		}
		//send JSO page (view)
		request.setAttribute("results", allComponents);
		RequestDispatcher dispatcher = request.getRequestDispatcher("test-result.jsp");
		dispatcher.forward(request, response);
	}
} 
