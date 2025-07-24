import {Routes} from '@angular/router';
import {OverviewComponent} from './pages/overview/overview.component';
import {EditComponent} from './pages/edit/edit.component';
import {AddComponent} from './pages/add/add.component';

export const routes: Routes = [
  {path: '', component: OverviewComponent },
  {path: 'edit' , component: EditComponent},
  {path: 'add' , component: AddComponent}
];

