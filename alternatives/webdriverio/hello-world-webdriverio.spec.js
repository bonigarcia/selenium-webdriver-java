describe('Hello World with WebDriverIO', () => {
   it('Login in the practice site', async () => {
      await browser.url(`https://bonigarcia.dev/selenium-webdriver-java/login-form.html`);

      await $('#username').setValue('user');
      await $('#password').setValue('user');
      await $('button[type="submit"]').click();
      await expect($('#success')).toHaveTextContaining('Login successful');
	  
      await browser.saveScreenshot('hello-world-webdriverio.png');
    });
});
