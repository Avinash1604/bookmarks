import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardHomeComponent } from './dashboard-home/dashboard-home.component';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/shared/material.module';
import { CreateLinkComponent } from './create-link/create-link.component';

const routes: Routes = [
  {
    path: '' , component: DashboardHomeComponent
  }
];

@NgModule({
  declarations: [DashboardHomeComponent, CreateLinkComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    MaterialModule
  ],
})
export class DashboardModule { }
