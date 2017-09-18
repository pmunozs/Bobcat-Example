package com.automation.pageobjects.homepage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.cognifide.qa.bb.aem.dialog.classic.field.AemDropdown;
import com.cognifide.qa.bb.aem.dialog.classic.field.AemTextField;
import com.cognifide.qa.bb.aem.qualifier.DialogField;
import com.cognifide.qa.bb.aem.ui.AemDialog;
import com.cognifide.qa.bb.aem.ui.component.AemComponent;
import com.cognifide.qa.bb.qualifier.PageObject;
import com.google.inject.Inject;

@PageObject
@AemComponent(cssClassName = "title", group = "Conexio Group", name = "Title")
public class TitleComponent {
	
	public enum Colors {
		
		PRIMARY("rgba(0, 0, 0, 1)");
		
		private String color;
		
		private Colors(String color) {
			
			this.color = color;
		}
		
		public String getColor() {
			
			return this.color;
		}
	}
	
	public enum Sizes {
		
		H1("H1", "h1", "75px"), H2("H2", "h2", "35px"), H3("H3", "h3", "30px"), H4("H4", "h4", "20px");
		
		private String label;
		private String tag;
		private String size;
		
		private Sizes(String label, String tag, String size) {
			
			this.label = label;
			this.tag = tag;
			this.size = size;
			
		}
		
		public String getLabel() {
			
			return this.label;
		}
		
		public String getTag() {
			
			return this.tag;
		}
		
		public String getSize() {
			
			return this.size;
		}
	}
	
	@Inject
	private AemDialog dialog;
	
	@DialogField(label = "Title")
	@CacheLookup
	private AemTextField txtTitle;
	
	@DialogField(label = "Type / Size")
	@CacheLookup
	private AemDropdown drpTypeSize;
	
	@FindBy(xpath = "//div[contains(@class, 'title')]/*")
	@CacheLookup
	private WebElement titleText;
	
	
	/* -- Dialog get -- */
	public AemDialog getDialog() {
		
		return dialog;
	}
	
	/* -- Field get(s) -- */
	public AemTextField getTitleField() {
		
		return txtTitle;
	}
	
	public AemDropdown getSizeDropDown() {
		
		return drpTypeSize;	
	}
	
	/* -- Component content get -- */
	public String getTitleText() {
		
		return titleText.getText();
	}
	
	public String getTitleColor() {
		
		return titleText.getCssValue("color");
	}
	
	public String getTitleTag() {
		
		return titleText.getTagName();
	}
	
	public String getTitleSize() {
		
		return titleText.getCssValue("font-size");
	}
}
