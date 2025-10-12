// import { ComponentFixture, TestBed } from '@angular/core/testing';
// import { RouterTestingModule } from '@angular/router/testing';
// import { By } from '@angular/platform-browser';
// import { AppComponent } from './app.component';
// import { RouterOutlet, RouterLink } from '@angular/router';
// import { DebugElement, NO_ERRORS_SCHEMA } from '@angular/core';
//
// describe('AppComponent', () => {
//   let component: AppComponent;
//   let fixture: ComponentFixture<AppComponent>;
//   let debugElement: DebugElement;
//
//   beforeEach(async () => {
//     await TestBed.configureTestingModule({
//       imports: [
//         RouterTestingModule,
//         AppComponent
//       ],
//       schemas: [NO_ERRORS_SCHEMA] // Ignore unknown elements and attributes
//     })
//     .compileComponents();
//
//     fixture = TestBed.createComponent(AppComponent);
//     component = fixture.componentInstance;
//     debugElement = fixture.debugElement;
//     fixture.detectChanges();
//   });
//
//   it('should create the app', () => {
//     expect(component).toBeTruthy();
//   });
//
//   it('should have a navbar with title "Adresboek"', () => {
//     const navbarBrand = debugElement.query(By.css('.navbar-brand'));
//     expect(navbarBrand).toBeTruthy();
//     expect(navbarBrand.nativeElement.textContent).toContain('Adresboek');
//   });
//
//   it('should have a navigation link to "add"', () => {
//     const addLink = debugElement.query(By.css('.nav-link'));
//     expect(addLink).toBeTruthy();
//     expect(addLink.attributes['routerLink']).toBe('add');
//     expect(addLink.nativeElement.textContent).toContain('Nieuw contact');
//   });
//
//   it('should have a search form with input field', () => {
//     const searchForm = debugElement.query(By.css('form'));
//     expect(searchForm).toBeTruthy();
//
//     const searchInput = debugElement.query(By.css('input[type="search"]'));
//     expect(searchInput).toBeTruthy();
//     expect(searchInput.attributes['placeholder']).toBe('Zoek');
//   });
//
//   it('should have a router-outlet', () => {
//     const routerOutlet = debugElement.query(By.directive(RouterOutlet));
//     expect(routerOutlet).toBeTruthy();
//   });
//
//   it('should use RouterLink for navigation', () => {
//     const routerLinks = debugElement.queryAll(By.directive(RouterLink));
//     expect(routerLinks.length).toBeGreaterThan(0);
//
//     // Check that at least one RouterLink points to 'add'
//     const hasAddLink = routerLinks.some(de =>
//       de.injector.get(RouterLink).routerLink === 'add');
//     expect(hasAddLink).toBeTruthy();
//   });
// });
