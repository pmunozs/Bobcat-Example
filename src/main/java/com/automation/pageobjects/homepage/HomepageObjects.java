package com.automation.pageobjects.homepage;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.cognifide.qa.bb.aem.page.AuthorPage;
import com.cognifide.qa.bb.aem.ui.parsys.AemParsys;
import com.cognifide.qa.bb.qualifier.Frame;
import com.cognifide.qa.bb.qualifier.PageObject;

@PageObject
@Frame("$cq")
public class HomepageObjects extends AuthorPage {
	
	private final String URL = "/cf#/content/conexio-internal-project/en/test-automation-page.html";
	private final String PAGE_TITLE = "Automation";
	private final String PAGE_NAME = "test-automation-page";
	private final String PAGE_TEMPLATE = "conexio-internal-project Content Page";
	
	@FindBy(xpath = "//div[contains(@class, 'cq-placeholder-par_47_42')]/..")
	@CacheLookup
	private AemParsys parsys;
	
	@FindBy(xpath = "//div[contains(@class, 'title')]/.")
	@CacheLookup
	private TitleComponent titleComponent;
	
	@FindBy(xpath = "//div[contains(@class, 'text')]/.")
	@CacheLookup
	private TextComponent textComponent;
	
	@FindBy(xpath = "//div[contains(@class, 'image')]/*")
	@CacheLookup
	private ImageComponent imageComponent;
	
	/* -- Get components -- */
	public ImageComponent getImageComponent() {
		
		return imageComponent;
	}
	
	public TitleComponent getTitleComponent() {
		
		return titleComponent;
	}
	
	public TextComponent getTextComponent() {
		
		return textComponent;
	}
	
	/* -- Get parsys -- */
	public AemParsys getParsys() {
		
		return parsys;
	}
	
	@Override
	public String getContentPath() {
		
		return URL;
	}

	@Override
	public String getPageTitle() {
		
		return PAGE_TITLE;
	}
	
	public String getPageName() {
		
		return PAGE_NAME;
	}
	
	public String getPageTemplate() {
		
		return PAGE_TEMPLATE;
	}

}
