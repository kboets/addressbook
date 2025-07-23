import {Component, inject, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {PersonService} from './pages/services/person.service';
import {Person} from './domain/person';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NgIf, NgForOf],
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
