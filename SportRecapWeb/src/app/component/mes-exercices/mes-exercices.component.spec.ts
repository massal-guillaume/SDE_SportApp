import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MesExercicesComponent } from './mes-exercices.component';

describe('MesExercicesComponent', () => {
  let component: MesExercicesComponent;
  let fixture: ComponentFixture<MesExercicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MesExercicesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MesExercicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
