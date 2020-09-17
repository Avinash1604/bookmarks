import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {
  BookmarkCardLayoutComponent,
  CardModel,
} from './bookmark-card-layout.component';
import { ImageFavIconDirective } from '../image-fav-icon.directive';

describe('BookmarkCardLayoutComponent', () => {
  let component: BookmarkCardLayoutComponent;
  let fixture: ComponentFixture<BookmarkCardLayoutComponent>;

  const cardModel: CardModel = {
    title: 'test',
    favIcon: 'http://favicon.ico',
    description: 'desc',
    longUrl: 'http://loc',
    shortUrl: 'http://',
    leftBorderStyle: 'none',
    id: 13,
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BookmarkCardLayoutComponent, ImageFavIconDirective],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookmarkCardLayoutComponent);
    component = fixture.componentInstance;
    component.cardModel = cardModel;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should edit button event works', () => {
    spyOn(component.output, 'emit');
    component.update();
    expect(component.output.emit).toHaveBeenCalled();
  });

  it('should delete button event works', () => {
    spyOn(component.output, 'emit');
    component.delete();
    expect(component.output.emit).toHaveBeenCalled();
  });

  it('should copy button event works', () => {
    spyOn(component.output, 'emit');
    component.copy();
    expect(component.output.emit).toHaveBeenCalled();
  });


  it('should user able to select and unselect the cards', () => {
    spyOn(component.output, 'emit');
    component.handleChange({target: {checked: true}});
    expect(component.output.emit).toHaveBeenCalled();
  });

  it('should card click event works', () => {
    spyOn(component.output, 'emit');
    component.openCard();
    expect(component.output.emit).toHaveBeenCalled();
  });
});
