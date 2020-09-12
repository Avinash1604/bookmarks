import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { Operation } from 'src/app/shared/bookmark-card-layout/bookmark-card-layout.component';
import { MaterialModule } from 'src/app/shared/material.module';
import { UrlService } from 'src/app/shared/service/url.service';
import { ExpiredCardComponent } from './expired-card.component';


export class MatDialogMock {
  open() {
    return {
      afterClosed: () => of({ action: true }),
    };
  }
}

export class MatDialogRefMock {
  close() {}
}

describe('ExpiredCardComponent', () => {
  let component: ExpiredCardComponent;
  let fixture: ComponentFixture<ExpiredCardComponent>;
  let urlService: UrlService;

  const bookmarkResponse = {
    details: [
      {
        longUrl:
          'https://stackoverflow.com/questions/10282939/how-to-get-favicons-url-from-a-generic-webpage-in-javascript',
        id: 114,
        expiryDate: '2020-09-04',
        shortUrl: 'https://bookmarks-tiny.herokuapp.com/b0',
        createdOn: '2020-09-11T11:45:46.506037',
        title: 'Stack overflow',
        description: 'Testing the stack overflow link',
        bookmarked: true,
      },
    ],
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExpiredCardComponent ],
      imports: [
        BrowserAnimationsModule,
        MaterialModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
      ],
      providers: [
        UrlService,
        { provide: MatDialog, useClass: MatDialogMock },
        { provide: MatDialogRef, useClass: MatDialogRefMock },
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpiredCardComponent);
    component = fixture.componentInstance;
    urlService = TestBed.inject(UrlService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get all bookmarked urls', () => {
    spyOn(urlService, 'getBookmarkedShortUrl').and.returnValue(
      of(bookmarkResponse)
    );
    component.getAllUrls();
    expect(component.bookmarkUrls).toEqual(bookmarkResponse.details);
  });

  it('should get a border color depending on expiry or non expiry date', () => {
    expect(component.borderStyleExpired('2020-06-20')).toEqual(
      'left-border-red'
    );
    expect(component.borderStyleExpired('2025-06-20')).toEqual(
      'left-border-red'
    );
  });

  it('should get a notification when new link is added', () => {
    component.newlinkAdded();
    urlService.isBookMarkLinkAdded(true);
    urlService.getBookmarkAdded.subscribe((data) => {
      expect(data).toEqual(true);
    });
  });

  it('should open new window and redirect to web app when user clicks on card', () => {
    spyOn(window, 'open');
    component.openUrlOnCardClick('http://localhost');
    expect(window.open).toHaveBeenCalledWith('http://localhost', '_blank');
  });

  it('should copy object when clicks on copy', () => {
    spyOn(component.clipboard, 'copy');
    component.copy('test');
    expect(component.clipboard.copy).toHaveBeenCalledWith('test');
  });

  it('should get a fav ion url for the given url', () => {
    expect(component.getFavIcon('https://stackoverflow.com/questions')).toEqual(
      'https://stackoverflow.com/favicon.ico'
    );
  });

  it('should delete a url', () => {
    component.bookmarkUrls = bookmarkResponse.details;
    spyOn(urlService, 'deleteShortUrl').and.returnValue(of('success'));
    component.delete(114);
    expect(urlService.deleteShortUrl).toHaveBeenCalledWith(114);
  });

  it('should update the url data', () => {
    component.bookmarkUrls = bookmarkResponse.details;
    component.updateLink(bookmarkResponse.details[0]);
  });

  it('should create a cardModel for url model', () => {
    const cardModel = component.convertUrlToCardModel(
      bookmarkResponse.details[0]
    );
    expect(cardModel.id).toEqual(114);
  });

  it('should all card layout ouput events should work', () => {
    component.bookmarkUrls = bookmarkResponse.details;
    spyOn(component, 'copy');
    const cardModel = component.convertUrlToCardModel(
      bookmarkResponse.details[0]
    );
    component.cardOperation({ model: cardModel, operation: Operation.COPY });
    expect(component.copy).toHaveBeenCalled();
    spyOn(component, 'updateLink');
    component.cardOperation({ model: cardModel, operation: Operation.EDIT });
    expect(component.updateLink).toHaveBeenCalled();
    spyOn(component, 'delete');
    component.cardOperation({ model: cardModel, operation: Operation.DELETE });
    expect(component.delete).toHaveBeenCalled();
    spyOn(component, 'openUrlOnCardClick');
    component.cardOperation({
      model: cardModel,
      operation: Operation.CARD_CLICK,
    });
    expect(component.openUrlOnCardClick).toHaveBeenCalled();
  });
});
