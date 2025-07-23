import {Component} from '@angular/core';
import {PersonService} from './pages/services/person.service';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [NgIf, NgForOf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent{
  title = 'addressbook';
  allPersons;

  constructor(private personService: PersonService) {
    this.allPersons = this.personService.allPersons;
  }
}
