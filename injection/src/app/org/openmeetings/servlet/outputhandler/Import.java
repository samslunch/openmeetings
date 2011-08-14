package org.openmeetings.servlet.outputhandler;

import http.utils.multipartrequest.ServletMultipartRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmeetings.app.data.basic.AuthLevelmanagement;
import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.data.user.dao.UsersDaoImpl;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.openmeetings.app.xmlimport.LanguageImport;
import org.openmeetings.app.xmlimport.UserImport;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class Import extends HttpServlet {
	private static final long serialVersionUID = 582610358088411294L;
	private static final Logger log = Red5LoggerFactory.getLogger(Import.class, ScopeApplicationAdapter.webAppRootKey);
	@Autowired
	private Sessionmanagement sessionManagement;
    @Autowired
    private Usermanagement userManagement;
	@Autowired
	private UsersDaoImpl usersDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {

		try {
			String sid = httpServletRequest.getParameter("sid");
			if (sid == null) {
				sid = "default";
			}
			System.out.println("sid: " + sid);

			String moduleName = httpServletRequest.getParameter("moduleName");
			if (moduleName == null) {
				moduleName = "moduleName";
			}
			System.out.println("moduleName: " + moduleName);
			Long users_id = sessionManagement.checkSession(sid);
			Long user_level = userManagement.getUserLevelByID(
					users_id);
			
			String publicSID = httpServletRequest.getParameter("publicSID");
			if (publicSID == null) {
				//Always ask for Public SID
				return;
			}

			log.debug("users_id: " + users_id);
			log.debug("user_level: " + user_level);
			log.debug("moduleName: " + moduleName);

			// if (user_level!=null && user_level > 0) {
			if (AuthLevelmanagement.getInstance().checkAdminLevel(user_level)) {
				if (moduleName.equals("users")) {
					log.error("Import Users");
					String organisation = httpServletRequest.getParameter("secondid");
					if (organisation == null) {
						organisation = "0";
					}
					Long organisation_id = Long.valueOf(organisation).longValue();
					log.debug("organisation_id: " + organisation_id);

					ServletMultipartRequest upload = new ServletMultipartRequest(httpServletRequest, 104857600); // max100 mb
					InputStream is = upload.getFileContents("Filedata");
					
					UserImport.getInstance().addUsersByDocument(is);

				} else if (moduleName.equals("language")) {
					log.error("Import Language");
					String language = httpServletRequest.getParameter("secondid");
					if (language == null) {
						language = "0";
					}
					Long language_id = Long.valueOf(language).longValue();
					System.out.println("language_id: " + language_id);

					ServletMultipartRequest upload = new ServletMultipartRequest(httpServletRequest, 104857600); // max100 mb
					InputStream is = upload.getFileContents("Filedata");
					
					LanguageImport.getInstance().addLanguageByDocument(language_id, is);
					
				}
			} else {
				System.out.println("ERROR LangExport: not authorized FileDownload "+ (new Date()));
			}	
			
			log.debug("Return And Close");
			
			LinkedHashMap<String,Object> hs = new LinkedHashMap<String,Object>();
			hs.put("user", usersDao.getUser(users_id));
			hs.put("message", "library");
			hs.put("action", "import");
			
			log.debug("moduleName.equals(userprofile) ? "+moduleName);
			
			//if (!moduleName.equals("userprofile")) {
				log.debug("moduleName.equals(userprofile) ! ");
				
			ScopeApplicationAdapter.getInstance().sendMessageWithClientByPublicSID(hs,publicSID);
			
			return;
	
		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			er.printStackTrace();
		}
	}

}
