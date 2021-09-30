import { TestBed } from '@angular/core/testing';

import { HttpSeviceService } from './http-sevice.service';

describe('HttpSeviceService', () => {
  let service: HttpSeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpSeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
