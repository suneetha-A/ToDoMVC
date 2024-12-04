package ToDoAssessment;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;

public class TodoRepository {
	
	Actions actions;
	
	@FindBy(id = "todo-input")
	WebElement todoTask;
	
	@FindBy(id = "todo-input")
	WebElement todoTask1;
	
	@FindBy(id = "todo-input")
	WebElement todoTask2;
	
	@FindBy(xpath = "//li[1]//div[1]//input[1]")
	WebElement checkBox;
	
	
	
	
	public TodoRepository(WebDriver driver) {
		// TODO Auto-generated constructor stub
		
		PageFactory.initElements(driver, this);
		actions = new Actions(driver);
	}
	
	public void addTodoElements() {
		
		//Adding Tasks
        // Send text to the text box and press Enter
        actions.sendKeys(todoTask1, "Task 1").sendKeys(Keys.ENTER).perform();
        actions.sendKeys(todoTask2, "Task 2").sendKeys(Keys.ENTER).perform();
		
	}
	
	
	public void clickCheckBox()
	{
		checkBox.click();
	}

}
