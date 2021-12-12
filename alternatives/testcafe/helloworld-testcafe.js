import { Selector } from 'testcafe';

fixture`Hello World with TestCafe`
   .page`https://bonigarcia.dev/selenium-webdriver-java/login-form.html`;
test('Login in the practice site', async t => {
   await t
      .typeText('#username', 'user')
      .typeText('#password', 'user')
      .click('button[type="submit"]')
      .expect(Selector('#success').innerText).eql('Login successful')
      .takeScreenshot();
});
