const puppeteer = require('puppeteer');

(async () => {
   const browser = await puppeteer.launch(); 
   const page = await browser.newPage();
   
   await page.goto('https://bonigarcia.dev/selenium-webdriver-java/login-form.html');
   await page.type('#username', 'user');
   await page.type('#password', 'user');
   await page.click('button[type="submit"]');
   await page.waitForXPath('//*[contains(text(), "Login successful")]');
   await page.screenshot({ path: 'helloword-puppeteer.png' });
   
   await browser.close();
})();