import {PersonService} from '../../services/person.service';
import {Component} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [NgIf, NgForOf],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss'
})
export class OverviewComponent {
  title = 'adresboek';
  allPersons;

  constructor(private personService: PersonService) {
    this.allPersons = this.personService.allPersons;
  }
}
