package com.conexio.automation.homepage;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertThat;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.TimeoutException;

import com.automation.pageobjects.homepage.HomepageObjects;
import com.automation.pageobjects.homepage.ImageComponent;
import com.cognifide.qa.bb.aem.AemLogin;
import com.cognifide.qa.bb.aem.dialog.classic.field.AemTextArea;
import com.cognifide.qa.bb.aem.dialog.classic.field.AemTextField;
import com.cognifide.qa.bb.aem.dialog.classic.field.lookup.AemLookupField;
import com.cognifide.qa.bb.aem.dialog.classic.field.lookup.AemPathWindow;
import com.cognifide.qa.bb.aem.ui.AemContentFinder;
import com.cognifide.qa.bb.aem.ui.parsys.AemParsys;
import com.cognifide.qa.bb.aem.ui.wcm.SiteAdminPage;
import com.cognifide.qa.bb.constants.Timeouts;
import com.cognifide.qa.bb.junit.Modules;
import com.cognifide.qa.bb.junit.TestRunner;
import com.cognifide.qa.bb.provider.selenium.BobcatWait;
import com.conexio.automation.GuiceModule;
import com.google.inject.Inject;

@RunWith(TestRunner.class)
@Modules(GuiceModule.class)
public class ImageComponentTest {

	private final String BASE_URL = "/content/conexio-internal-project/en/";
	
	@Inject
	private AemLogin aemLogin;
	
	@Inject
	private SiteAdminPage siteAdminPage;
	
	@Inject
	private HomepageObjects homepage;
	
	@Inject
	private AemContentFinder contentFinder;
	
	@Inject
	private BobcatWait wait;
	
	private ImageComponent image;
	
	private AemParsys parsys;
	
	@Before
	public void setup() {
		
		// 1. AEM Login
		aemLogin.authorLogin();
		siteAdminPage.open(BASE_URL);
		
		//2. Creates page if doesn't exists
		createPageIfDoesntExists();	
		
		//3. Redirect to HomepageObjects specified path URL
		homepage.open();
		
		//4. Inserts component if doesn't exists
		insertComponentIfDoesntExists();	
		
	}
	
	@Test
	public void insertImage() {
		
		//1. Opens page
		homepage.open();
		//assertTrue(homepage.isDisplayed());
					
		//2. Insert image
		image = homepage.getImageComponent();
		insertImageByName("anag.jpg");
		
		//3. Verify image was inserted 
	
	}
	

	@Ignore
	public void verifiesImageInformation() {
		
		// Abrir diálogo
		image.getDialog().open();
		image.getDialog().clickTab("Advanced");	
		
		// Content Tree
		AemLookupField lookup = image.getLinkToField();	
		
		wait.withTimeout(Timeouts.SMALL).until(input -> {
			
			AemPathWindow pathWindow = lookup.openPathWindow();
			pathWindow.getContentTree().selectPath("Community Sites");
			return pathWindow.clickOk();
				
		});
		
		// Title
		AemTextField title = image.getTitleField();
		title.setValue("This is a title");
		
		// Description
		AemTextArea description = image.getDescriptionField();
		description.setValue("Texto de prueba");
		
		// Size
		
		// Accessibility 
		image.getDialog().clickTab("Accessibility");
		
		// Alternative Text 
		AemTextField alternativeText = image.getAlternativeTextField();
		alternativeText.setValue("Imagen de Ana"); 

		
		// Cerrar diálogo
		image.getDialog().ok();
	}
	
	@After
	public void teardown() {
		
		BobcatWait.sleep(1);
	}
	
	public void createPageIfDoesntExists() {
		
		// Verifies if page exists
		if(!siteAdminPage.isPagePresent(homepage.getPageTitle())) {
			
			siteAdminPage.createNewPage(homepage.getPageTitle(),
					homepage.getPageName(), homepage.getPageTemplate());
		
			assertTrue(siteAdminPage.isPagePresent(homepage.getPageTitle())); 
			assertTrue(siteAdminPage.isTemplateOnTheList(homepage.getPageTitle(), homepage.getPageTemplate()));
			
		}	
	}
	
	public void insertComponentIfDoesntExists() {
		
		parsys = homepage.getParsys().clear();
		
		try {
			
			image = parsys.getFirstComponentOfType(ImageComponent.class);
			
		} catch (IndexOutOfBoundsException ex) {
			
			image = parsys.insertComponent(ImageComponent.class);
			image = parsys.getFirstComponentOfType(ImageComponent.class);
				
		} finally {
			
			assertThat("Didn't find specified parsys", parsys, CoreMatchers.notNullValue());
			assertThat("Didn't find Image Component", image, CoreMatchers.notNullValue());
			
		}
	}
	
	public void insertImageByName(String imageName) {
		
		contentFinder.clickTab("Images");
		List <String> content = new ArrayList<String>();
		content = contentFinder.getResults();
		
		if(content.size() > 0) {
			
			image.getDialog().open();
			image.insertImage(contentFinder.getElementByName(imageName));
			
			try {
				
				image.getDialog().ok();
				
			} catch (TimeoutException e) {
				
				
			} 
					
		}
	}
	
}
