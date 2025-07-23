import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, retry, shareReplay, tap} from 'rxjs/operators';
import {Person} from '../../domain/person';
import {toSignal} from '@angular/core/rxjs-interop';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private personsUrl = 'addressbook/v1/person';
  private cache$: Observable<Person[]> | null = null;

  private http = inject(HttpClient)

  //constructor(private http: HttpClient) { }

  /**
   * Handles HTTP errors
   * @param error The HTTP error response
   * @returns An observable that errors with user-friendly message
   */
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred';

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }

    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }

  private allPersons$ = this.http.get<Person[]>(`${this.personsUrl}/all`).pipe(
    retry(2),
    tap(persons => console.log('Fetched persons', persons.length)),
    shareReplay(1),
    catchError(this.handleError)
  );
  allPersons = toSignal(this.allPersons$, {initialValue: [] as Person[]})

  /**
   * Clears the persons cache
   */
  clearCache(): void {
    this.cache$ = null;
  }

  /**
   * Retrieves a person by ID
   * @param id The ID of the person to retrieve
   * @returns An observable of Person
   */
  findById(id: number): Observable<Person> {
    return this.http.get<Person>(`${this.personsUrl}/${id}`).pipe(
      retry(2),
      tap(person => console.log('Fetched person', person.id)),
      catchError(this.handleError)
    );
  }

  /**
   * Registers a new person
   * @param person The person to register
   * @returns An observable of the HTTP response
   */
  registerPerson(person: Person): Observable<void> {
    return this.http.post<void>(this.personsUrl, person).pipe(
      tap(() => {
        console.log('Person registered successfully');
        this.clearCache(); // Clear cache after adding a new person
      }),
      catchError(this.handleError)
    );
  }

  /**
   * Deletes a person by ID
   * @param id The ID of the person to delete
   * @returns An observable of the HTTP response
   */
  deletePerson(id: number): Observable<void> {
    return this.http.delete<void>(`${this.personsUrl}/${id}`).pipe(
      tap(() => {
        console.log(`Person with ID ${id} deleted successfully`);
        this.clearCache(); // Clear cache after deleting a person
      }),
      catchError(this.handleError)
    );
  }

  /**
   * Updates an existing person
   * @param person The person to update
   * @returns An observable of the HTTP response
   */
  updatePerson(person: Person): Observable<void> {
    return this.http.put<void>(this.personsUrl, person).pipe(
      tap(() => {
        console.log(`Person with ID ${person.id} updated successfully`);
        this.clearCache(); // Clear cache after updating a person
      }),
      catchError(this.handleError)
    );
  }
}
