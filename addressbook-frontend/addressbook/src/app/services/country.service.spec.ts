import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpErrorResponse } from '@angular/common/http';

import { CountryService } from './country.service';
import { Country } from '../domain/country';

describe('CountryService', () => {
  let service: CountryService;
  let httpTestingController: HttpTestingController;
  const countryUrl = 'addressbook/v1/country';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CountryService]
    });

    service = TestBed.inject(CountryService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Verify that no unmatched requests are outstanding
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('allCountries', () => {
    it('should return all countries', () => {
      let mockCountries: Country[];
      mockCountries = [
        {id: 1, name: 'Belgium', countryCode: 'BE', phoneCode: '0032'},
        {id: 2, name: 'Netherlands', countryCode: 'NL', phoneCode: '0031'}
      ];

      // Access the signal to trigger the HTTP request
      const countries = service.allCountries();

      // Initially, the signal should have an empty array
      expect(countries).toEqual([]);

      // Respond to the HTTP request
      const req = httpTestingController.expectOne(`${countryUrl}/all`);
      expect(req.request.method).toEqual('GET');
      req.flush(mockCountries);

      // Now the signal should have the mock data
      expect(service.allCountries()).toEqual(mockCountries);
    });

    it('should retry failed requests up to 2 times', () => {
      // Access the signal to trigger the HTTP request
      service.allCountries();

      // Respond with an error to the first request
      let req = httpTestingController.expectOne(`${countryUrl}/all`);
      req.flush('Error', { status: 500, statusText: 'Server Error' });

      // It should retry, so expect another request
      req = httpTestingController.expectOne(`${countryUrl}/all`);
      req.flush('Error', { status: 500, statusText: 'Server Error' });

      // It should retry one more time
      req = httpTestingController.expectOne(`${countryUrl}/all`);
      req.flush('Error', { status: 500, statusText: 'Server Error' });

      // After 3 failures (original + 2 retries), it should stop retrying
      // and the signal should still have the initial empty array
      expect(service.allCountries()).toEqual([]);
    });

    it('should handle HTTP errors', () => {
      // Spy on console.error
      spyOn(console, 'error');

      // Access the signal to trigger the HTTP request
      service.allCountries();

      // Respond with an error
      const req = httpTestingController.expectOne(`${countryUrl}/all`);
      const errorResponse = new HttpErrorResponse({
        error: 'test 404 error',
        status: 404,
        statusText: 'Not Found'
      });
      req.flush('Error', errorResponse);

      // Console.error should have been called with an error message
      expect(console.error).toHaveBeenCalled();

      // The signal should still have the initial empty array
      expect(service.allCountries()).toEqual([]);
    });
  });
});
