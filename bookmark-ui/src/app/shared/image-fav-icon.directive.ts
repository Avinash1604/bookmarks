import { Directive, Input, ElementRef, AfterViewInit } from '@angular/core';

@Directive({
  selector: '[appImageFavIcon]'
})
export class ImageFavIconDirective implements AfterViewInit {

  @Input() src;

  constructor(private imageRef: ElementRef) {
  }

  ngAfterViewInit(): void {
      const img = new Image();
      img.onload = () => {
          this.setImage(this.src);
      };

      img.onerror = () => {
          this.setImage('https://img.icons8.com/metro/26/000000/link.png');
      };

      img.src = this.src;
  }

  private setImage(src: string) {
      this.imageRef.nativeElement.setAttribute('src', src);
  }
}
