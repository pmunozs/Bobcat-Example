package com.conexio.automation.homepage;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.TimeoutException;

import com.automation.pageobjects.homepage.HomepageObjects;
import com.automation.pageobjects.homepage.TextComponent;
import com.cognifide.qa.bb.aem.AemLogin;
import com.cognifide.qa.bb.aem.dialog.classic.field.RtButton;
import com.cognifide.qa.bb.aem.ui.parsys.AemParsys;
import com.cognifide.qa.bb.aem.ui.wcm.SiteAdminPage;
import com.cognifide.qa.bb.junit.Modules;
import com.cognifide.qa.bb.junit.TestRunner;
import com.cognifide.qa.bb.provider.selenium.BobcatWait;
import com.conexio.automation.GuiceModule;
import com.google.inject.Inject;

@RunWith(TestRunner.class)
@Modules(GuiceModule.class)
public class TextComponentTest {

	private final String BASE_URL = "/content/conexio-internal-project/en/";
	
	@Inject 
	private AemLogin aemLogin;
	
	@Inject
	private SiteAdminPage siteAdminPage;
	
	@Inject
	private HomepageObjects homepage;
	
	private TextComponent text;
	
	private AemParsys parsys;
	
	private String testText = "Magna adipiscing per est mi adipiscing eget massa"
			+ " quis a hendrerit a a ullamcorper semper lacus. Ultricies a parturient "
			+ "euismod et tristique litora integer a vehicula dui eros augue quisque"
			+ " gravida fermentum litora ad vestibulum condimentum adipiscing quis a ut "
			+ "convallis sit vehicula himenaeos. Dolor id sed dapibus ornare at a inceptos "
			+ "adipiscing pharetra sed ac in et vivamus consectetur scelerisque curabitur "
			+ "lectus suscipit suspendisse eros risus metus a commodo maecenas. A conubia "
			+ "vulputate etiam mi adipiscing parturient fringilla eleifend fames bibendum "
			+ "fringilla turpis adipiscing ipsum ligula. Quam habitant ut tempus ullamcorper"
			+ " a nibh elit nisl potenti convallis a parturient a amet dictumst ridiculus "
			+ "a viverra ac fames primis tincidunt magna.";
	
	@Before
	public void setup() {
		
		// 1. AEM Login
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
	public void verifyRichTextFieldWorks() {
		
		// 1. Opens dialog
		text.getDialog().open();
		
		// 2. Sets rich text field
		text.getRchText().clear();
		text.getRchText().type(testText);
		
		// 3. Sets bold text
		text.getRchText().selectText(0, 5);
		text.getRchText().click(RtButton.BOLD);
		
		// 4. Sets italic text
		text.getRchText().selectText(6, 16);
		text.getRchText().click(RtButton.ITALIC);
		
		// 5. Sets underlined text
		text.getRchText().selectText(17, 20);
		text.getRchText().click(RtButton.UNDERLINE);
		
		// Press OK in dialog
		try {
			
			text.getDialog().ok();
			
		} catch (TimeoutException e) {
			
			assertThat("Diálogo continúa visible", text.getDialog().isVisible(), is(false));
			
		}
		
		// Asserts text styles
		assertTrue("Text or styles are different! (bold)",
				text.getRchText().getInnerHTML().contains("<b>Magna</b>"));
		
		assertTrue("Text or styles are different! (italic)",
				text.getRchText().getInnerHTML().contains("<i>adipiscing</i>"));
		
		assertTrue("Text or styles are different! (underlined)"
				, text.getRchText().getInnerHTML().contains("<u>per</u>"));
	}
	
	
	@After
	public void teardown() {
		
		// Waits 1 second before closing
		BobcatWait.sleep(1);
	}
	
	public void createPageIfDoesntExists() {
		
		if(!siteAdminPage.isPagePresent(homepage.getPageTitle())) {
			
			siteAdminPage.createNewPage(homepage.getPageTitle(),
					homepage.getPageName(), homepage.getPageTemplate());
		
			assertTrue(siteAdminPage.isPagePresent(homepage.getPageTitle())); 
			assertTrue(siteAdminPage.isTemplateOnTheList(homepage.getPageTitle(), homepage.getPageTemplate()));
			
		}	
		
	}
	
	public void insertComponentIfDoesntExists() {
		
		parsys = homepage.getParsys();
			
		try {
			
			// Gets existing title component
			text = parsys.getFirstComponentOfType(TextComponent.class);
			
		} catch (IndexOutOfBoundsException ex) {
			
			// If it doesn't exists, inserts it
			text = parsys.insertComponent(TextComponent.class);
			text = parsys.getFirstComponentOfType(TextComponent.class);
			
			
		} finally {
			
			// Verifies component not null
			assertThat("Error when trying to get parsys", parsys, CoreMatchers.notNullValue());
			assertThat("Error when trying to get title", text, CoreMatchers.notNullValue());
			
		}			
		
	}
	
}
