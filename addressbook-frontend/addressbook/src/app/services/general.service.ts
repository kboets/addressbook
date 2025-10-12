import {HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';

export abstract class GeneralService {
  /**
   * Handles HTTP errors
   * @param error The HTTP error response
   * @returns An observable that errors with user-friendly message
   */
  protected handleError(error: HttpErrorResponse): Observable<never> {
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
}
