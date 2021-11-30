import { TestBed } from '@angular/core/testing';

import { PictureHandlerService } from './picture-handler.service';

describe('PictureHandlerService', () => {
  let service: PictureHandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PictureHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
