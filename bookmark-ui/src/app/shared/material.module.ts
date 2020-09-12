import { NgModule } from '@angular/core';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import {ClipboardModule} from '@angular/cdk/clipboard';
import { HttpClientModule } from '@angular/common/http';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import {MatTabsModule} from '@angular/material/tabs';
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatIconModule} from '@angular/material/icon';
import {MatExpansionModule} from '@angular/material/expansion';
import { ImageFavIconDirective } from './image-fav-icon.directive';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { BookmarkCardLayoutComponent } from './bookmark-card-layout/bookmark-card-layout.component';
import { CommonModule } from '@angular/common';


@NgModule({
    imports: [
        MatButtonModule,
        MatDatepickerModule,
        MatFormFieldModule,
        MatNativeDateModule,
        MatInputModule,
        ClipboardModule,
        HttpClientModule,
        MatSnackBarModule,
        MatDialogModule,
        MatSidenavModule,
        MatToolbarModule,
        MatListModule,
        MatCardModule,
        MatTabsModule,
        MatTableModule,
        MatSortModule,
        MatPaginatorModule,
        MatSlideToggleModule,
        MatIconModule,
        MatExpansionModule,
        MatProgressSpinnerModule,
        CommonModule
    ],
    exports: [
        CommonModule,
        MatButtonModule,
        MatDatepickerModule,
        MatFormFieldModule,
        MatNativeDateModule,
        MatInputModule,
        ClipboardModule,
        HttpClientModule,
        MatSnackBarModule,
        MatDialogModule,
        MatSidenavModule,
        MatToolbarModule,
        MatListModule,
        MatCardModule,
        MatTabsModule,
        MatTableModule,
        MatSortModule,
        MatPaginatorModule,
        MatSlideToggleModule,
        MatIconModule,
        MatExpansionModule,
        ImageFavIconDirective,
        MatProgressSpinnerModule,
        BookmarkCardLayoutComponent
    ],
    declarations: [ImageFavIconDirective, BookmarkCardLayoutComponent]
})
export class MaterialModule {

}
