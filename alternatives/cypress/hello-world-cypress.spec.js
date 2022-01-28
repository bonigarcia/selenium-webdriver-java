describe('Hello World with Cypress', () => {
   it('Login in the practice site', () => {
      cy.visit('https://bonigarcia.dev/selenium-webdriver-java/login-form.html')
	
      cy.get('#username').type('user')
      cy.get('#password').type('user')
      cy.contains('Submit').click()
      cy.contains('Login successful')
	  
      cy.screenshot("hello-world-cypress")
  })
})