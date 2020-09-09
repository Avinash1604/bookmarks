import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './feature/home/home.component';
import { DashboardHomeComponent } from './feature/dashboard/dashboard-home/dashboard-home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'dashboard',
    loadChildren: () =>
      import('./feature/dashboard/dashboard.module').then(
        (m) => m.DashboardModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
