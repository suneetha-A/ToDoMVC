package ToDoAssessment;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Listeners;



@Listeners(ListenersTodo.class)



public class TodoTestCases {
	WebDriver driver;
	TodoRepository todoRepository;
	
	@BeforeMethod
    public void setUp() {
       
        driver = new ChromeDriver();
        driver.get("https://todomvc.com/examples/react/dist/");
         todoRepository = new TodoRepository(driver);
        
    }

	@AfterMethod
    public void tearDown() {
        driver.quit();
    }

   @Test
    public void emptyTodo() {
        //WebElement todoTask = driver.findElement(By.id("todo-input"));
	   todoRepository.todoTask.sendKeys(Keys.ENTER);
       
        // Verify the todo list with out adding Task
                
        boolean elementExists = driver.findElements(By.cssSelector(".todo-count")).size() > 0;

        // Assert that the element is not present on the page
        Assert.assertFalse(elementExists, "Element found when it should not be present!");
          
    
    }
    
    @Test
    public void addTodo() {
    	
    	todoRepository.addTodoElements();   
    	
        List<WebElement> todoList = driver.findElements(By.cssSelector(".todo-list li"));
    	// Then check the size of the Todo list
    	Assert.assertEquals(todoList.size(), 2);
    	
        // Verify that the new todo appears in the list
         Assert.assertEquals(driver.findElement(By.xpath("(//span[@class='todo-count'])[1]")).getText(), "2 items left!");
         //The input field should be cleared after adding the task
         Assert.assertTrue(todoRepository.todoTask.getAttribute("value").isEmpty());
         
         //verify the Entered Task with added Task in the TOdo list
         
         

    }
    
    @Test
    public void VerifyDestroyButton() {
    	 todoRepository.addTodoElements();   
         
    	 Actions a = new Actions(driver);
    	 a.moveToElement(driver.findElement(By.cssSelector(".toggle"))).click().perform();
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         WebElement deleteFilter = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".destroy")));
                  
         deleteFilter.click();
            

        // Verify that the new todo appears in the list
         Assert.assertEquals(driver.findElement(By.xpath("(//span[@class='todo-count'])[1]")).getText(), "1 item left!");

    }
    
    @Test
    public void verifyCompleted() {
    	 	 
    	todoRepository.addTodoElements();
    	driver.findElement(By.xpath("//li[1]//div[1]//input[1]")).click();
    	//Check the Todo list weather or not item selected
    	//System.out.println(driver.findElement(By.xpath("//li[1]")).getAttribute("class").contains("completed"));
    	Assert.assertTrue(driver.findElement(By.xpath("//li[1]")).getAttribute("class").contains("completed"), "Todo should be marked as completed.");
    	
    	//To check Completed Tag
    	
    	driver.findElement(By.linkText("Completed")).click();
    	//Check the Todo list weather or not item selected
    	Assert.assertTrue(driver.findElement(By.cssSelector("li[class='completed'] input[type='checkbox']")).isSelected());
    	//Check the todo-count message
    	Assert.assertEquals(driver.findElement(By.xpath("(//span[@class='todo-count'])[1]")).getText(), "1 item left!");
    	
    
    }
       
    @Test
    
    public void verifyActive()
    {
    	todoRepository.addTodoElements();
         
         //Click the Checkbox beside of Task(to make task completed)
    	todoRepository.clickCheckBox();
  	
   
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //collect the active tasks before clicking Active link
        driver.findElement(By.linkText("All")).click();
        List<WebElement> todoActiveElementsofMainList = driver.findElements(By.cssSelector(".todo-list li:not(.completed)"));
       
       
        driver.findElement(By.linkText("Active")).click();
        //Check the todo-count message
        Assert.assertEquals(driver.findElement(By.xpath("(//span[@class='todo-count'])[1]")).getText(), "1 item left!");
        List<WebElement> todoActiveElementsofActiveLink = driver.findElements(By.cssSelector(".todo-list li" ));
        
        //Compart both list sizes
        Assert.assertEquals(todoActiveElementsofMainList, todoActiveElementsofActiveLink, "Both lists are equal.");
       
    }
 
    @Test
    public void verifyMainPageAfterClearCompleted() {
    	todoRepository.addTodoElements();
    	todoRepository.clickCheckBox();
    	
    	driver.findElement(By.className("clear-completed")).click();
    	//Check the todo list (check for unchecked boxes)
    	
    	Assert.assertFalse(driver.findElement(By.xpath("//li[1]")).getAttribute("class").contains("completed"), "Element deleted from Todo list.");
    	
    
    }
    
    @Test
    public void verifyCompletedPageAfterClearCompleted() {
    	todoRepository.addTodoElements();

    	todoRepository.clickCheckBox();
    	    	
    	driver.findElement(By.className("clear-completed")).click();
    	//Check the todo list (after clicking Completed )
    	
    	driver.findElement(By.linkText("Completed")).click();
    	List<WebElement> todoList = driver.findElements(By.cssSelector(".todo-list li"));
    	
    	Assert.assertEquals(todoList.size(), 0, "todo list is empty in Completed page");
    	
      
    }
    
    

    @Test
    public void verifySelectDownArrowButton() {
    	todoRepository.addTodoElements();
    	//Press downArrow button in text field
    	driver.findElement(By.className("toggle-all")).click();
    	//Check the todo-count message 	
    	Assert.assertEquals(driver.findElement(By.xpath("(//span[@class='todo-count'])[1]")).getText(), "0 items left!");
    	
    
    }
    
    
    @Test
    public void verifySelectDownArrowButtonTwotimes() {
    	todoRepository.addTodoElements();
    	
    	//select and unselect items
    	driver.findElement(By.className("toggle-all")).click();
    	driver.findElement(By.className("toggle-all")).click();
    	//Check the todo-count message 	    	
    	Assert.assertEquals(driver.findElement(By.xpath("(//span[@class='todo-count'])[1]")).getText(), "2 items left!", "All items Tallyed! ");
    	
    
    }
    
	@Test
    public void verifyAll() {
		todoRepository.addTodoElements();
         
         int beforesize = driver.findElements(By.cssSelector(".todo-list li")).size();
         
         driver.findElement(By.linkText("Completed")).click();
         driver.findElement(By.linkText("All")).click();
         
         int aftersize = driver.findElements(By.cssSelector(".todo-list li")).size();
         
         Assert.assertEquals(beforesize, aftersize);


         
    }
    @Test
    public void verifyEditInput() {
    	
    	String testTask = "testTask";
      	 
    	// Create an Actions object
         Actions actions = new Actions(driver);

         // Send text to the text box and press Enter
         actions.sendKeys(todoRepository.todoTask, "Test").doubleClick().sendKeys(testTask).sendKeys(Keys.ENTER).perform();
         Assert.assertEquals(driver.findElement(By.className("view")).getText(),testTask, "Successfull");
         
    }
    
    
    @Test
    public void verifyEnteredTaskwithList() {
    	
    	String str = "VerifyTask";
        //WebElement todoTask = driver.findElement(By.id("todo-input"));
	   Actions actions = new Actions(driver);
	   actions.sendKeys(todoRepository.todoTask,str).sendKeys(Keys.ENTER).perform();
        // Verify the todo list with out adding Task
                
	   WebElement task= driver.findElement(By.xpath("//ul[@class='todo-list']//li[1]//label"));

        // Assert that the element is not present on the page
        Assert.assertEquals(task.getText(), str);
          
    
    }
    
    
    @Test
    public void persistToDos() {
        // Assuming todoRepository.addTodoElements() works fine for adding todos
        todoRepository.addTodoElements();  // Add todos to the list

        // Find the size of the todo list before page refresh
        int beforeSize = driver.findElements(By.cssSelector(".todo-list li")).size();

        // Refresh the page
        driver.navigate().refresh();

        // Find the size of the todo list after page refresh
        int afterSize = driver.findElements(By.cssSelector(".todo-list li")).size();

        
        // Compare before and after sizes of the todo list
        Assert.assertEquals(beforeSize, afterSize, "Todos list not persisted after refresh");
        
    }
    
    @Test
    public void editExistingTodo() {
    	
    	todoRepository.addTodoElements();
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement todoItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='todo-list']//li[1]//label")));

        //Double-click on the todo item to trigger the edit mode
        Actions actions = new Actions(driver);
        actions.doubleClick(todoItem).perform();

        //Wait for the input field that becomes editable after double-click
        WebElement editInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='todo-list']//li[1]//input")));

        //Use JavaScript to clear the input field (if clear() does not work)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '';", editInput);  // Clears the input using JavaScript

        //Modify the todo text (e.g., appending "_edited")
        editInput.sendKeys("Updated Todo Text");

        //Save the changes by pressing "Enter"
        editInput.sendKeys(Keys.ENTER);

        //verify the todo item was updated
        WebElement updatedTodo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='todo-list']//li[1]//label")));
        String updatedText = updatedTodo.getText();

        Assert.assertEquals(updatedText, "Updated Todo Text");
    } 
    
    

}
