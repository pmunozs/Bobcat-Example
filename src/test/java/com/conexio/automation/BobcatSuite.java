package com.conexio.automation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.cognifide.qa.bb.junit.Modules;
import com.cognifide.qa.bb.junit.concurrent.ConcurrentSuite;

import com.conexio.automation.GuiceModule;

import com.conexio.automation.homepage.ImageComponentTest;
import com.conexio.automation.homepage.TextComponentTest;
import com.conexio.automation.homepage.TitleComponentTest;

@Modules(GuiceModule.class)
@RunWith(ConcurrentSuite.class)
@Suite.SuiteClasses({  TitleComponentTest.class,  TextComponentTest.class, ImageComponentTest.class, })
public class BobcatSuite {

}