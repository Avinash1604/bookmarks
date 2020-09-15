import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupLinkCardComponent } from './group-link-card.component';

describe('GroupLinkCardComponent', () => {
  let component: GroupLinkCardComponent;
  let fixture: ComponentFixture<GroupLinkCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupLinkCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupLinkCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
