import {Component} from '@angular/core';
import {PersonService} from './pages/services/person.service';
import {NgForOf, NgIf} from '@angular/common';
import {Person} from './domain/person';

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
  public onOpenModal(mode: string, person?: Person) {
    const button = document.createElement('button');
    const container = document.getElementById('main-container');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addPersonModal');
    }
    if (mode === 'edit') {
      button.setAttribute('data-target', '#updatePersonModal');
    }
    if (mode === 'delete') {
      button.setAttribute('data-target', '#deletePersonModal');
    }
    if (container) {
      container.appendChild(button);
      button.click();
    }

  }
}
