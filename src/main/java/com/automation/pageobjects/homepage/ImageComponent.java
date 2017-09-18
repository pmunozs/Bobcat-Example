package com.automation.pageobjects.homepage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.cognifide.qa.bb.aem.dialog.classic.field.AemTextArea;
import com.cognifide.qa.bb.aem.dialog.classic.field.AemTextField;
import com.cognifide.qa.bb.aem.dialog.classic.field.image.AemImage;
import com.cognifide.qa.bb.aem.dialog.classic.field.lookup.AemLookupField;
import com.cognifide.qa.bb.aem.qualifier.DialogField;
import com.cognifide.qa.bb.aem.ui.AemDialog;
import com.cognifide.qa.bb.aem.ui.component.AemComponent;
import com.cognifide.qa.bb.dragdrop.Draggable;
import com.cognifide.qa.bb.qualifier.PageObject;
import com.google.inject.Inject;

@PageObject
@AemComponent(cssClassName = "image", group = "Conexio Group", name = "Image")
public class ImageComponent {

	@Inject
	private AemDialog dialog;
	
	@DialogField(label = "Title")
	@CacheLookup
	private AemTextField titleField;
	
	@DialogField(label = "Link to")
	@CacheLookup
	private AemLookupField linkToField;
	
	@DialogField(label = "Description")
	@CacheLookup
	private AemTextArea descriptionField;
	
	@DialogField(label = "Size")
	@CacheLookup
	private AemTextField sizeField;
	
	@DialogField(label = "Alternative Text")
	@CacheLookup
	private AemTextField alternativeTextField;
	
	@DialogField(css = ".cq-smartfile-_46_47file")
	@CacheLookup
	private AemImage image;
	
	@FindBy(xpath = "//div[contains(@class, 'cq-dialog-par_47image')]/*")
	@CacheLookup
	private WebElement img;
	
	/* -- Gets -- */
	public AemDialog getDialog() {
		
		return dialog;
	}
	
	public AemImage getImage() {
		
		return image;
	}
	
	public WebElement getImageContent() {
		
		return img;
	}
	
	public AemTextField getTitleField() {
		
		return titleField;
	}
	
	public AemLookupField getLinkToField() {
		
		return linkToField;
	}
	
	public AemTextArea getDescriptionField() {
		
		return descriptionField;
	}
	
	public AemTextField getAlternativeTextField() {
		
		return alternativeTextField;
	}

	public void insertImage(Draggable contentFinderImage) {
		
		image.insert(contentFinderImage);
		
	}
	
	public void getImagePath(String attribute) {
		
		img.getAttribute(attribute);
	}
	
	
	
}
