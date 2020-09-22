import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestRegisterComponent } from './request-register.component';

describe('RequestRegisterComponent', () => {
  let component: RequestRegisterComponent;
  let fixture: ComponentFixture<RequestRegisterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RequestRegisterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
