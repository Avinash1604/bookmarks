import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-bookmark-card-layout',
  templateUrl: './bookmark-card-layout.component.html',
  styleUrls: ['./bookmark-card-layout.component.scss'],
})
export class BookmarkCardLayoutComponent implements OnInit {
  @Input() cardModel: CardModel;
  @Output() public output = new EventEmitter<{
    model: CardModel;
    operation: Operation;
  }>();

  constructor() {}

  ngOnInit(): void {}

  public copy() {
    this.output.emit({ model: this.cardModel, operation: Operation.COPY });
  }

  public update() {
    this.output.emit({ model: this.cardModel, operation: Operation.EDIT });
  }

  public delete() {
    this.output.emit({ model: this.cardModel, operation: Operation.DELETE });
  }

  public openCard() {
    this.output.emit({
      model: this.cardModel,
      operation: Operation.CARD_CLICK,
    });
  }

  handleChange(event){
    const target = event.target;
    this.cardModel.selected = target.checked;
    this.output.emit({
      model: this.cardModel,
      operation: Operation.CHECKBOX
    });
  }
}

export interface CardModel {
  title: string;
  favIcon: string;
  description: string;
  longUrl: string;
  shortUrl: string;
  leftBorderStyle: string;
  id: number;
  selected?: boolean;
  selectionRequired?: boolean;
}

export enum Operation {
  COPY,
  DELETE,
  EDIT,
  CARD_CLICK,
  CHECKBOX
}
