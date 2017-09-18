package com.automation.pageobjects.homepage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.cognifide.qa.bb.aem.dialog.classic.field.AemRichText;
import com.cognifide.qa.bb.aem.qualifier.DialogField;
import com.cognifide.qa.bb.aem.ui.AemDialog;
import com.cognifide.qa.bb.aem.ui.component.AemComponent;
import com.cognifide.qa.bb.qualifier.PageObject;
import com.google.inject.Inject;

@PageObject
@AemComponent(cssClassName = "text", group = "Conexio Group", name = "Text")
public class TextComponent {

	@Inject
	private AemDialog dialog;
	
	@DialogField(label = "no label")
	@CacheLookup
	private AemRichText rchText;
	
	@FindBy(xpath = "//div[contains(@class, 'text')]/*")
	@CacheLookup
	private WebElement rchTextParagraph;
	
	public AemDialog getDialog() {
		
		return dialog;
	}
	
	public AemRichText getRchText() {
		
		return rchText;
	}
	
}
