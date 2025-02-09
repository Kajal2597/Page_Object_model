package pom.utils;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pom.base.TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil extends TestBase {

    public static long PAGE_LOAD_TIMEOUT = 20;
    public static long IMPLICIT_WAIT = 20;
    public static String TESTDATA_SHEET_PATH ="C:\\Users\\prasa\\IdeaProjects\\Page_Object_Model\\src\\LoginDetails.xlsx";
    static Workbook book;
    static Sheet sheet;
    static JavascriptExecutor js;
    public static Object[][] getTestData(String sheetName) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(TESTDATA_SHEET_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            book = WorkbookFactory.create(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = book.getSheet(sheetName);
        Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
        // System.out.println(sheet.getLastRowNum() + "--------" +
        // sheet.getRow(0).getLastCellNum());
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
                data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
                // System.out.println(data[i][k]);
            }
        }
        return data;
    }


    public static void takeScreenshotAtEndOfTest() throws IOException {
        // Capture the screenshot
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Define the directory path for screenshots
        String currentDir = System.getProperty("user.dir");
        String screenshotDir = currentDir + "/screenshots";

        // Check if the 'screenshots' folder exists, if not, create it
        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Save the screenshot with a timestamp
        String screenshotPath = screenshotDir + "/" + System.currentTimeMillis() + ".png";
        FileUtils.copyFile(scrFile, new File(screenshotPath));

        // Create a meaningful screenshot file name
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String screenshotName = testName + "_" + timestamp + ".png";

        System.out.println("Screenshot saved at: " + screenshotPath);
    }
}