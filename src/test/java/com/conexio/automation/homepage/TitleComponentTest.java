package com.conexio.automation.homepage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.CoreMatchers;

import com.automation.pageobjects.homepage.HomepageObjects;
import com.automation.pageobjects.homepage.TitleComponent;
import com.automation.pageobjects.homepage.TitleComponent.Colors;
import com.automation.pageobjects.homepage.TitleComponent.Sizes;

import com.cognifide.qa.bb.aem.AemLogin;
import com.cognifide.qa.bb.aem.ui.AemDialog;
import com.cognifide.qa.bb.aem.ui.parsys.AemParsys;
import com.cognifide.qa.bb.aem.ui.wcm.SiteAdminPage;

import com.cognifide.qa.bb.junit.Modules;
import com.cognifide.qa.bb.junit.TestRunner;
import com.conexio.automation.GuiceModule;

import com.google.inject.Inject;

@RunWith(TestRunner.class)
@Modules(GuiceModule.class)
public class TitleComponentTest {
	
	private final String BASE_URL = "/content/conexio-internal-project/en/";
	private String headingText = "Hello, I'm a Demo";
	
	@Inject 
	private AemLogin aemLogin;
	
	@Inject
	private SiteAdminPage siteAdminPage;
	
	@Inject
	private HomepageObjects homepage;
	
	@Inject
	private WebDriver driver;
	
	private TitleComponent title;
	
	private AemParsys parsys;
	
	
	@Before
	public void setup() {
		
		//1. AEM Login
		aemLogin.authorLogin();
		siteAdminPage.open(BASE_URL);
	
		//2. Creates page if doesn't exists
		createPageIfDoesntExists();
		
		//3. Open test page
		homepage.open();
		
		//4. Inserts component if doesn't exists
		insertComponentIfDoesntExists();	
		
	}
	
	@Test
	public void verifiesDefaultTitleValue() {	
		
		// 1.Verifies that title equals page title
		assertThat(title.getTitleText().toLowerCase(), is(homepage.getPageTitle().toLowerCase()));
		
		// 2.  Verifies that title has default color
		assertThat(title.getTitleColor(), is(Colors.PRIMARY.getColor()));			
							
	}
	
	@Test
	public void verifiesHeadingStyles() {			
		
		for(Sizes s : Sizes.values()) {
			
			// 1. Set fields
			setHeaderAndSize("Header " + s.getLabel(), s.getLabel());
			
			// 2. Get title
			title = getExistingTitle();
			
			// 3. Verifies label, tag, size, color
			assertThat("Title text is different", title.getTitleText(), is("Header " + s.getLabel()));
			assertThat("Tags are different", title.getTitleTag(), is(s.getTag()));
			assertThat("Font size is different", title.getTitleSize(), is(s.getSize()));
			assertThat("Title color is different", title.getTitleColor(), is(Colors.PRIMARY.getColor()));
			
		}	
	}
	
	@Test
	public void verifiesHeadingFieldSetsProperly() {		

		// 1. Set title value and header size
		setHeaderAndSize(headingText, "H3");
		
		// 2. Get title
		title = getExistingTitle();
		
		// 3. Verifies values
		assertThat(title.getTitleText().toUpperCase(), is(headingText.toUpperCase()));
		assertThat(title.getTitleTag(), is(Sizes.H1.getTag()));	
		
	}
	
	@After
	public void teardown() {
		
		//BobcatWait.sleep(1);
		driver.quit();
	}
	
	
	/* -- Methods-- */
	public void setHeaderAndSize(String heading, String size) {
		
		AemDialog dialog = title.getDialog();
		
		try {
			
			dialog.open();
						
			title.getTitleField().setValue(heading);
			title.getSizeDropDown().selectByText(size);
			
			dialog.ok();
			
			
		} catch (TimeoutException e) {
			
			assertThat("Diálogo continúa visible", title.getDialog().isVisible(), is(false));
			
		}		
	}
	
	public TitleComponent getExistingTitle() {
		    
		title = parsys.getFirstComponentOfType(TitleComponent.class);		
		return title;
	}
	
	public void createPageIfDoesntExists() {
		
		if(!siteAdminPage.isPagePresent(homepage.getPageTitle())) {
			
			siteAdminPage.createNewPage(homepage.getPageTitle(),
					homepage.getPageName(), homepage.getPageTemplate());
			
			 assertTrue("Page is not present",
					siteAdminPage.isPagePresent(homepage.getPageTitle())); 
			
			assertTrue("Template is not present", 
					siteAdminPage.isTemplateOnTheList(homepage.getPageTitle(), homepage.getPageTemplate()));

		}			
	}
	
	public void insertComponentIfDoesntExists() {
		
		try {
			
			parsys = homepage.getParsys().clear();
			title = parsys.getFirstComponentOfType(TitleComponent.class);
			
		} catch (IndexOutOfBoundsException ex) {
			
			// If it doesn't exists, inserts it
			title = parsys.insertComponent(TitleComponent.class);
			title = parsys.getFirstComponentOfType(TitleComponent.class);
			
			
		} finally {
			
			// Verifies component not null
			assertThat("Error when trying to get parsys", parsys, CoreMatchers.notNullValue());
			assertThat("Error when trying to get title", title, CoreMatchers.notNullValue());
			
		}			
		
	}
}
