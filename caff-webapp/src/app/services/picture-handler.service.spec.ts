import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { PictureHandlerService } from './picture-handler.service';

describe('PictureHandlerService', () => {
  let service: PictureHandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule],
    });
    service = TestBed.inject(PictureHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
