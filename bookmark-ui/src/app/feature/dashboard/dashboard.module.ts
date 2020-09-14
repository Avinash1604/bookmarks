import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardHomeComponent } from './dashboard-home/dashboard-home.component';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/shared/material.module';
import { CreateLinkComponent } from './create-link/create-link.component';
import { LinkCardComponent } from './link-card/link-card.component';
import { ExpiredCardComponent } from './expired-card/expired-card.component';
import { GroupComponent } from './group/group.component';
import { CreateGroupComponent } from './create-group/create-group.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardHomeComponent,
    children: [
      { path: '', redirectTo: 'links', pathMatch: 'full' },
      {
        path: 'links',
        component: LinkCardComponent,
      },
      {
        path: 'all-links',
        component: LinkCardComponent,
      },
      {
        path: 'expired',
        component: ExpiredCardComponent,
      },
      {
        path: 'group',
        component: GroupComponent,
      },
    ],
  },
];

@NgModule({
  declarations: [
    DashboardHomeComponent,
    CreateLinkComponent,
    LinkCardComponent,
    ExpiredCardComponent,
    GroupComponent,
    CreateGroupComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
  ],
})
export class DashboardModule {}
