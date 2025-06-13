import { Component, Input, Output, EventEmitter, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-modal',
  standalone: false,
  encapsulation: ViewEncapsulation.None,
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent {
  @Input() show = false;
  @Input() title = '';
  @Output() close = new EventEmitter<void>();

  onBackdropClick() {
    this.close.emit();
  }
}