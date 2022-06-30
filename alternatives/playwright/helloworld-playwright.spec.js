const { test, expect } = require('@playwright/test');

test('basic test', async ({ page }) => {
   await page.goto('https://bonigarcia.dev/selenium-webdriver-java/login-form.html');
  
   await page.type('#username', 'user');
   await page.type('#password', 'user');
   await page.click('button[type="submit"]');
   await page.waitForSelector('#success');
   await expect(page.locator('#success')).toHaveText('Login successful'); 
  
   await page.screenshot({ path: 'helloworld-playwright.png' });
});