import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Person} from '../domain/person';
import {catchError, retry, shareReplay} from 'rxjs/operators';
import {toSignal} from '@angular/core/rxjs-interop';
import {Country} from '../domain/country';
import {GeneralService} from './general.service';

@Injectable({
  providedIn: 'root'
})
export class CountryService extends GeneralService {

  private countryUrl = 'addressbook/v1/country';

  private http = inject(HttpClient)

  constructor() {
    super();
  }

  private allCountries$ = this.http.get<Country[]>(`${this.countryUrl}/all`).pipe(
    retry(2),
    //tap(persons => console.log('Fetched persons', persons.length)),
    shareReplay(1),
    catchError(super.handleError)
  );

  allCountries = toSignal(this.allCountries$, {initialValue: [] as Country[]})
}
